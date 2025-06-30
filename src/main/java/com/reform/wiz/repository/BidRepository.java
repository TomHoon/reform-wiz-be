package com.reform.wiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reform.wiz.entity.BidEntity;

public interface BidRepository extends JpaRepository<BidEntity, Long> {
  List<BidEntity> findByBoardEntity_Bno(Long bno);

}
