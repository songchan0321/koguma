package com.fiveguys.koguma.repository.member;

import com.fiveguys.koguma.data.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

        boolean existsByNicknameAndActiveFlag(String nickname, Boolean activeFlag);
        Optional<Member> findByNicknameAndActiveFlag(String nickname, Boolean activeFlag);
        boolean existsByIdAndActiveFlag(Long Id, Boolean activeFlag);
        Optional<Member> findByIdAndActiveFlag(Long id, Boolean activeFlag);


}