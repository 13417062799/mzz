package com.distlab.hqsms.edge;

import com.distlab.hqsms.alarm.Alarm;
import com.distlab.hqsms.alarm.AlarmRepository;
import com.distlab.hqsms.broadcast.device.Broadcast;
import com.distlab.hqsms.broadcast.device.BroadcastRepository;
import com.distlab.hqsms.camera.Camera;
import com.distlab.hqsms.camera.CameraRepository;
import com.distlab.hqsms.charger.Charger;
import com.distlab.hqsms.charger.ChargerRepository;
import com.distlab.hqsms.common.StringUtils;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.light.Light;
import com.distlab.hqsms.light.LightRepository;
import com.distlab.hqsms.meter.Meter;
import com.distlab.hqsms.meter.MeterRepository;
import com.distlab.hqsms.screen.device.Screen;
import com.distlab.hqsms.screen.device.ScreenRepository;
import com.distlab.hqsms.strategy.Rule;
import com.distlab.hqsms.weather.Weather;
import com.distlab.hqsms.weather.WeatherRepository;
import com.distlab.hqsms.wifi.WifiAP;
import com.distlab.hqsms.wifi.WifiAPRepository;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.Status;
import com.sankuai.inf.leaf.service.SegmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

@Service
public class DeviceService {
  public static final String LOCALHOST = "127.0.0.1";
  private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);
  @Autowired
  CameraRepository cameraRepository;
  @Autowired
  WeatherRepository weatherRepository;
  @Autowired
  BroadcastRepository broadcastRepository;
  @Autowired
  ScreenRepository screenRepository;
  @Autowired
  AlarmRepository alarmRepository;
  @Autowired
  ChargerRepository chargerRepository;
  @Autowired
  MeterRepository meterRepository;
  @Autowired
  WifiAPRepository wifiAPRepository;
  @Autowired
  LightRepository lightRepository;
  @Autowired
  PoleRepository poleRepository;
  @Autowired
  ServerRepository serverRepository;
  @Autowired
  private SegmentService segmentService;
  @Value("${hqsms.edge.server.enable}")
  boolean isEdgeServerEnable;

  public enum LeafType {
    SERVER("server"),
    POLE("pole"),
    PLAN("plan"),
    CHARGER("charger"),
    CHARGER_LOG("charger-log"),
    CHARGER_ORDER("charger-order"),
    WIFI("wifi"),
    WIFI_LOG("wifi-log"),
    SCREEN("screen"),
    SCREEN_CONTENT("screen-content"),
    ALARM("alarm"),
    ALARM_LOG("alarm-log"),
    LIGHT("light"),
    LIGHT_LOG("light-log"),
    WEATHER("weather"),
    WEATHER_LOG("weather-log"),
    METER("meter"),
    METER_LOG("meter-log"),
    BROADCAST("broadcast"),
    BROADCAST_CONTENT("broadcast-content"),
    CAMERA("camera"),
    CAMERA_LOG("camera-log"),
    CAMERA_VEHICLE("camera-vehicle"),
    CAMERA_HUMAN("camera-human"),
    RULE("rule"),
    RULE_EVENT("rule-event");

    private final String code;

    private LeafType(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

  /**
   * ???????????????ID
   * @param leafType ID??????
   * @return ?????????ID
   */
  public BigInteger getLeafId(LeafType leafType) {
    Result leafId = segmentService.getId(leafType.getCode());
    if (leafId.getStatus() == Status.EXCEPTION) {
      return BigInteger.valueOf(-1);
    }
    return BigInteger.valueOf(leafId.getId());
  }

  /**
   * ????????????ID??????????????????????????????????????????
   * @param deviceId  ??????ID
   * @param deviceType  ????????????
   * @return  ?????????????????????
   */
  public String getServerAddress(BigInteger deviceId, Rule.RuleDeviceType deviceType) {
    if (isEdgeServerEnable) {
      return LOCALHOST;
    }
    DeviceHierarchy<Server, Pole, Device> hierarchy = getHierarchy(deviceId, deviceType);
    if (hierarchy == null) {
      return null;
    }
    return String.format("%s:%s", hierarchy.getServer().getIp(), hierarchy.getServer().getPort());
  }

  /**
   * ????????????ID??????????????????????????????????????????
   * @param poleId  ??????ID
   * @return  ?????????????????????
   */
  public String getServerAddressByPoleId(BigInteger poleId) {
    if (isEdgeServerEnable) {
      return LOCALHOST;
    }
    return poleRepository.findById(poleId)
      .map(pole -> serverRepository.findById(pole.getServerId())
        .map(server -> String.format("%s:%s", server.getIp(), server.getPort()))
        .orElse(""))
      .orElse(null);
  }

  /**
   * ????????????ID??????????????????????????????????????????
   * @param deviceId  ??????ID
   * @param deviceType  ????????????
   * @return  ??????????????????
   */
  public DeviceHierarchy<Server, Pole, Device> getHierarchy(BigInteger deviceId, Rule.RuleDeviceType deviceType) {
    Device device = null;
    switch (deviceType) {
      case CAMERA: {
        Optional<Camera> camera = cameraRepository.findById(deviceId);
        if (!camera.isPresent()) {
          logger.warn("device not exists: " + deviceId);
          return null;
        }
        ;
        device = camera.get();
        break;
      }
      case WEATHER: {
        Optional<Weather> weather = weatherRepository.findById(deviceId);
        if (!weather.isPresent()) {
          logger.warn("device not exists: " + deviceId);
          return null;
        }
        ;
        device = weather.get();
        break;
      }
      case METER: {
        Optional<Meter> meter = meterRepository.findById(deviceId);
        if (!meter.isPresent()) {
          logger.warn("device not exists: " + deviceId);
          return null;
        }

        device = meter.get();
        break;
      }
      case BROADCAST: {
        Optional<Broadcast> broadcast = broadcastRepository.findById(deviceId);
        if (!broadcast.isPresent()) {
          logger.warn("device not exists: " + deviceId);
          return null;
        }
        ;
        device = broadcast.get();
        break;
      }
      case SCREEN: {
        Optional<Screen> screen = screenRepository.findById(deviceId);
        if (!screen.isPresent()) {
          logger.warn("device not exists: " + deviceId);
          return null;
        }
        ;
        device = screen.get();
        break;
      }
      case CHARGER: {
        Optional<Charger> chager = chargerRepository.findById(deviceId);
        if (!chager.isPresent()) {
          logger.warn("device not exists: " + deviceId);
          return null;
        }

        device = chager.get();
        break;
      }
      case ALARM: {
        Optional<Alarm> alarm = alarmRepository.findById(deviceId);
        if (!alarm.isPresent()) {
          logger.warn("device not exists: " + deviceId);
          return null;
        }
        ;
        device = alarm.get();
        break;
      }
      case WIFI: {
        Optional<WifiAP> wifi = wifiAPRepository.findById(deviceId);
        if (!wifi.isPresent()) {
          logger.warn("device not exists: " + deviceId);
          return null;
        }
        ;
        device = wifi.get();
        break;
      }
      case LIGHT: {
        Optional<Light> light = lightRepository.findById(deviceId);
        if (!light.isPresent()) {
          logger.warn("device not exists: " + deviceId);
          return null;
        }
        device = light.get();
        break;
      }
    }

    if (device == null) {
      logger.warn("device type not match: " + deviceType);
      return null;
    }

    BigInteger poleId = device.getPoleId();
    Optional<Pole> pole = poleRepository.findById(poleId);
    if (!pole.isPresent()) {
      logger.warn("pole not exists: " + poleId);
      return null;
    }

    BigInteger serverId = pole.get().getServerId();
    Optional<Server> server = serverRepository.findById(serverId);
    if (!server.isPresent()) {
      logger.warn("server not exists: " + serverId);
      return null;
    }

    return new DeviceHierarchy<>(server.get(), pole.get(), device, deviceType);
  }

  /**
   * ??????????????????????????????
   *
   * @param repository  ??????
   * @param type        ????????????
   * @param keyword     ?????????
   * @param pageable    ????????????
   * @param <T>         ????????????
   * @param <E>         ????????????
   * @return            ??????????????????
   */
  public <T extends DeviceRepository<E>, E> WebResponse<Page<E>> search(T repository, int type, String keyword, Pageable pageable) {
    switch (type) {
      case 1 : // ?????????
        try {
          return WebResponse.success(repository.findBySupplierLike("%" + keyword + "%", pageable));
        } catch (Exception e) {
          return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
        }
      case 2 : // ????????????
      {
        if ("1".equals(keyword)) {
          try {
            return WebResponse.success(repository.findByIsOn(Device.DeviceOn.YES, pageable));
          } catch (Exception e) {
            return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
          }
        }
        if ("0".equals(keyword)) {
          try {
            return WebResponse.success(repository.findByIsOn(Device.DeviceOn.NO, pageable));
          } catch (Exception e) {
            return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
          }
        }
        return WebResponse.fail(WebResponseEnum.OUT_PARAM_VALUE_ERROR, "must be 0 or 1");
      }
      case 3 : // ????????????
        try {
          return WebResponse.success(repository.findByCodeLike("%" + keyword + "%", pageable));
        } catch (Exception e) {
          return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
        }
      case 4 : // ????????????
        try {
          return WebResponse.success(repository.findByModelLike("%" + keyword + "%", pageable));
        } catch (Exception e) {
          return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
        }
      case 5 : // ????????????
        try {
          return WebResponse.success(repository.findByNameLike("%" + keyword + "%", pageable));
        } catch (Exception e) {
          return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
        }
      default:
        return WebResponse.fail(WebResponseEnum.OUT_PARAM_VALUE_ERROR, "type error");
    }
  }


  /**
   * ?????????????????????????????????????????????????????????????????????null
   * ???????????????????????????????????????????????????????????????
   *
   * @param sourceBean  ???????????????????????????
   * @param targetBean  ??????????????????
   * @return            ????????????????????????????????????
   * @throws IllegalAccessException ??????
   */
  public <T> T updateFields(T sourceBean, T targetBean) throws IllegalAccessException{
    Class<?> sourceClass = sourceBean.getClass();
    List<Field> sourceFields = new ArrayList<>();
    while (sourceClass != null) {
      sourceFields.addAll(Arrays.asList(sourceClass.getDeclaredFields()));
      sourceClass = sourceClass.getSuperclass();
    }
    Class<?> targetClass = targetBean.getClass();
    List<Field> targetFields = new ArrayList<>();
    while (targetClass != null) {
      targetFields.addAll(Arrays.asList(targetClass.getDeclaredFields()));
      targetClass = targetClass.getSuperclass();
    }

    for (int i = 0; i < sourceFields.size(); i++) {
      Field sourceField = sourceFields.get(i);
      Field targetField = targetFields.get(i);
      sourceField.setAccessible(true);
      targetField.setAccessible(true);

      if (sourceField.get(sourceBean) != null && !"serialVersionUID".equals(sourceField.getName())) {
        targetField.set(targetBean, sourceField.get(sourceBean));
      }
    }
    return targetBean;
  }

  /**
   * ????????????????????????????????????
   *
   * @param deviceRepository  ??????
   * @param filter            ????????????
   * @param pageable          ????????????
   * @param <D>               ????????????
   * @param <E>               ????????????
   * @return                  ????????????
   */
  public <D extends DeviceRepository<E>, E> WebResponse<Page<E>> search(
    D deviceRepository, DeviceAndLogFilter filter, Pageable pageable
  ) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Specification<E> specification = new Specification<E>() {
      private static final long serialVersionUID = 1L;
      @Override
      public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String startInStr = filter.getStart();
        String endInStr = filter.getEnd();
        Date start = null, end = null;
        try {
          if (startInStr != null) {
            start = sdf.parse(startInStr);
          }
          if (endInStr != null) {
            end = sdf.parse(endInStr);
          }
        } catch (ParseException e) {
          start = null;
          end = null;
        }

        Device.DeviceOn isOn = filter.getIsOn();
        String supplier = filter.getSupplier();
        String code = filter.getCode();
        String model = filter.getModel();
        String name = filter.getName();

        List<Predicate> predicates = new ArrayList<>();

        // ????????????
        if (start != null && end != null) {
          Predicate predicate = cb.between(root.get("createdAt"), start, end);
          predicates.add(predicate);
        }
         if (start != null && end == null) {
          Predicate predicate = cb.between(root.get("createdAt"), start, new Date());
          predicates.add(predicate);
        }
        // ????????????
        if (isOn != null) {
          Predicate predicate = cb.equal(root.get("isOn"), isOn);
          predicates.add(predicate);
        }
        // ?????????
        if (StringUtils.isNotBlank(supplier)) {
          Predicate predicate = cb.like(root.get("supplier"), "%" + supplier + "%");
          predicates.add(predicate);
        }
        // ????????????
        if (StringUtils.isNotBlank(code)) {
          Predicate predicate = cb.like(root.get("code"), "%" + code + "%");
          predicates.add(predicate);
        }
        // ????????????
        if (StringUtils.isNotBlank(model)) {
          Predicate predicate = cb.like(root.get("model"), "%" + model + "%");
          predicates.add(predicate);
        }
        // ????????????
        if (StringUtils.isNotBlank(name)) {
          Predicate predicate = cb.like(root.get("name"), "%" + name + "%");
          predicates.add(predicate);
        }

        if (predicates.size() == 0) {
          return null;
        }

        Predicate[] p = new Predicate[predicates.size()];
        return cb.and(predicates.toArray(p));
      }
    };
    try {
      Page<E> pages = deviceRepository.findAll(specification, pageable);
      return WebResponse.success(pages);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  /**
   * ????????????????????????????????????
   *
   * @param filter        ????????????
   * @param <E>           ????????????
   * @return              ????????????
   */
  public <E, T extends DeviceAndLogFilter> Specification<E> searchLog(
    Specification<E> specSource, T filter
  ) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Specification<E> specTarget = new Specification<E>() {
      private static final long serialVersionUID = 1L;
      @Override
      public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String startInStr = filter.getStart();
        String endInStr = filter.getEnd();
        Date start = null, end = null;
        try {
          if (startInStr != null) {
            start = sdf.parse(startInStr);
          }
          if (endInStr != null) {
            end = sdf.parse(endInStr);
          }
        } catch (ParseException e) {
          start = null;
          end = null;
        }
        BigInteger deviceId = filter.getDeviceId();

        List<Predicate> predicates = new ArrayList<>();

        // ????????????
        if (start != null && end != null) {
          Predicate predicate = cb.between(root.get("createdAt"), start, end);
          predicates.add(predicate);
        }
        if (start != null && end == null) {
          Predicate predicate = cb.between(root.get("createdAt"), start, new Date());
          predicates.add(predicate);
        }
        // ??????ID
        if (deviceId != null) {
          Predicate predicate = cb.equal(root.get("deviceId"), deviceId);
          predicates.add(predicate);
        }

        if (predicates.size() == 0) {
          return null;
        }

        Predicate[] p = new Predicate[predicates.size()];
        return cb.and(predicates.toArray(p));
      }
    };

    return specTarget.and(specSource);
  }

  /**
   * ??????PING
   * ??????InetAddress???isReachable??????????????????ping???????????????????????????????????????????????????????????????????????????
   * @param ip ??????
   * @param timeOut ????????????
   */
  static boolean isReachable(String ip, int timeOut){
    try {
      InetAddress address = InetAddress.getByName(ip);
      return address.isReachable(timeOut);
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }
}

@Converter(autoApply = true)
class LeafTypeConvertor implements AttributeConverter<DeviceService.LeafType, String> {
  @Override
  public String convertToDatabaseColumn(DeviceService.LeafType value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public DeviceService.LeafType convertToEntityAttribute(String code) {
    if (code == null) {
      return null;
    }

    return Stream.of(DeviceService.LeafType.values())
      .filter(c -> c.getCode().equals(code))
      .findFirst()
      .orElseThrow(IllegalArgumentException::new);
  }
}
