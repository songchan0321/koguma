package com.fiveguys.koguma.repository.club;

import com.fiveguys.koguma.data.entity.ClubMemberMeetUpJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubMemberMeetUpJoinRepository extends JpaRepository<ClubMemberMeetUpJoin, Long> {

    @Query("select cm from ClubMemberMeetUpJoin cm where cm.clubMeetUp = :meetUpId and cm.clubMember = :clubMemberId")
    ClubMemberMeetUpJoin findByMeetUpJoinMember(@Param("meetUpId") Long meetUpId, @Param("clubMemberId") Long clubMemberId);

    List<ClubMemberMeetUpJoin> findAllByClubMeetUpId(Long clubMeetUpId);
}
