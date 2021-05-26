package com.distlab.hqsms.screen.content;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class ScreenContentUtils {

  //素材属性
  public static JSONObject item(
    String programName, String fileName,
    String duration, String function
  ) {
    //投放视频或图片
    String type = null;
    if (function.equals("image")) {
      type = "2";
    } else if (function.equals("video")) {
      type = "3";
    }
    //资源在服务器的相对路径
    String filePath = ".\\" + programName + ".files\\" + fileName;
    JSONObject fileSource = new JSONObject();
    fileSource.put("IsRelative", 1);//相对路径，必须为1
    fileSource.put("FilePath", filePath);
    //转场特效
    JSONObject inEffect = new JSONObject();
    inEffect.put("Type", 2);//转场特效，选择范围：0~48
    inEffect.put("Time", "1000");//转场持续时间，单位：ms
    //构建素材
    JSONObject item = new JSONObject();
    item.put("Type", type);//2-图片
    item.put("Duration", duration);//素材持续时间
    item.put("Volume", "1.000000");
    item.put("FileSource", fileSource);
    item.put("ReserveAS", "0");//是否保持宽高比
    item.put("inEffect", inEffect);

    return item;
  }


  //区域属性
  public static JSONObject region(JSONArray items, String width, String height, String layer) {
    JSONObject rect = new JSONObject();
    rect.put("X", "0");//区域位置坐标X轴，为统一暂不支持自定义
    rect.put("Y", "0");//区域位置坐标Y轴
    rect.put("Width", width);//区域宽度
    rect.put("Height", height);//区域高度
    rect.put("BorderWidth", "0");//边界宽度，默认为0
    rect.put("BorderColor", "0xFFFFFF00");//边界颜色

    JSONObject region = new JSONObject();
    region.put("Layer", layer);//1-Top layer; 2-Second layer; ....
    region.put("Rect", rect);
    region.put("Items", items);

    return region;
  }

  //节目页属性
  public static JSONObject page(JSONArray regions, String pageDuration, String loopType) {

    JSONObject page = new JSONObject();
    page.put("AppointDuration", pageDuration);//页面持续时长，单位：ms
    page.put("LoopType", loopType);//0-使用页面持续时间，1-页面所有内容转换到一下页
    page.put("BgColor", "0x00FFFFFF");//页面背景颜色，暂不支持自定义
    page.put("Regions", regions);

    return page;
  }

  //节目信息属性
  public static JSONObject programs(JSONArray pages, String width, String height) {
    JSONObject info = new JSONObject();
    info.put("Width", width);//节目宽度
    info.put("Height", height);//节目高度

    JSONObject program = new JSONObject();
    program.put("Information", info);
    program.put("Pages", pages);

    JSONObject programs = new JSONObject();
    programs.put("Program", program);

    JSONObject vsnObj = new JSONObject();
    vsnObj.put("Programs", programs);

    return vsnObj;
  }
}
