package com.fiveguys.koguma.repository.club;


import com.fiveguys.koguma.data.entity.ClubMemberJoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubMemberJoinRequestRepository extends JpaRepository<ClubMemberJoinRequest, Long> {
}
