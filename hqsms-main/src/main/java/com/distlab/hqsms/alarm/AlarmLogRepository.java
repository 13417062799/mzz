package com.distlab.hqsms.alarm;

import com.distlab.hqsms.edge.DeviceLogRepository;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@Api(tags = {"报警器"})
@RepositoryRestResource(
  exported = false,
  path = "alarm-logs",
  collectionResourceRel = "alarm-logs",
  collectionResourceDescription = @Description("报警器数据列表"),
  itemResourceRel = "alarm-log",
  itemResourceDescription = @Description("报警器数据")
)
public interface AlarmLogRepository extends DeviceLogRepository<AlarmLog> {
}
