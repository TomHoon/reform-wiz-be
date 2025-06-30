package com.reform.wiz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.reform.wiz.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
  Page<BoardEntity> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

}
