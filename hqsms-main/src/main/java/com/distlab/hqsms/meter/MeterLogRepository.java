package com.distlab.hqsms.meter;

import com.distlab.hqsms.edge.DeviceLogRepository;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@Api(tags = {"电能表"})
@RepositoryRestResource(
  exported = false,
  path = "meter-logs",
  collectionResourceRel = "meter-logs",
  collectionResourceDescription = @Description("电能表数据列表"),
  itemResourceRel = "meter-log",
  itemResourceDescription = @Description("电能表数据")
)
public interface MeterLogRepository extends DeviceLogRepository<MeterLog> {
}
