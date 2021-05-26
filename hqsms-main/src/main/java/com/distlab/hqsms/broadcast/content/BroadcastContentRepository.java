package com.distlab.hqsms.broadcast.content;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.util.Collection;

@Api(tags = {"广播内容"})
@RepositoryRestResource(
  exported = false,
  path = "broadcast-contents",
  collectionResourceRel = "broadcast-contents",
  collectionResourceDescription = @Description("广播内容列表"),
  itemResourceRel = "broadcast-content",
  itemResourceDescription = @Description("广播内容")
)
public interface BroadcastContentRepository extends PagingAndSortingRepository<BroadcastContent, BigInteger> {
  Page<BroadcastContent> findByContentNameLike(String name, Pageable pageable);
  Page<BroadcastContent> findByIdIn(Collection<BigInteger> id, Pageable pageable);
}
