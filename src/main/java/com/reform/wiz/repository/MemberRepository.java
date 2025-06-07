package com.reform.wiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reform.wiz.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

}
