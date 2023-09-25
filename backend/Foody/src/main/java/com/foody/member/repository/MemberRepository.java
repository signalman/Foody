package com.foody.member.repository;

import com.foody.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.email= :email AND (m.height IS NULL OR m.weight IS NULL OR m.nickname IS NULL)")
    Member checkInfoIsNUll(@Param("email") String email);
}
