package com.fiveguys.koguma.repository.club;

import com.fiveguys.koguma.data.entity.ClubMeetUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubMeetUpRepository extends JpaRepository<ClubMeetUp, Long> {

    List<ClubMeetUp> findByClubId(Long clubMeetUpId);
}
