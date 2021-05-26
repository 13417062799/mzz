package com.distlab.hqsms.broadcast.plan;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@Api(tags = "广播计划")
@RepositoryRestResource(
  exported = false,
  path = "broadcast-plans",
  collectionResourceRel = "broadcast-plans",
  collectionResourceDescription = @Description("广播计划列表"),
  itemResourceRel = "broadcast-plan",
  itemResourceDescription = @Description("广播计划")
)
public interface BroadcastPlanRepository extends PagingAndSortingRepository<BroadcastPlan, BigInteger> {
  Page<BroadcastPlan> findByPlanNameLike(String name, Pageable pageable);
}
