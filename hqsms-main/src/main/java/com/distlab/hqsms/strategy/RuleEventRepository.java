package com.distlab.hqsms.strategy;

import io.swagger.annotations.Api;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@Api(tags = {"策略"})
@RepositoryRestResource(
  exported = false,
  path = "rule-events",
  collectionResourceRel = "rule-events",
  collectionResourceDescription = @Description("策略事件列表"),
  itemResourceRel = "rule-event",
  itemResourceDescription = @Description("策略事件")
)
public interface RuleEventRepository extends PagingAndSortingRepository<RuleEvent, BigInteger> {
}
