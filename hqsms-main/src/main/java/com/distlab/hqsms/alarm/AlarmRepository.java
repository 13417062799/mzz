package com.distlab.hqsms.alarm;

import com.distlab.hqsms.edge.DeviceRepository;
import io.swagger.annotations.Api;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Api(tags = {"报警器"})
@RepositoryRestResource(
  exported = false,
  path = "alarms",
  collectionResourceRel = "alarms",
  collectionResourceDescription = @Description("报警器设备列表"),
  itemResourceRel = "alarm",
  itemResourceDescription = @Description("报警器设备")
)
public interface AlarmRepository extends DeviceRepository<Alarm> {
}
