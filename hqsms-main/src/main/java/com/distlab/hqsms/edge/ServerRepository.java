package com.distlab.hqsms.edge;

import io.swagger.annotations.Api;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@Api(tags = {"边缘服务器"})
@RepositoryRestResource(
  exported = false,
  path = "servers",
  collectionResourceRel = "servers",
  collectionResourceDescription = @Description("边缘服务器列表"),
  itemResourceRel = "server",
  itemResourceDescription = @Description("边缘服务器")
)
public interface ServerRepository extends PagingAndSortingRepository<Server, BigInteger> {
}
