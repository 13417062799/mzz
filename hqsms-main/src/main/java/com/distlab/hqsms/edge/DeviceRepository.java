package com.distlab.hqsms.edge;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public interface DeviceRepository<E> extends PagingAndSortingRepository<E, BigInteger>, JpaSpecificationExecutor<E> {
  Page<E> findByPoleIdIn(Collection<BigInteger> poleIds, Pageable pageable);
  Page<E> findBySupplierLike(String supplier, Pageable pageable);
  Page<E> findByIsOn(Device.DeviceOn isOn, Pageable pageable);
  Page<E> findByCodeLike(String code, Pageable pageable);
  Page<E> findByModelLike(String model, Pageable pageable);
  Page<E> findByNameLike(String name, Pageable pageable);
  Page<E> findByCreatedAtBetween(Date start, Date end, Pageable pageable);
}
