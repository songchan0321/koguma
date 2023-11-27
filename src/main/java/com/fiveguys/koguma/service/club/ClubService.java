package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.ClubDTO;
import com.fiveguys.koguma.data.dto.ClubMemberDTO;

import java.util.List;

public interface ClubService {

    public Long addClub(ClubDTO clubDTO, Long memberId);

    public List<ClubDTO> listClub();

    public List<ClubDTO> listMyClub(Long memberId);

    public ClubDTO getClub(Long clubId);

    public void updateClub(ClubDTO clubDTO);

    public Long addClubMember(ClubMemberDTO clubMemberDTO);
}
