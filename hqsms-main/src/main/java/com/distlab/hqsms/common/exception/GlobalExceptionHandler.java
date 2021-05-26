package com.distlab.hqsms.common.exception;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

//  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//  @ExceptionHandler(BindException.class)
//  public ResponseResult validateErrorHandler(BindException e) {
//    if (e.hasErrors()) {
//      String message = e.getAllErrors().get(0).getDefaultMessage();
//      String key = e.getAllErrors().get(0).getObjectName();
//      JSONObject data = new JSONObject();
//      data.put(key, message);
//      return ResponseResult.fail(ErrorInfo.BAD_REQUEST, data);
//    }
//    return ResponseResult.fail(ErrorInfo.INTERNAL_SERVER_ERROR);
//  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public WebResponse<String> validateErrorHandler(MethodArgumentNotValidException e) {
    BindingResult bindingResult = e.getBindingResult();
    if (bindingResult.hasErrors()) {
      String field = bindingResult.getFieldErrors().get(0).getField();
      String message = bindingResult.getFieldErrors().get(0).getDefaultMessage();
      return WebResponse.fail(WebResponseEnum.OUT_PARAM_VALUE_ERROR, String.format("%s: %s", field, message));
    }
    return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public WebResponse<String> validatorErrorHandler(ConstraintViolationException e) {
    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
    if (!violations.isEmpty()) {
      String property = violations.iterator().next().getPropertyPath().toString();
      if (property.contains(".")) {
        property = property.substring(property.lastIndexOf(".")+1);
      }
      return WebResponse.fail(WebResponseEnum.OUT_PARAM_VALUE_ERROR, String.format("%s: %s", property, violations.iterator().next().getMessage()));
    }
    return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public WebResponse<String> validatorErrorHandler(HttpRequestMethodNotSupportedException e) {
    return WebResponse.fail(WebResponseEnum.OUT_REQUEST_METHOD_ERROR, String.format("%s: %s", e.getMethod(), e.getMessage()));
  }

  @ExceptionHandler(MismatchedInputException.class)
  public WebResponse<String> validatorErrorHandler(MismatchedInputException e) {
    String typeName = e.getTargetType().getTypeName();
    String fieldName = e.getPath().get(0).getFieldName();
    return WebResponse.fail(WebResponseEnum.OUT_PARAM_TYPE_ERROR, String.format("%s: should be %s", fieldName, typeName));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public WebResponse<String> validatorErrorHandler(MethodArgumentTypeMismatchException e) {
    String message = e.getCause().getCause().getMessage();
    return WebResponse.fail(WebResponseEnum.OUT_PARAM_FORMAT_ERROR, String.format("%s: %s", e.getName(), message.toLowerCase()));
  }

}
