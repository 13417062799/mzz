package com.distlab.hqsms.edge;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigInteger;
import java.util.Date;

public interface DeviceLogRepository<E> extends PagingAndSortingRepository<E, BigInteger>, JpaSpecificationExecutor<E> {
  Page<E> findByCreatedAtBetween(Date start, Date end, Pageable pageable);
}
