package com.distlab.hqsms.camera;

import com.distlab.hqsms.edge.DeviceLogRepository;
import io.swagger.annotations.Api;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Api(tags = {"摄像头"})
@RepositoryRestResource(
  exported = false,
  path = "camera-logs",
  collectionResourceRel = "camera-logs",
  collectionResourceDescription = @Description("摄像头设备录像"),
  itemResourceRel = "camera",
  itemResourceDescription = @Description("摄像头设备录像")
)
public interface CameraLogRepository extends DeviceLogRepository<CameraLog> {
  List<CameraLog> findByDeviceIdAndStartedAtBeforeAndEndedAtAfter(BigInteger id, Date startedAt, Date endedAt);
}
