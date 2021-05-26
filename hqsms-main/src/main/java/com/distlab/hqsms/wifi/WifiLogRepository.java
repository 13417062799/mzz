package com.distlab.hqsms.wifi;

import com.distlab.hqsms.edge.DeviceLogRepository;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Api("无线网络")
@RepositoryRestResource(
  exported = false,
  path = "wifi-logs",
  collectionResourceRel = "wifi-logs",
  collectionResourceDescription = @Description("无线网络数据列表"),
  itemResourceRel = "wifi-log",
  itemResourceDescription = @Description("无线网络数据")
)
public interface WifiLogRepository extends DeviceLogRepository<WifiLog> {
  List<WifiLog> findByUserMac(String userMac);
  List<WifiLog> findByOffType(Integer offType);
  Page<WifiLog> findByLoginBetween(Date start, Date end, Pageable pageable);
  Page<WifiLog> findByOffType(Integer offType, Pageable pageable);
}
