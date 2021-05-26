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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Api(tags = {"边缘服务器"})
@RestController
@RequestMapping(path = "/servers")
public class ServerController {
  private final Logger logger = LoggerFactory.getLogger(ServerController.class);
  @Autowired
  private ServerRepository serverRepository;
  @Autowired
  private DeviceService deviceService;
  @Autowired
  private ServerService serverService;
  @Autowired
  private RestTemplateService restTemplateService;

  @ApiOperation(value = "获取边缘服务器列表")
  @GetMapping(value = {""})
  public WebResponse<Page<Server>> getAllServers(Pageable pageable) {
    try {
      return WebResponse.success(serverRepository.findAll(pageable));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "创建边缘服务器")
  @PostMapping(value = {""})
  public WebResponse<Map<String, BigInteger>> addServer(@RequestBody Server server) {
    BigInteger id = deviceService.getLeafId(DeviceService.LeafType.SERVER);
    if (id.equals(BigInteger.valueOf(-1))) {
      return WebResponse.fail(WebResponseEnum.SYS_GEN_ID_FAILED, MessageUtil.genIdFailed(MessageEnum.SERVER));
    }
    server.setId(id);
    server.setCreatedAt(new Date());
    server.setUpdatedAt(new Date());
    Map<String, BigInteger> map = new HashMap<>();
    map.put("Id", id);
    try {
      serverRepository.save(server);
      return WebResponse.success(map);
    } catch (Exception ex) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.SERVER, id));
    }
  }

  @ApiOperation(value = "获取边缘服务器信息")
  @GetMapping(value = {"/{id}"})
  public WebResponse<Optional<Server>> getServer(@PathVariable BigInteger id) {
    try {
      return WebResponse.success(serverRepository.findById(id));
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.UNKNOWN_ERROR, e.getCause().getLocalizedMessage());
    }
  }

  @ApiOperation(value = "更新边缘服务器信息", httpMethod = "POST")
  @PostMapping(value = {"/{id}"})
  public WebResponse<Server> setServer(@PathVariable BigInteger id, @RequestBody Server server) {
    try {
      String address = serverService.getServerAddress(id);
      if (StringUtils.isEmpty(address)) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER_IP, id));
      }

      WebResponse<Server> remoteRet = restTemplateService.exchange(
        String.format("http://%s/api/servers/edge/%d", address, id),
        HttpMethod.POST,
        new HttpEntity<>(server),
        new ParameterizedTypeReference<WebResponse<Server>>() {
        }
      );
      if (remoteRet.getCode() != WebResponseEnum.SUCCESS.getCode()) {
        return remoteRet;
      }
      Server rServer = remoteRet.getData();
      Server ret = serverRepository.save(rServer);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.SERVER, id));
    }
  }

  @ApiOperation(value = "更新边缘服务器信息", httpMethod = "POST", hidden = true)
  @PostMapping(value = {"/edge/{id}"})
  public WebResponse<Server> setEdgeServer(@PathVariable BigInteger id, @RequestBody Server server) {
    try {
      Optional<Server> tServer = serverRepository.findById(id);
      if (!tServer.isPresent()) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER, id));
      }
      Server serverTarget = tServer.get();
      serverTarget.setUpdatedAt(new Date());
      Server ret = serverRepository.save(serverTarget);
      return WebResponse.success(ret);
    } catch (Exception e) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.SERVER, id));
    }
  }

  @ApiOperation(value = "删除边缘服务器信息")
  @DeleteMapping(value = {"/{id}"})
  public WebResponse<Map<String, BigInteger>> deleteServer(@PathVariable BigInteger id) {
    try {
      Optional<Server> ret = serverRepository.findById(id);
      if (!ret.isPresent()) {
        return WebResponse.fail(WebResponseEnum.SYS_GET_DATA_FAILED, MessageUtil.notFound(MessageEnum.SERVER, id));
      }
      serverRepository.delete(ret.get());
      Map<String, BigInteger> map = new HashMap<>();
      map.put("Id", id);
      return WebResponse.success(map);
    } catch (Exception ex) {
      return WebResponse.fail(WebResponseEnum.SYS_SAVE_DATA_FAILED, MessageUtil.saveDataFailed(MessageEnum.SERVER, id));
    }
  }

}
