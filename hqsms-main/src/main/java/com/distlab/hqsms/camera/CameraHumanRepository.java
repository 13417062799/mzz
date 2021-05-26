package com.distlab.hqsms.camera;

import com.distlab.hqsms.edge.DeviceLogRepository;
import io.swagger.annotations.Api;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@Api(tags = {"摄像头"})
@RepositoryRestResource(
  exported = false,
  path = "camera-humans",
  collectionResourceRel = "camera-humans",
  collectionResourceDescription = @Description("摄像头识别人员数据列表"),
  itemResourceRel = "camera-human",
  itemResourceDescription = @Description("摄像头识别人员数据")
)
public interface CameraHumanRepository extends DeviceLogRepository<CameraHuman> {
}
