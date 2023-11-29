package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.ClubDTO;
import com.fiveguys.koguma.data.dto.ClubJoinRequestDTO;
import com.fiveguys.koguma.data.dto.ClubMemberDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Club;
import com.fiveguys.koguma.data.entity.ClubMember;
import com.fiveguys.koguma.data.entity.ClubMemberJoinRequest;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.repository.club.ClubMemberJoinRequestRepository;
import com.fiveguys.koguma.repository.club.ClubMemberRepository;
import com.fiveguys.koguma.repository.club.ClubRepository;
import com.fiveguys.koguma.service.member.MemberService;
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
    private final MemberService memberService;


    @Override
    public Long addClub(ClubDTO clubDTO, ClubMemberDTO clubMemberDTO) {

        //모임 생성 등록
        ClubDTO savedClubDTO = ClubDTO.fromEntity(clubRepository.save(clubDTO.toEntity()));

        //clubMemberDTO에 저장된 Club DTO set
        clubMemberDTO.setClubDTO(savedClubDTO);

        //모임장 권한 부여
        clubMemberDTO.setMemberRole(true);

        //clubMember 추가
        this.addClubMember(clubMemberDTO);

        return savedClubDTO.getId();
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
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 없습니다."));

        // 모임 업데이트 저장
        club.updateClub(clubDTO);
    }


    @Override
    public Long addJoinRequestClub(ClubJoinRequestDTO clubJoinRequestDTO) {

        ClubMemberJoinRequest clubMemberJoinRequest = clubJoinRequestDTO.toEntity();
        //save repo
        clubMemberJoinRequestRepository.save(clubMemberJoinRequest);

        return clubMemberJoinRequest.getId();
    }

    @Override
    public ClubJoinRequestDTO getClubJoinRequest(Long clubJoinRequestId) {

        // 모임 가입 요청 상세 조회
        ClubMemberJoinRequest clubMemberJoinRequest= clubMemberJoinRequestRepository.findById(clubJoinRequestId)
                .orElseThrow(()-> new IllegalArgumentException("해당 가입요청이 없습니다."));

        return ClubJoinRequestDTO.fromEntity(clubMemberJoinRequest);
    }

    @Override
    public List<ClubJoinRequestDTO> listClubJoinRequest(Long clubId) {

        List<ClubMemberJoinRequest> clubMemberJoinRequests = clubMemberJoinRequestRepository.findByClubId(clubId);

        List<ClubJoinRequestDTO> clubJoinRequestDTOS = clubMemberJoinRequests.stream()
                .map((c) -> new ClubJoinRequestDTO(c))
                .collect(Collectors.toList());

        return clubJoinRequestDTOS;
    }

    @Override
    public Long acceptJoinRequest(Long cmJoinRequestId) {

        // 가입 요청 조회
        ClubMemberJoinRequest cmJoinRequest = clubMemberJoinRequestRepository.findById(cmJoinRequestId).get();

        //모임원 엔티티 생성
        ClubMember clubMember = ClubMember.createClubMember(cmJoinRequest.getClub(), cmJoinRequest.getMember(), cmJoinRequest.getNickname(), cmJoinRequest.getContent());

        //모임원 저장
        clubMemberRepository.save(clubMember);

        //가입 요청 삭제 -> controller로 나갈 수 있음 하단 deleteService 하단 생성
        clubMemberJoinRequestRepository.deleteById(cmJoinRequestId);

        return clubMember.getId();
    }

    @Override
    public void rejectJoinRequest(Long cmJoinRequestId) {

        // 가입 요청 조회
        ClubMemberJoinRequest cmJoinRequest = clubMemberJoinRequestRepository.findById(cmJoinRequestId).get();

        // 가입 요청 거절로 삭제 -> controller로 나갈 수 있음 하단 deleteService 하단 생성
        clubMemberJoinRequestRepository.deleteById(cmJoinRequestId);
    }

    @Override
    public void deleteJoinRequest(Long cmJoinRequestId) {
        clubMemberJoinRequestRepository.deleteById(cmJoinRequestId);
    }



    @Override
    public Long addClubMember(ClubMemberDTO clubMemberDTO) {

        clubMemberDTO.setActiveFlag(true);
        ClubMember clubMember = clubMemberDTO.toEntity();

        //모임원 등록
        ClubMember saveClubMember = clubMemberRepository.save(clubMember);

        return saveClubMember.getId();
    }

    @Override
    public ClubMemberDTO getClubMember(Long clubMemberId) {

        //모임원 조회
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId)
                .orElseThrow(() -> new IllegalArgumentException("모임원이 없습니다."));

        return ClubMemberDTO.fromEntity(clubMember);
    }

    @Override
    public List<ClubMemberDTO> listClubMember(Long clubId) {

        // 모임원 리스트 조회
        List<ClubMember> clubMembers = clubMemberRepository.findByClubId(clubId);

        return clubMembers.stream()
                .map((c) -> new ClubMemberDTO(c))
                .collect(Collectors.toList());
    }

    @Override
    public void updateClubMember(ClubMemberDTO clubMemberDTO) {

    }

    @Override
    public void changeClubLeader(Long currentLeaderId, Long newLeaderId) {

        ClubMember currentLeader = clubMemberRepository.findById(currentLeaderId)
                .orElseThrow(()-> new IllegalArgumentException("해당 회원이 없습니다."));

        ClubMember newLeader = clubMemberRepository.findById(newLeaderId)
                .orElseThrow(()-> new IllegalArgumentException("해당 회원이 없습니다."));

        currentLeader.demoteClubMember();
        newLeader.promoteClubLeader();
    }

}
