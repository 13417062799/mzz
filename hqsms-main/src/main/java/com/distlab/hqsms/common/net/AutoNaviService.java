package com.distlab.hqsms.common.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.distlab.hqsms.edge.Pole;
import com.distlab.hqsms.edge.PoleRepository;
import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class AutoNaviService {
  private static final Logger log = LoggerFactory.getLogger(AutoNaviService.class);
  private static final String KEY = "e68f35dd33f53c8f41a646a77cb273dd";
  private static final String GEOCODE_API_URL = "https://restapi.amap.com/v3/geocode/geo";
  private static final String DISTRICT_API_URL = "https://restapi.amap.com/v3/config/district";

  @Autowired
  OkHttpService okHttpService;
  @Autowired
  PoleRepository poleRepository;

  /**
   * 获取行政区编号
   * @param placeName 地名
   * @return          操作结果（boolean）以及行政区、乡镇或街道编号
   */
  public String getAdCode(String placeName) {
    HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(GEOCODE_API_URL)).newBuilder();
    urlBuilder.addQueryParameter("key", KEY);
    urlBuilder.addQueryParameter("address", placeName);

    String bodyInString = okHttpService.queryRequest(urlBuilder);
    if (bodyInString == null) {
      return null;
    }
    JSONObject data = JSON.parseObject(bodyInString);
    if (data == null) {
      log.error("data not found");
      return null;
    }
    String status = data.getString("status");
    if (!"1".equals(status)) {
      log.error("geo data not found");
      return null;
    }
    JSONArray geocodes = data.getJSONArray("geocodes");
    if (geocodes == null) {
      log.error("geocodes not found");
      return null;
    }
    JSONObject geocode = geocodes.getJSONObject(0);
    if (geocode == null) {
      log.error("geocode not found");
      return null;
    }
    String adcode = geocode.getString("adcode");
    if (adcode == null) {
      log.error("place name error: adcode not found");
    }
    return adcode;
  }

  /**
   * 获取行政区经度数组和纬度数组
   * @param adcode  行政区编号
   * @return        操作结果（boolean）以及经度数组和纬度数组（List<Map<String, float[]>>）
   */
  public List<Map<String, float[]>> getDistrict(String adcode) {
    HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(DISTRICT_API_URL)).newBuilder();
    urlBuilder.addQueryParameter("key", KEY);
    urlBuilder.addQueryParameter("keywords", adcode);
    urlBuilder.addQueryParameter("subdistrict", "0");
    urlBuilder.addQueryParameter("extensions", "all");

    String bodyInString = okHttpService.queryRequest(urlBuilder);
    if (bodyInString == null) {
      return null;
    }
    JSONObject data = JSON.parseObject(bodyInString);
    if (data == null) {
      log.error("data not found");
      return null;
    }
    String status = data.getString("status");
    if (!"1".equals(status)) {
      log.error("district data not found");
      return null;
    }
    JSONArray districts = data.getJSONArray("districts");
    if (districts == null) {
      log.error("districts not found");
      return null;
    }
    JSONObject district = districts.getJSONObject(0);
    if (district == null) {
      log.error("district not found");
      return null;
    }
    String polyline = district.getString("polyline");
    if (polyline == null) {
      log.error("polyline not found");
      return null;
    }
    List<Map<String, float[]>> pointData = new ArrayList<>();
    //分割片区（若有）
    String[] multiPolyline = polyline.split("\\|");
    //遍历片区
    for (String area : multiPolyline) {
      //单片区内点集
      String[] pointList = area.split(";");
      float[] latList = new float[pointList.length];
      float[] lngList = new float[pointList.length];
      //遍历点集
      int j = 0;
      for (String pointInStr : pointList) {
        //提取经纬度
        String[] point = pointInStr.split(",");
        lngList[j] = Float.parseFloat(point[0]);
        latList[j] = Float.parseFloat(point[1]);
        j++;
      }
      Map<String, float[]> map = new HashMap<>();
      map.put("latList", latList);
      map.put("lngList", lngList);
      pointData.add(map);
    }

    return pointData;
  }

  /**
   * 判断点是否在行政区内
   * @param vertx 行政区边界纬度数组
   * @param verty 多边形边界经度数组
   * @param testx 测试点纬度
   * @param testy 测试点精度
   * @return      判断结果（boolean）
   */
  public boolean isInPolygon(float[] vertx, float[] verty, float testx, float testy) {
    if (vertx == null || verty == null || vertx.length != verty.length) {
      return false;
    }
    int nvert = vertx.length;
    int i, j;
    boolean c = false;
    for (i = 0, j = nvert -1 ; i < nvert; j = i++) {
      if (((verty[i] > testy) != (verty[j] > testy)) &&
        (testx < (vertx[j] - vertx[i]) * (testy - verty[i]) / (verty[j] - verty[i]) + vertx[i])) {
        c = !c;
      }
    }
    return c;
  }

  /**
   * 根据行政区获取
   * @param district  行政区
   * @return          行政区范围内的灯杆ID数组
   */
//  @SuppressWarnings("unchecked")
  public ArrayList<BigInteger> findPoleIdsByDistrict(String district) {
    //根据地址获取区号
    String adCode = getAdCode(district);
    if (adCode == null) {
      log.error("adcode not found");
      return null;
    }
    //根据区号获取行政区边界
    List<Map<String, float[]>> pointData = getDistrict(adCode);
    if (pointData.isEmpty()) {
      log.info("point data not found");
      return null;
    }

    //根据边界获取范围内的灯杆ID
    ArrayList<BigInteger> idIn = new ArrayList<>();
    Iterable<Pole> poles = poleRepository.findAll();
    for (Pole pole : poles) {
      Float latitude = pole.getLatitude();
      Float longitude = pole.getLongitude();
      if (latitude == null || longitude == null) {
        continue;
      }
      boolean inPolygon = false;
      for (Map<String, float[]> map : pointData) {
        float[] latList = map.get("latList");
        float[] lngList = map.get("lngList");
        inPolygon = isInPolygon(latList, lngList, latitude, longitude);
        if (inPolygon) {
          //在其中一个片区，跳出遍历
          break;
        }
      }
      if (!inPolygon) {
        continue;
      }
      BigInteger poleId = pole.getId();
      if (poleId == null) {
        continue;
      }
      idIn.add(poleId);
    }
    //返回符合条件的灯杆ID
    return idIn;
  }
}
