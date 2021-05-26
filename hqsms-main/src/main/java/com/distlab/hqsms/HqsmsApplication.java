package com.distlab.hqsms;

import com.sankuai.inf.leaf.plugin.annotation.EnableLeafServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableLeafServer
public class HqsmsApplication extends WebMvcConfigurerAdapter implements CommandLineRunner {
  @Autowired
  private ConfigurableApplicationContext applicationContext;

  public static void main(String[] args) {
    SpringApplication.run(HqsmsApplication.class, args);
  }

  @Override
  public void run(String... args) {
    applicationContext.registerShutdownHook();
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    // Can just allow `methods` that you need.
    registry.addMapping("/**").allowedMethods("PUT", "GET", "DELETE", "OPTIONS", "PATCH", "POST");
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
  }

}
