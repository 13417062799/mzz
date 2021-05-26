package com.distlab.hqsms.wifi;

import com.distlab.hqsms.edge.DeviceRepository;
import io.swagger.annotations.Api;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@Api("无线网络")
@RepositoryRestResource(
  exported = false,
  path = "wifi-aps",
  collectionResourceRel = "wifi-aps",
  collectionResourceDescription = @Description("无线网络AP设备列表"),
  itemResourceRel = "wifi-ap",
  itemResourceDescription = @Description("无线网络AP设备")
)
public interface WifiAPRepository extends DeviceRepository<WifiAP> {
  Optional<WifiAP> findByMac(String mac);
}
