package com.distlab.hqsms.weather;

import com.distlab.hqsms.edge.DeviceRepository;
import io.swagger.annotations.Api;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Api(tags = {"气象传感器"})
@RepositoryRestResource(
  exported = false,
  path = "weathers",
  collectionResourceRel = "weathers",
  collectionResourceDescription = @Description("气象设备列表"),
  itemResourceRel = "weather",
  itemResourceDescription = @Description("气象设备")
)
public interface WeatherRepository extends DeviceRepository<Weather> {
}
