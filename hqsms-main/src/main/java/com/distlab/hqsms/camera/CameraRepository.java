package com.distlab.hqsms.camera;

import com.distlab.hqsms.edge.DeviceRepository;
import io.swagger.annotations.Api;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@Api(tags = {"摄像头"})
@RepositoryRestResource(
  exported = false,
  path = "cameras",
  collectionResourceRel = "cameras",
  collectionResourceDescription = @Description("摄像头设备列表"),
  itemResourceRel = "camera",
  itemResourceDescription = @Description("摄像头设备")
)
public interface CameraRepository extends DeviceRepository<Camera> {
}
