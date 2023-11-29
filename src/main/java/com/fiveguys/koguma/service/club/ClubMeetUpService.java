package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.ClubMeetUpDTO;

import java.util.List;

public interface ClubMeetUpService {

    public Long addClubMeetUp(ClubMeetUpDTO clubMeetUpDTO, Long clubId);

    public ClubMeetUpDTO getClubMeetUp(Long clubMeetUpId);

    public List<ClubMeetUpDTO> listClubMeetUp(Long clubId);

    public void updateClubMeetUp(ClubMeetUpDTO clubMeetUpDTO);

    public void deleteClubMeetUp(Long clubMeetUpId);

    public Long joinClubMeetUp(Long clubMeetUpId, Long clubMemberId);

}
