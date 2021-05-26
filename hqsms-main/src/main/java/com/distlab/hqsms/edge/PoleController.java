package com.distlab.hqsms.edge;

import com.distlab.hqsms.common.StringUtils;
import com.distlab.hqsms.common.exception.MessageEnum;
import com.distlab.hqsms.common.exception.MessageUtil;
import com.distlab.hqsms.common.exception.WebResponse;
import com.distlab.hqsms.common.exception.WebResponseEnum;
import com.distlab.hqsms.common.net.RestTemplateService;
import com.distlab.hqsms.strategy.Rule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Api(tags = {"灯杆"})
@RestController
@RequestMapping(path = "/poles")
public class PoleController {
  private final Logger logger = LoggerFactory.getLogger(PoleController.class);
  @Autowired
  private PoleRepository poleRepository;
  @Autowired
  private PoleService poleService;
  @Autowired
  private DeviceService deviceService;
  @Autowired
  private RestTemplateService restTemplateService;

  @ApiOperation(value = "获取灯杆列表")
  @GetMapping(value = {""})
  public WebResponse<Page<Pole>> getAllPoles(Pageable pageable) {
    try {
      return WebResponse.success(poleRepository.findAll(pageable));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "创建灯杆")
  @PostMapping(value = {""})
  public WebResponse<Map<String, BigInteger>> addPole(@RequestBody Pole pole) {
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.POLE);
    if (id.equals(BigInteger.valueOf(-1))) {
      return WebResponse.fail(WebResponseEnum.SYS_GEN_ID_FAILED, MessageUtil.genIdFailed(MessageEnum.POLE));
    }
    pole.setId(id);
    pole.setCreatedAt(new Date());
    pole.setUpdatedAt(new Date());
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", id);
    try {
      poleRepository.save(pole);
      return WebResponse.success(map);
    } catch (Exception ex) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.POLE, id));
    }
  }

  @ApiOperation(value = "获取灯杆信息")
  @GetMapping(value = {"/{id}"})
  public WebResponse<Optional<Pole>> getPole(@PathVariable BigInteger id) {
    try {
      return WebResponse.success(poleRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "更新灯杆信息", httpMethod = "POST")
  @PostMapping(value = {"/{id}"})
  public WebResponse<Pole> setPole(@PathVariable BigInteger id, @RequestBody Pole pole) {
    try {
      String address = poleService.getServerAddress(id);
      if (StringUtils.isEmpty(address)) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, id));
      }

      WebResponse<Pole> remoteRet = restTemplateService.exchange(
        String.format("http://%s/api/poles/edge/%d", address, id),
        HttpMethod.POST,
        new HttpEntity<>(pole),
        new ParameterizedTypeReference<WebResponse<Pole>>() {
        }
      );
      if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
        return remoteRet;
      }
      Pole rPole = remoteRet.getData();
      Pole ret = poleRepository.save(rPole);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.POLE, id));
    }
  }

  @ApiOperation(value = "更新灯杆信息", httpMethod = "POST", hidden = true)
  @PostMapping(value = {"/edge/{id}"})
  public WebResponse<Pole> setEdgePole(@PathVariable BigInteger id, @RequestBody Pole pole) {
    try {
      Optional<Pole> tPole = poleRepository.findById(id);
      if (!tPole.isPresent()) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.POLE, id));
      }
      Pole poleTarget = tPole.get();
      poleTarget.setUpdatedAt(new Date());
      Pole ret = poleRepository.save(poleTarget);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.POLE, id));
    }
  }
  
  @ApiOperation(value = "删除灯杆信息")
  @DeleteMapping(value = {"/{id}"})
  public WebResponse<Map<String, BigInteger>> deletePole(@PathVariable BigInteger id) {
    try {
      Optional<Pole> ret = poleRepository.findById(id);
      if (!ret.isPresent()) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.POLE, id));
      }
      poleRepository.delete(ret.get());
      Map<String, BigInteger> map = new HashMap<>();
      map.put("Id", id);
      return WebResponse.success(map);
    } catch (Exception ex) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.POLE, id));
    }
  }
}
