package com.Hanium.CarCamping.repository;

import com.Hanium.CarCamping.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
   Optional<Member> findByEmail(String email);
   boolean existsByEmail(String email);
   Optional<Member> findByNickname(String name);
   boolean existsByNickname(String nickname);
}
