package com.distlab.hqsms.strategy;

import com.distlab.hqsms.common.StringUtils;
import com.distlab.hqsms.common.exception.MessageEnum;
import com.distlab.hqsms.common.exception.MessageUtil;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.common.net.RestTemplateService;
import com.distlab.hqsms.edge.DeviceService;
import com.distlab.hqsms.edge.Pole;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Api(tags = {"策略"})
@RestController
@RequestMapping(path = "/rules")
public class RuleController {
  private final Logger logger = LoggerFactory.getLogger(RuleController.class);
  @Autowired
  private RuleRepository ruleRepository;
  @Autowired
  private DeviceService deviceService;
  @Autowired
  private RestTemplateService restTemplateService;

  @ApiOperation(value = "获取策略列表")
  @GetMapping(value = {""})
  public WebResponse<Page<Rule>> getAllRules(Pageable pageable) {
    try {
      return WebResponse.success(ruleRepository.findAll(pageable));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  public int validateReference(Rule rule) {
    Rule.RuleName ruleName = rule.getName();
    String ruleReference = rule.getReference();
    if (ruleName == null || ruleReference == null) {
      return -1;
    }
    ObjectMapper mapper = new ObjectMapper();
    try {
      switch (ruleName) {
        case CAMERA_HUMAN_APPEAR: {
          CameraHumanReference reference = mapper.readValue(ruleReference, CameraHumanReference.class);
          if (reference.getFaceScoreMin() < 0
            || reference.getFaceScoreMax() > 1
            || reference.getCodeDistanceMin() < 0
            || reference.getCodeDistanceMax() > 1
          ) {
            return -1;
          }
          break;
        }
        case CAMERA_VEHICLE_APPEAR: {
          CameraVehicleReference reference = mapper.readValue(ruleReference, CameraVehicleReference.class);
          if (reference.getPlateScoreMin() < 0
            || reference.getPlateScoreMax() > 1
            || StringUtils.isEmpty(reference.getPlate())
          ) {
            return -1;
          }
          break;
        }
        default:
          return -1;
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      return -1;
    }
    return 0;
  }

  @ApiOperation(value = "创建策略")
  @PostMapping(value = {""})
  public WebResponse<Map<String, BigInteger>> addRule(@RequestBody Rule rule) {
    if (validateReference(rule) != 0) {
      return WebResponse.fail(WebResponseEnum.OUT_PARAM_VALUE_ERROR, MessageUtil.genIdFailed(MessageEnum.RULE));
    }
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.RULE);
    if (id.equals(BigInteger.valueOf(-1))) {
      return WebResponse.fail(WebResponseEnum.SYS_GEN_ID_FAILED, MessageUtil.genIdFailed(MessageEnum.RULE));
    }
    rule.setId(id);
    rule.setCreatedAt(new Date());
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", id);
    try {
      ruleRepository.save(rule);
      return WebResponse.success(map);
    } catch (Exception ex) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.RULE, id));
    }
  }

  @ApiOperation(value = "获取策略信息")
  @GetMapping(value = {"/{id}"})
  public WebResponse<Optional<Rule>> getRule(@PathVariable BigInteger id) {
    try {
      return WebResponse.success(ruleRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "更新策略信息", httpMethod = "POST")
  @PostMapping(value = {"/{id}"})
  public WebResponse<Rule> setRule(@PathVariable BigInteger id, @RequestBody Rule rule) {
    try {
      if (validateReference(rule) != 0) {
        return WebResponse.fail(WebResponseEnum.OUT_PARAM_VALUE_ERROR, MessageUtil.genIdFailed(MessageEnum.RULE));
      }
      String address = deviceService.getServerAddress(rule.getDeviceId(), rule.getDeviceType());
      if (StringUtils.isEmpty(address)) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, id));
      }

      WebResponse<Rule> remoteRet = restTemplateService.exchange(
        String.format("http://%s/api/rules/edge/%d", address, id),
        HttpMethod.POST,
        new HttpEntity<>(rule),
        new ParameterizedTypeReference<WebResponse<Rule>>() {
        }
      );
      if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
        return remoteRet;
      }
      Rule rRule = remoteRet.getData();
      Rule ret = ruleRepository.save(rRule);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.RULE, id));
    }
  }

  @ApiOperation(value = "更新策略信息", httpMethod = "POST", hidden = true)
  @PostMapping(value = {"/edge/{id}"})
  public WebResponse<Rule> setEdgeRule(@PathVariable BigInteger id, @RequestBody Rule rule) {
    try {
      Optional<Rule> tRule = ruleRepository.findById(id);
      if (!tRule.isPresent()) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.RULE, id));
      }
      Rule ruleTarget = tRule.get();
      Rule ret = ruleRepository.save(ruleTarget);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.RULE, id));
    }
  }

  @ApiOperation(value = "删除策略信息")
  @DeleteMapping(value = {"/{id}"})
  public WebResponse<Map<String, BigInteger>> deleteRule(@PathVariable BigInteger id) {
    try {
      Optional<Rule> ret = ruleRepository.findById(id);
      if (!ret.isPresent()) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.RULE, id));
      }
      ruleRepository.delete(ret.get());
      Map<String, BigInteger> map = new HashMap<>();
      map.put("Id", id);
      return WebResponse.success(map);
    } catch (Exception ex) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.RULE, id));
    }
  }

}

@ApiModel("人员标识物参考值")
class CameraHumanReference {
  @ApiModelProperty(
    value = "待匹配的人脸特征相似度范围最小值，取值范围[0, 1.0]",
    required = true
  )
  private float codeDistanceMin;
  @ApiModelProperty(
    value = "待匹配的人脸特征相似度范围最大值，取值范围[0, 1.0]",
    required = true
  )
  private float codeDistanceMax;
  @ApiModelProperty(
    value = "待匹配的人脸特征",
    required = true
  )
  private String code;
  @ApiModelProperty(
    value = "人脸可信度范围最小值，取值范围[0, 1.0]",
    required = true
  )
  private float faceScoreMin;
  @ApiModelProperty(
    value = "人脸可信度范围最大值，取值范围[0, 1.0]",
    required = true
  )
  private float faceScoreMax;

  public float getCodeDistanceMin() {
    return codeDistanceMin;
  }

  public void setCodeDistanceMin(float codeDistanceMin) {
    this.codeDistanceMin = codeDistanceMin;
  }

  public float getCodeDistanceMax() {
    return codeDistanceMax;
  }

  public void setCodeDistanceMax(float codeDistanceMax) {
    this.codeDistanceMax = codeDistanceMax;
  }

  public float getFaceScoreMin() {
    return faceScoreMin;
  }

  public void setFaceScoreMin(float faceScoreMin) {
    this.faceScoreMin = faceScoreMin;
  }

  public float getFaceScoreMax() {
    return faceScoreMax;
  }

  public void setFaceScoreMax(float faceScoreMax) {
    this.faceScoreMax = faceScoreMax;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}

@ApiModel("车辆标识物参考值")
class CameraVehicleReference {
  @ApiModelProperty(
    value = "待匹配的车牌号码",
    required = true
  )
  private String plate;
  @ApiModelProperty(
    value = "车牌可信度范围最小值，取值范围[0, 1.0]",
    required = true
  )
  private float plateScoreMin;
  @ApiModelProperty(
    value = "车牌可信度范围最大值，取值范围[0, 1.0]",
    required = true
  )
  private float plateScoreMax;

  public float getPlateScoreMin() {
    return plateScoreMin;
  }

  public void setPlateScoreMin(float plateScoreMin) {
    this.plateScoreMin = plateScoreMin;
  }

  public float getPlateScoreMax() {
    return plateScoreMax;
  }

  public void setPlateScoreMax(float plateScoreMax) {
    this.plateScoreMax = plateScoreMax;
  }

  public String getPlate() {
    return plate;
  }

  public void setPlate(String plate) {
    this.plate = plate;
  }
}
