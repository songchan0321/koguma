package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.ClubDTO;
import com.fiveguys.koguma.data.dto.ClubJoinRequestDTO;
import com.fiveguys.koguma.data.dto.ClubMemberDTO;

import java.util.List;

public interface ClubService {

    public Long addClub(ClubDTO clubDTO, ClubMemberDTO clubMemberDTO);

    public List<ClubDTO> listClub(Double latitude, Double longitude);

    public List<ClubDTO> listMyClub(Long memberId);

    public ClubDTO getClub(Long clubId);

    public void updateClub(ClubDTO clubDTO);

    public Long addJoinRequestClub(ClubJoinRequestDTO clubJoinRequestDTO);

    public ClubJoinRequestDTO getClubJoinRequest(Long clubJoinRequestId);

    public List<ClubJoinRequestDTO> listClubJoinRequest(Long clubId);

    public Long acceptJoinRequest(Long clubJoinRequestId);

    public void rejectJoinRequest(Long cmJoinRequestId);

    public void deleteJoinRequest(Long cmJoinRequestId);

    public Long addClubMember(ClubMemberDTO clubMemberDTO);

    public ClubMemberDTO getClubMember(Long clubMemberId);

    public List<ClubMemberDTO> listClubMember(Long clubId);

    public void updateClubMember(ClubMemberDTO clubMemberDTO);

    public void changeClubLeader(Long currentLeaderId, Long newLeaderId);

}
