package com.distlab.hqsms.camera;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class FaceService {
  private final Logger logger = LoggerFactory.getLogger(FaceService.class);
  public static String scriptPath;

  static {
    try {
      scriptPath = System.getProperty("user.dir") + File.separator + "sdk" + File.separator + "face_recognition" + File.separator + "main.py";
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  @Value("${hqsms.cloud.server.host}")
  String cloudServerHost;
  @Value("${hqsms.cloud.server.port}")
  String cloudServerPort;

  /**
   * 校验文件是否有效
   * @param name 文件名称
   * @return 校验结果
   */
  public boolean isFileValid(String name) {
    File file = new File(name);
    if (!file.isFile() || !file.canRead()) {
      logger.error("file not exists: " + name + " " + file.isFile() + " " + file.canRead());
      return false;
    }
    return true;
  }

  /**
   * 人脸定位，获取图片中人脸的位置信息
   *
   * @param faceFilePathName 图片绝对路径
   * @return 人脸位置信息的字符串如 “[[0.1, 0.2, 0.4, 0.6]]"
   */
  public List<FaceLocation> faceLocate(String faceFilePathName) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    List<String> list = new ArrayList<>();
    list.add("python3");
    list.add(scriptPath);
    list.add("locate");
    list.add(String.format("--image=%s", faceFilePathName));
    processBuilder.command(list);

    List<FaceLocation> ret = new ArrayList<>();
    try {
      if (!isFileValid(faceFilePathName)) {
        return ret;
      }
      Process process = processBuilder.start();
      int exit = process.waitFor();
      if (exit != 0) {
        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        logger.error("face locate failed: " + faceFilePathName + " " + error.readLine());
        return ret;
      }
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      ObjectMapper mapper = new ObjectMapper();
      List<List<Integer>> results = mapper.readValue(reader.readLine(), ArrayList.class);
      for (List<Integer> result : results) {
        FaceLocation location = new FaceLocation();
        location.setLocation(result);
        ret.add(location);
      }
      reader.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return ret;
  }

  /**
   * 人脸编码，获取图片中人脸的编码信息
   *
   * @param faceFilePathName 图片绝对路径
   * @return 人脸编码信息信息的字符串
   */
  public List<FaceCode> faceEncode(String faceFilePathName) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    List<String> list = new ArrayList<>();
    list.add("python3");
    list.add(scriptPath);
    list.add("encode");
    list.add(String.format("--image=%s", faceFilePathName));
    processBuilder.command(list);

    List<FaceCode> ret = new ArrayList<>();
    try {
      if (!isFileValid(faceFilePathName)) {
        return ret;
      }
      Process process = processBuilder.start();
      int exit = process.waitFor();
      if (exit != 0) {
        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        logger.error("face encode failed: " + faceFilePathName + " " + error.readLine());
        return ret;
      }
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      ObjectMapper mapper = new ObjectMapper();
      List<List<Float>> results = mapper.readValue(reader.readLine(), ArrayList.class);
      for (List<Float> result : results) {
        FaceCode code = new FaceCode();
        code.setCode(result);
        ret.add(code);
      }
      reader.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return ret;
  }

  /**
   * 人脸匹配，匹配数据库中所有人脸
   *
   * @param faceCode 需要匹配的人脸编码
   * @return 匹配结果的字符串，包含匹配成功的摄像头人员数据ID数值，如“[1, 2]"
   */
  public List<BigInteger> faceMatch(String faceCode) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    List<String> list = new ArrayList<>();
    list.add("python3");
    list.add(scriptPath);
    list.add("match");
    list.add(String.format("--code=%s", faceCode));
    list.add(String.format("--cloud=%s", String.format("%s:%s", cloudServerHost, cloudServerPort)));
    processBuilder.command(list);

    List<BigInteger> ret = new ArrayList<>();
    try {
      Process process = processBuilder.start();
      int exit = process.waitFor();
      if (exit != 0) {
        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        logger.error("face match failed: " + faceCode + " " + error.readLine());
        return ret;
      }
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      ObjectMapper mapper = new ObjectMapper();
      List<Integer> results = mapper.readValue(reader.readLine(), ArrayList.class);
      for (Integer result : results) {
        ret.add(BigInteger.valueOf(result));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return ret;
  }

  /**
   * 人脸相似度
   *
   * @param faceCode 人脸编码
   * @param faceCodeReference 需要比较的人脸编码
   * @return 相似度结果的字符串
   */
  public List<FaceDistance> faceDistance(String faceCode, String faceCodeReference) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    List<String> list = new ArrayList<>();
    list.add("python3");
    list.add(scriptPath);
    list.add("distance");
    list.add(String.format("--code=%s", faceCode));
    list.add(String.format("--reference=%s", faceCodeReference));
    processBuilder.command(list);

    List<FaceDistance> ret = new ArrayList<>();
    try {
      Process process = processBuilder.start();
      int exit = process.waitFor();
      if (exit != 0) {
        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        logger.error("face distance failed: " + faceCode + " " + error.readLine());
        return ret;
      }
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      ObjectMapper mapper = new ObjectMapper();
      List<Double> results = mapper.readValue(reader.readLine(), ArrayList.class);
      for (Double result : results) {
        FaceDistance distance = new FaceDistance();
        distance.setDistance(result);
        ret.add(distance);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return ret;
  }

  static public class FaceDistance {
    private Double distance;

    public Double getDistance() {
      return distance;
    }

    public void setDistance(Double distance) {
      this.distance = distance;
    }
  }

  static public class FaceLocation {
    private List<Integer> location;

    public List<Integer> getLocation() {
      return location;
    }

    public void setLocation(List<Integer> location) {
      this.location = location;
    }
  }

  static public class FaceCode {
    private List<Float> code;
    private BigInteger sourceId;

    public List<Float> getCode() {
      return code;
    }

    public void setCode(List<Float> code) {
      this.code = code;
    }

    public BigInteger getSourceId() {
      return sourceId;
    }

    public void setSourceId(BigInteger sourceId) {
      this.sourceId = sourceId;
    }
  }
}