package com.distlab.hqsms.screen.device;

import com.distlab.hqsms.edge.DeviceRepository;
import io.swagger.annotations.Api;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Api(tags = "屏幕设备基础接口", description = "单个、批量查询设备基础信息")
@RepositoryRestResource(
  path = "screen",
  collectionResourceRel = "screens",
  collectionResourceDescription = @Description("屏幕设备数据列表"),
  itemResourceRel = "screen",
  itemResourceDescription = @Description("屏幕设备数据")
)
public interface ScreenRepository extends DeviceRepository<Screen> {
}
