package com.reform.wiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reform.wiz.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

}
