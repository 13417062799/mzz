package com.distlab.hqsms.screen.plan;

import io.swagger.annotations.Api;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@Api(tags = "屏幕计划基础接口", description = "单个、批量查询计划基础信息")
@RepositoryRestResource(
  path = "screen-plan",
  collectionResourceRel = "screen-plans",
  collectionResourceDescription = @Description("屏幕设备播放计划数据列表"),
  itemResourceRel = "screen-plan",
  itemResourceDescription = @Description("屏幕设备播放计划数据")
)
public interface ScreenPlanRepository extends PagingAndSortingRepository<ScreenPlan, BigInteger> {
}
