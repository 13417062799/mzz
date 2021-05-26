package com.distlab.hqsms.cloud;

import io.swagger.annotations.Api;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Api(tags = {"媒体文件"})
@RepositoryRestResource(
  exported = false,
  path = "media-files",
  collectionResourceRel = "media-files",
  collectionResourceDescription = @Description("媒体文件列表"),
  itemResourceRel = "media-file",
  itemResourceDescription = @Description("媒体文件")
)
public interface MediaFileRepository extends PagingAndSortingRepository<MediaFile, BigInteger> {
  List<MediaFile> findBySourceIdAndSourceIn(BigInteger sourceId, List<MediaFile.MediaFileSource> source);
  List<MediaFile> findBySource(MediaFile.MediaFileSource source);
}
