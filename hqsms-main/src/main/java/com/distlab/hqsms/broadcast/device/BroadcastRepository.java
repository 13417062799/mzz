package com.distlab.hqsms.broadcast.device;

import com.distlab.hqsms.edge.DeviceRepository;
import io.swagger.annotations.Api;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Api(tags = {"广播设备"})
@RepositoryRestResource(
  exported = false,
  path = "broadcasts",
  collectionResourceRel = "broadcasts",
  collectionResourceDescription = @Description("广播设备列表"),
  itemResourceRel = "broadcast",
  itemResourceDescription = @Description("广播设备")
)
public interface BroadcastRepository extends DeviceRepository<Broadcast> {
}
