package com.distlab.hqsms.charger;

import com.distlab.hqsms.edge.DeviceRepository;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Api("充电桩")
@RepositoryRestResource(
  exported = false,
  path = "chargers",
  collectionResourceRel = "chargers",
  collectionResourceDescription = @Description("充电桩设备列表"),
  itemResourceRel = "charger",
  itemResourceDescription = @Description("充电桩设备")
)
public interface ChargerRepository extends DeviceRepository<Charger>{
}
