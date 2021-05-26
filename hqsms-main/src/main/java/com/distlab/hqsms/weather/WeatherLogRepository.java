package com.distlab.hqsms.weather;

import com.distlab.hqsms.edge.DeviceLogRepository;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.util.Optional;

@Api(tags = {"气象传感器"})
@RepositoryRestResource(
  exported = false,
  path = "weather-logs",
  collectionResourceRel = "weather-logs",
  collectionResourceDescription = @Description("气象数据列表"),
  itemResourceRel = "weather-log",
  itemResourceDescription = @Description("气象数据")
)
public interface WeatherLogRepository extends DeviceLogRepository<WeatherLog> {
}