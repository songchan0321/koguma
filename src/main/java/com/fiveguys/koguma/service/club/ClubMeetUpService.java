package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.ClubMeetUpDTO;
import com.fiveguys.koguma.data.dto.ClubMemberMeetUpJoinDTO;
import com.fiveguys.koguma.data.dto.club.CreateClubMeetUpDTO;

import java.util.List;

public interface ClubMeetUpService {

    public Long addClubMeetUp(CreateClubMeetUpDTO clubMeetUpDTO, Long clubId);

    public ClubMeetUpDTO getClubMeetUp(Long clubMeetUpId);

    public List<ClubMeetUpDTO> listClubMeetUp(Long clubId, String meetUpType);

    public void updateClubMeetUp(Long meetUpId,ClubMeetUpDTO clubMeetUpDTO);

    public void deleteClubMeetUp(Long leaderId,Long clubMeetUpId);

    public Long joinClubMeetUp(Long clubMeetUpId, Long clubMemberId);

    public void cancel(Long clubMeetUpId, Long clubMemberId);

    public List<ClubMemberMeetUpJoinDTO> listClubMeetUpMember(Long meetUpId);

    public void deleteMeetupJoinMember(Long meetUpId, Long joinMemberId, Long leaderId);

    public void changeMeetUpState();

    public boolean checkJoinMeetUp(Long meetUpId,Long clubMemberId);

}
