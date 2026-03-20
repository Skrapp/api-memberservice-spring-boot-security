package com.nilsson.wigellApi.repository;

import com.nilsson.wigellApi.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo extends JpaRepository<Member,Long> {
}
