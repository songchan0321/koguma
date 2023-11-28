package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.ClubDTO;
import com.fiveguys.koguma.data.dto.ClubJoinRequestDTO;
import com.fiveguys.koguma.data.dto.ClubMemberDTO;
import com.fiveguys.koguma.data.entity.Club;
import com.fiveguys.koguma.data.entity.ClubMember;
import com.fiveguys.koguma.data.entity.ClubMemberJoinRequest;
import com.fiveguys.koguma.repository.club.ClubMemberJoinRequestRepository;
import com.fiveguys.koguma.repository.club.ClubMemberRepository;
import com.fiveguys.koguma.repository.club.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service @Transactional
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService{

    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubMemberJoinRequestRepository clubMemberJoinRequestRepository;


    @Override
    public Long addClub(ClubDTO clubDTO, ClubMemberDTO clubMemberDTO) {

        Club club = clubDTO.toEntity();
        ClubMember clubMember = clubMemberDTO.toEntity();

        //모임 생성 등록
        Club clubId = clubRepository.save(club);

        //모임장 등록
        ClubMember savedClubMember = clubMemberRepository.save(clubMember);

        //모임장 권한 부여
        savedClubMember.updateClubMemberRole();

        return club.getId();
    }

    @Override
    public List<ClubDTO> listClub(Double latitude, Double longitude) {

        //모임 리스트 조회
        List<Club> clubs = clubRepository.findClubsByLocation(latitude, longitude);

        // Entity -> Dto로 전환
        List<ClubDTO> clubDTOS = clubs.stream()
                .map((o) -> new ClubDTO(o))
                .collect(Collectors.toList());

        return clubDTOS;
    }

    @Override
    public List<ClubDTO> listMyClub(Long memberId) {

        // 내 모임 리스트 조회
        List<ClubMember> clubMembers = clubMemberRepository.findByMemberId(memberId);

        // clubMember -> club 으로 전환
        List<Club> clubs = clubMembers.stream()
                .map(ClubMember::getClub)
                .collect(Collectors.toList());

        // club -> clubDto로 전환
        List<ClubDTO> clubDTOS = clubs.stream()
                .map((c)-> new ClubDTO(c))
                .collect(Collectors.toList());

        return clubDTOS;
    }

    @Override
    public ClubDTO getClub(Long clubId) {

        // 모임 조회
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 없습니다."));

        // DTO -> Entity return
        return ClubDTO.fromEntity(club);
    }

    @Override
    public void updateClub(ClubDTO clubDTO) {

        // 모임 정보 찾기
        Club club = clubRepository.findById(clubDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 클럽 없습니다."));

        // 모임 업데이트 저장
        club.updateClub(clubDTO);
    }


    @Override
    public Long addClubMember(ClubMemberDTO clubMemberDTO) {

        ClubMember clubMember = clubMemberDTO.toEntity();

        //모임원 등록
        ClubMember saveClubMember = clubMemberRepository.save(clubMember);

        return saveClubMember.getId();
    }

    @Override
    public Long addJoinRequestClub(ClubJoinRequestDTO clubJoinRequestDTO) {

        ClubMemberJoinRequest clubMemberJoinRequest = clubJoinRequestDTO.toEntity();

        //save repo
        clubMemberJoinRequestRepository.save(clubMemberJoinRequest);

        return clubMemberJoinRequest.getId();
    }

}
