package com.distlab.hqsms.strategy;

import io.swagger.annotations.Api;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.util.List;

@Api(tags = {"策略"})
@RepositoryRestResource(
  exported = false,
  path = "rules",
  collectionResourceRel = "rules",
  collectionResourceDescription = @Description("策略列表"),
  itemResourceRel = "rule",
  itemResourceDescription = @Description("策略")
)
public interface RuleRepository extends PagingAndSortingRepository<Rule, BigInteger> {
  List<Rule> findByDeviceIdAndDeviceTypeAndDeviceLogTypeOrderByLevelAsc(BigInteger deviceId, Rule.RuleDeviceType device, Rule.RuleDeviceLogType deviceLogType);
}
