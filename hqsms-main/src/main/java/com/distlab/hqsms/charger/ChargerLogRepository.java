package com.distlab.hqsms.charger;

import com.distlab.hqsms.edge.DeviceLogRepository;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@Api("充电桩")
@RepositoryRestResource(
  exported = false,
  path = "charger-logs",
  collectionResourceRel = "charger-logs",
  collectionResourceDescription = @Description("充电桩设备数据列表"),
  itemResourceRel = "charger-log",
  itemResourceDescription = @Description("充电桩设备数据")
)
public interface ChargerLogRepository extends DeviceLogRepository<ChargerLog> {
}
