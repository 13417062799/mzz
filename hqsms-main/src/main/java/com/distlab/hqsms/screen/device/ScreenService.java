package com.distlab.hqsms.screen.device;

import com.alibaba.fastjson.JSONObject;
import com.distlab.hqsms.common.net.OkHttpService;
import com.distlab.hqsms.edge.Device;
import com.distlab.hqsms.edge.DeviceService;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class ScreenService {

  private static final Logger logger = LoggerFactory.getLogger(ScreenService.class);
  private static final MediaType mediaType = MediaType.parse("application/json; charset = utf-8");

  @Autowired
  OkHttpService okHttpService;
  @Autowired
  ScreenRepository screenRepository;
  @Autowired
  DeviceService deviceService;

  //SCREEN SERVICE调用

  /**
   * 音量控制器
   *
   * @param ip     ip
   * @param volume 音量(0-15)
   * @param sc     屏幕实体
   * @return       操作结果
   */
  public Screen volumeSetter(String ip, int volume, Screen sc) {
    String url = String.format("http://%s/api/volume", ip);
    JSONObject obj = new JSONObject();
    obj.put("musicvolume", volume);
    RequestBody requestBody = RequestBody.create(mediaType, obj.toJSONString());
    //发起请求
    boolean flag = okHttpService.putRequest(url, requestBody);
    if (!flag) {
      return null;
    }
    //更新数据
    sc.setVolume(volume);
    try {
      return screenRepository.save(sc);
    } catch (Exception e) {
      logger.info("save data error: " + e.getMessage());
      return null;
    }
  }

  /**
   * 色温控制器
   *
   * @param ip               ip
   * @param colorTemperature 色温参数(2000-10000)
   * @param sc               屏幕实体
   * @return                 操作结果
   */
  public Screen colorTemperatureSetter(String ip, int colorTemperature, Screen sc) {
    String url = String.format("http://%s/api/colortemp", ip);
    JSONObject obj = new JSONObject();
    obj.put("colortemp", colorTemperature);
    RequestBody requestBody = RequestBody.create(mediaType, obj.toJSONString());
    //发起请求
    boolean flag = okHttpService.putRequest(url, requestBody);
    if (!flag) {
      return null;
    }
    //更新数据
    sc.setColorTemperature(colorTemperature);
    try {
      return screenRepository.save(sc);
    } catch (Exception e) {
      logger.info("save data error: " + e.getMessage());
      return null;
    }
  }

  /**
   * 亮度控制器
   *
   * @param ip         ip
   * @param brightness 亮度(0-255)
   * @param sc         屏幕实体
   * @return 给前端的响应
   */
  public Screen brightnessSetter(String ip, int brightness, Screen sc) {
    String url = String.format("http://%s/api/brightness", ip);
    JSONObject obj = new JSONObject();
    obj.put("brightness", brightness);
    RequestBody requestBody = RequestBody.create(mediaType, obj.toJSONString());
    //发起请求
    boolean flag = okHttpService.putRequest(url, requestBody);
    if (!flag) {
      return null;
    }
    //更新数据
    sc.setBrightness(brightness);
    try {
      return screenRepository.save(sc);
    } catch (Exception e) {
      logger.info("save data error: " + e.getMessage());
      return null;
    }
  }

  /**
   * 分辨率控制器
   *
   * @param ip         ip
   * @param width      16的倍数，max4096
   * @param height     64的倍数，max1536
   * @param sc         屏幕实体
   * @return           给前端的响应
   */
  public Screen resolutionSetter(String ip, int width, int height, Screen sc) {
    String url = String.format("http://%s/api/dimension", ip);
    JSONObject obj = new JSONObject();
    obj.put("width", width);
    obj.put("height", height);
    obj.put("hsync", 0);  //Must be 0 for the default 60FPS LED refreshing rate.
    obj.put("dclk", 0);   //Must be 0 for the default 60FPS LED refreshing rate.
    RequestBody requestBody = RequestBody.create(mediaType, obj.toJSONString());
    //发起请求
    boolean flag = okHttpService.putRequest(url, requestBody);
    if (!flag) {
      return null;
    }
    //更新数据
    sc.setResolution(width + "x" + height);
    try {
      return screenRepository.save(sc);
    } catch (Exception e) {
      logger.info("save data error: " + e.getMessage());
      return null;
    }
  }

  /**
   * 电源控制器
   *
   * @param ip      ip
   * @param command 控制命令（sleep, wakeup, reboot, shutdown不可用)
   * @param sc      屏幕实体
   * @return 给前端的响应
   */
  public Screen powerSetter(String ip, String command, Screen sc) {
    String url = String.format("http://%s/api/action", ip);
    JSONObject obj = new JSONObject();
    obj.put("command", command);
    RequestBody requestBody = RequestBody.create(mediaType, obj.toJSONString());
    //发起请求
    boolean flag = okHttpService.postRequest(url, requestBody);
    if (!flag) {
      return null;
    }
    //更新数据
    if (command.equals("wakeup") || command.equals("reboot")) {
      sc.setIsOn(Device.DeviceOn.YES);
    }
    if (command.equals("sleep")) {
      sc.setIsOn(Device.DeviceOn.NO);
    }
    try {
      return screenRepository.save(sc);
    } catch (Exception e) {
      logger.info("save data error: " + e.getMessage());
      return null;
    }
  }


  //CONTROLLER调用

  /**
   * 屏幕控制器（音量、色温、亮度、分辨率、电源）
   *
   * @param screenId 设备ID
   * @return 给前端的响应体
   */
  public Screen setter(BigInteger screenId, ScreenSetterInfo setterInfo) {
    Optional<Screen> scTemp = screenRepository.findById(screenId);
    if (!scTemp.isPresent()) {
      logger.info("screen not found");
      return null;
    }
    Screen sc = scTemp.get();
    String ip = sc.getIp();
    if (ip == null) {
      logger.info("ip not found");
      return null;
    }
    if (setterInfo.getVolume() != null) {
      return volumeSetter(ip, setterInfo.getVolume(), sc);
    }
    if (setterInfo.getBrightness() != null) {
      return brightnessSetter(ip, setterInfo.getBrightness(), sc);
    }
    if (setterInfo.getColorTemp() != null) {
      return colorTemperatureSetter(ip, setterInfo.getColorTemp(), sc);
    }
    if (setterInfo.getHeight() != null && setterInfo.getWidth() != null) {
      return resolutionSetter(ip, setterInfo.getWidth(), setterInfo.getHeight(), sc);
    }
    String command = setterInfo.getCommand();
    if (command.equals("wakeup") || command.equals("reboot") || command.equals("sleep") || command.equals("shutdown")) {
      return powerSetter(ip, command, sc);
    }
    logger.error("illegal arguments");
    return null;
  }

}
