package com.reform.wiz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reform.wiz.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findById(String id);

    Optional<MemberEntity> findByPhone(String phone);
}
