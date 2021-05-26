package com.distlab.hqsms.light;

import com.distlab.hqsms.edge.DeviceLogRepository;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@Api(tags = {"灯控"})
@RepositoryRestResource(
  exported = false,
  path = "light-logs",
  collectionResourceRel = "light-logs",
  collectionResourceDescription = @Description("灯控数据列表"),
  itemResourceRel = "light-log",
  itemResourceDescription = @Description("灯控数据")
)
public interface LightLogRepository extends DeviceLogRepository<LightLog> {
}
