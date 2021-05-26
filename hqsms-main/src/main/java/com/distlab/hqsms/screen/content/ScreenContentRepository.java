package com.distlab.hqsms.screen.content;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Api(tags = "屏幕内容基础接口", description = "单个、批量查询内容基础信息")
@RepositoryRestResource(
  path = "screen-content",
  collectionResourceRel = "screen-contents",
  collectionResourceDescription = @Description("屏幕播放内容数据列表"),
  itemResourceRel = "screen-content",
  itemResourceDescription = @Description("屏幕设备播放内容数据")
)
public interface ScreenContentRepository extends PagingAndSortingRepository<ScreenContent, BigInteger> {
  List<ScreenContent> findContentById(BigInteger id);

}
