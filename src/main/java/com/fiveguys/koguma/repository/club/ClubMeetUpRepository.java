package com.fiveguys.koguma.repository.club;

import com.fiveguys.koguma.data.entity.ClubMeetUp;
import com.fiveguys.koguma.data.entity.MeetUpType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClubMeetUpRepository extends JpaRepository<ClubMeetUp, Long> {

    List<ClubMeetUp> findByClubIdAndMeetUpType(Long clubId, MeetUpType meetUpType);

    // 스케줄 날짜 작업 필요
    @Query("select m from ClubMeetUp as m where m.meetDate < :endDate and m.meetUpType = :status")
    List<ClubMeetUp> findByChangeState(@Param("endDate") LocalDateTime endDate, @Param("status") String status);

    @Query("select COUNT(cmu) from ClubMeetUp cmu where cmu.club.id = :clubId and cmu.meetUpType = 'COMPLETE'")
    Long countActiveMeetUpsByClubId(@Param("clubId") Long clubId);


}
