package com.fiveguys.koguma.repository.club;

import com.fiveguys.koguma.data.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    List<ClubMember> findAllByMemberId(Long memberId);

    List<ClubMember> findByClubId(Long clubId);

    Optional<ClubMember> findByClubIdAndMemberId(Long clubId, Long memberId);

    Boolean existsByClubIdAndMemberId(Long clubId, Long memberId);
}
