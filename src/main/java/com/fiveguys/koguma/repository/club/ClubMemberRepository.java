package com.fiveguys.koguma.repository.club;

import com.fiveguys.koguma.data.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    List<ClubMember> findByMemberId(Long memberId);

    List<ClubMember> findByClubId(Long clubId);
}
