package com.distlab.hqsms.light;

import com.distlab.hqsms.edge.DeviceRepository;
import io.swagger.annotations.Api;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Api(tags = {"灯控"})
@RepositoryRestResource(
  exported = false,
  path = "lights",
  collectionResourceRel = "lights",
  collectionResourceDescription = @Description("灯控设备列表"),
  itemResourceRel = "light",
  itemResourceDescription = @Description("灯控设备")
)
public interface LightRepository extends DeviceRepository<Light> {
}
