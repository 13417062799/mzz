package com.distlab.hqsms.strategy;

import com.distlab.hqsms.camera.FaceService;
import com.distlab.hqsms.edge.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class StrategyService {
  private static final Logger logger = LoggerFactory.getLogger(StrategyService.class);
  @Autowired
  private RuleRepository ruleRepository;
  @Autowired
  private RuleEventRepository ruleEventRepository;
  @Autowired
  private DeviceService deviceService;
  @Autowired
  private FaceService faceService;

  /**
   * 检查数值是否在指定范围内
   *
   * @param input 数值
   * @param min   范围最小值，包含该值
   * @param max   范围最大值，包含该值
   * @return -1，未知错误；0，数值是在指定范围；1，数值不在指定范围
   */
  public int isInRange(String input, String min, String max) {
    if (StringUtils.isEmpty(input) || StringUtils.isEmpty(min) || StringUtils.isEmpty(max)) {
      return -1;
    }
    // 如果数据不在参数区间，则返回匹配失败
    if (Float.parseFloat(input) < Float.parseFloat(min) || Float.parseFloat(input) > Float.parseFloat(max)) {
      return 1;
    }
    return 0;
  }

  /**
   * 执行策略
   *
   * @param param       策略参数
   * @param existEvents 系统（非数据库）已经存在的事件信息列表，边缘服务器执行时为空列表，云端服务器执行时根据边缘服务器上报事件信息列表决定
   * @return 匹配成功后生成的事件信息列表
   */
  public List<RuleEvent> execute(StrategyParameter param, List<RuleEvent> existEvents) {
    List<Rule> rules = ruleRepository.findByDeviceIdAndDeviceTypeAndDeviceLogTypeOrderByLevelAsc(param.getDeviceId(), param.getDeviceType(), param.getDeviceLogType());
    List<RuleEvent> events = new ArrayList<>();
    for (Rule rule : rules) {
      // 查找边缘服务器的时间信息列表中属于当前策略的事件信息
      RuleEvent existEvent = existEvents.stream()
        .filter(ruleEvent -> ruleEvent.getRuleId().equals(rule.getId()))
        .findAny()
        .orElse(null);
      // 如果查找结果不为空，则意味着有边缘服务器存在而云端服务器不存在的事件信息，直接将加进事件信息列表
      if (existEvent != null) {
        events.add(existEvent);
        continue;
      }
      // 事件信息初次生成
      try {
        // 获取匹配值
        Map<String, String> values = param.getValues();
        if (match(rule, values, rule.getReference()) == 0) {
          RuleEvent event = new RuleEvent();
          BigInteger id = deviceService.getLeafId(DeviceService.LeafType.RULE_EVENT);
          if (id.equals(BigInteger.valueOf(-1))) {
            continue;
          }
          event.setId(id);
          event.setRuleId(rule.getId());
          event.setDeviceLogId(param.getDeviceLogId());
          event.setDeviceLogType(param.getDeviceLogType());
          event.setLongitude(param.getDeviceLogLongitude());
          event.setLatitude(param.getDeviceLogLatitude());
          event.setIsDone(RuleEvent.RuleEventDone.NO);
          event.setCreatedAt(new Date());

          switch (rule.getName()) {
            case CAMERA_HUMAN_APPEAR: {
              event.setLevel(Rule.RuleLevel.LEVEL1_1);
              event.setName(Rule.RuleName.CAMERA_HUMAN_APPEAR);
            }
            case CAMERA_VEHICLE_APPEAR: {
              event.setLevel(Rule.RuleLevel.LEVEL1_1);
              event.setName(Rule.RuleName.CAMERA_VEHICLE_APPEAR);
            }
            case CAMERA_OFFLINE: {
              event.setLevel(Rule.RuleLevel.LEVEL3);
              event.setName(Rule.RuleName.CAMERA_OFFLINE);
            }
            default:
          }
          events.add(event);
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    ruleEventRepository.saveAll(events);
    return events;
  }


  /**
   * 匹配策略
   *
   * @param rule       策略名称
   * @param values     匹配输入值，从事件中获取
   * @param rawReference  匹配参考值，从数据库中获取
   * @return -1，未知错误；0，匹配成功；1，匹配失败
   */
  public int match(Rule rule, Map<String, String> values, String rawReference) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      switch (rule.getName()) {
        case CAMERA_HUMAN_APPEAR: {
          CameraHumanReference reference = mapper.readValue(rawReference, CameraHumanReference.class);
          // 获取匹配数据和匹配参数
          String faceScore = values.get("faceScore");
          String faceCode = values.get("faceCode");
          if (StringUtils.isEmpty(faceScore) || StringUtils.isEmpty(faceCode)) {
            return -1;
          }
          String faceCodeReference = reference.getCode();
          String faceCodeReferenceDistanceMin = String.valueOf(reference.getCodeDistanceMin());
          String faceCodeReferenceDistanceMax = String.valueOf(reference.getCodeDistanceMax());
          String faceScoreMin = String.valueOf(reference.getFaceScoreMin());
          String faceScoreMax = String.valueOf(reference.getFaceScoreMax());
          if (isInRange(faceScore, faceScoreMin, faceScoreMax) != 0) {
            return 1;
          }
          // 人脸识别延迟太大，待修改
          List<FaceService.FaceDistance> distances = faceService.faceDistance(faceCode, faceCodeReference);
          // 选取第一个人脸编码作为指定编码
          if (distances.size() == 0) {
            return 1;
          }
          String distance = distances.get(0).getDistance().toString();
          return isInRange(distance, faceCodeReferenceDistanceMin, faceCodeReferenceDistanceMax);
        }
        case CAMERA_VEHICLE_APPEAR: {
          // 获取匹配数据和匹配参数，
          CameraVehicleReference reference = mapper.readValue(rawReference, CameraVehicleReference.class);
          String plateEntireScore = values.get("plateEntireScore");
          String plateChars = values.get("plateChars");
          if (StringUtils.isEmpty(plateEntireScore) || StringUtils.isEmpty(plateChars)) {
            return -1;
          }
          String plateCharsRegexp = reference.getPlate();
          String plateEntireScoreMin = String.valueOf(reference.getPlateScoreMin());
          String plateEntireScoreMax = String.valueOf(reference.getPlateScoreMax());
          if (isInRange(plateEntireScore, plateEntireScoreMin, plateEntireScoreMax) != 0) {
            return 1;
          }
          return Pattern.matches(plateCharsRegexp, plateChars) ? 0 : 1;
        }
        default:
          return 1;
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      return -1;
    }
  }

}
