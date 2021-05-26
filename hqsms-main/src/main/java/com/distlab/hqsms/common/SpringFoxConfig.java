package com.distlab.hqsms.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .tags(new Tag("边缘服务器", "管理边缘服务器信息"))
      .tags(new Tag("灯杆", "管理灯杆信息"))
      .tags(new Tag("摄像头", "管理摄像头信息和数据"))
      .tags(new Tag("气象传感器", "管理气象传感器信息和数据"))
      .tags(new Tag("广播", "管理广播信息和数据"))
      .tags(new Tag("灯控", "管理灯控信息和数据"))
      .tags(new Tag("屏幕", "管理屏幕信息和数据"))
      .tags(new Tag("充电桩", "管理充电桩信息和数据"))
      .tags(new Tag("电能表", "管理电能表信息和数据"))
      .tags(new Tag("报警器", "管理报警器信息和数据"))
      .tags(new Tag("无线网络", "管理无线网络信息和数据"))
      .tags(new Tag("策略", "策略和策略事件"))
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.distlab.hqsms"))
      .paths(PathSelectors.any())
      .build()
      .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
      "华全多功能杆管理系统(HQSMS)接口文档",
      "可以管理设备信息、获取设备数据、控制设备和配置设备策略",
      "0.1",
      "",
      new Contact("分布技术联合实验室", "", ""),
      "MIT", "https://opensource.org/licenses/MIT", Collections.emptyList());
  }
}