package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.*;
import com.fiveguys.koguma.data.dto.club.GetClubMemberDTO;
import com.fiveguys.koguma.data.dto.club.ListClubByCategoryDTO;
import com.fiveguys.koguma.data.dto.club.NearClubDTO;

import java.util.List;

public interface ClubService {

    public Long addClub(CreateClubDTO createClubDTO, Long memberId);

    public List<ClubDTO> listClub(Double latitude, Double longitude);

    public List<ListClubByCategoryDTO> listClub();

    public List<ListClubByCategoryDTO> listMyClub(Long memberId);

//    public List<ListClubByCategoryDTO> listClubByCategory(Long categoryId);
    public List<ListClubByCategoryDTO> listClubByCategory(LocationDTO locationDTO,  String keyword, Long categoryId) throws Exception;

    public ClubDTO getClub(Long clubId);

    public ListClubByCategoryDTO findClub(Long clubId);

    public void updateClub(ClubDTO clubDTO);

    public Long addJoinRequestClub(ClubJoinRequestDTO clubJoinRequestDTO, Long memberId);

    public ClubJoinRequestDTO getClubJoinRequest(Long clubJoinRequestId);

    public List<ClubJoinRequestDTO> listClubJoinRequest(Long clubId);

    public Long acceptJoinRequest(Long clubJoinRequestId);

    public void rejectJoinRequest(Long cmJoinRequestId);

    public void deleteJoinRequest(Long clubId, Long memberId);

    public Long addClubMember(ClubMemberDTO clubMemberDTO);

    public GetClubMemberDTO getClubMember(Long clubId, Long memberId);

    public ClubMemberDTO getClubMember(Long clubMemberId);

    public List<ClubMemberDTO> listClubMember(Long clubId);

    public Integer countClubMember(Long clubId);
    public void updateClubMember(ClubMemberDTO clubMemberDTO);

    public void changeClubLeader(Long currentLeaderId, Long newLeaderId);

    public GetClubMemberDTO checkJoinClub(Long clubId, Long memberId);

    public Boolean checkJoinRequest(Long clubId, Long memberId);

}
