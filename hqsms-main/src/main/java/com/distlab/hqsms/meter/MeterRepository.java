package com.distlab.hqsms.meter;

import com.distlab.hqsms.edge.DeviceRepository;
import io.swagger.annotations.Api;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Api(tags = {"电能表"})
@RepositoryRestResource(
  exported = false,
  path = "meters",
  collectionResourceRel = "meters",
  collectionResourceDescription = @Description("电能表设备列表"),
  itemResourceRel = "meter",
  itemResourceDescription = @Description("电能表设备")
)
public interface MeterRepository extends DeviceRepository<Meter> {

}
