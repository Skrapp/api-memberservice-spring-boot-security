package com.nilsson.wigellApi.repository;

import com.nilsson.wigellApi.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MemberRepo extends JpaRepository<Member,Long> {
    boolean existsByDateOfBirth(LocalDate dateOfBirth);
}
