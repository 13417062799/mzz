package com.distlab.hqsms.camera;

import com.distlab.hqsms.edge.DeviceLogRepository;
import io.swagger.annotations.Api;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Api(tags = {"摄像头"})
@RepositoryRestResource(
  exported = false,
  path = "camera-vehicles",
  collectionResourceRel = "camera-vehicles",
  collectionResourceDescription = @Description("摄像头识别车辆数据列表"),
  itemResourceRel = "camera-vehicle",
  itemResourceDescription = @Description("摄像头识别车辆数据")
)
public interface CameraVehicleRepository extends DeviceLogRepository<CameraVehicle> {
}
