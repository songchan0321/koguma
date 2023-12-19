package com.fiveguys.koguma.repository.club;

import com.fiveguys.koguma.data.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    List<ClubMember> findAllByMemberId(Long memberId);

    List<ClubMember> findByClubId(Long clubId);

    Optional<ClubMember> findByClubIdAndMemberId(Long clubId, Long memberId);

    Boolean existsByClubIdAndMemberId(Long clubId, Long memberId);

//    @Query("SELECT COUNT(m) FROM ClubMember c JOIN c.member m WHERE c.club = :clubId")
//    Integer countClubMembersByClubId(@Param("clubId") Long clubId);

    Integer countByClubId(Long clubId);
}
