package com.distlab.hqsms.edge;

import io.swagger.annotations.Api;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@Api(tags = {"灯杆"})
@RepositoryRestResource(
  exported = false,
  path = "poles",
  collectionResourceRel = "poles",
  collectionResourceDescription = @Description("灯杆列表"),
  itemResourceRel = "pole",
  itemResourceDescription = @Description("灯杆")
)
public interface PoleRepository extends PagingAndSortingRepository<Pole, BigInteger> {
}
