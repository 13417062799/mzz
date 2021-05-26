package com.distlab.hqsms.common.exception;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


@Configuration
public class ValidatorConfig {

  /**
   * 配置校验器
   * @return  validator
   */
  @Bean
  public Validator validator() {
    ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
      .configure()
      //快速失败模式，遇到第一个不满足直接返回，不在继续校验后面的参数
      .failFast(true)
      .buildValidatorFactory();
    return validatorFactory.getValidator();
  }

  /**
   * 方法参数校验器
   */
  @Bean
  public MethodValidationPostProcessor methodValidationPostProcessor() {
    MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
    //设置快速失败返回
    postProcessor.setValidator(validator());
    return postProcessor;
  }
}
