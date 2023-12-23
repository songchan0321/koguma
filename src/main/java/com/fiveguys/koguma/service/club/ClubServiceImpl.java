package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.*;
import com.fiveguys.koguma.data.dto.club.GetClubMemberDTO;
import com.fiveguys.koguma.data.dto.club.ListClubByCategoryDTO;
import com.fiveguys.koguma.data.entity.*;
import com.fiveguys.koguma.repository.club.ClubMemberJoinRequestRepository;
import com.fiveguys.koguma.repository.club.ClubMemberRepository;
import com.fiveguys.koguma.repository.club.ClubRepository;
import com.fiveguys.koguma.service.common.CategoryService;
import com.fiveguys.koguma.service.common.ImageService;
import com.fiveguys.koguma.service.common.LocationService;
import com.fiveguys.koguma.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Transactional
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService{

    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubMemberJoinRequestRepository clubMemberJoinRequestRepository;
    private final MemberService memberService;
    private final LocationService locationService;
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final ClubPostCategoryService clubPostCategoryService;



    @Override
    public Long addClub(CreateClubDTO ccd, Long memberId) {

        MemberDTO memberDto = memberService.getMember(memberId);

        LocationDTO lorepo = locationService.getMemberRepLocation(memberId);

        CategoryDTO category = categoryService.getCategory(ccd.getCategoryId());




        Club savedClub = Club.createClub(ccd.getTitle(), ccd.getContent(), ccd.getMaxCapacity(),
                lorepo.getLatitude(), lorepo.getLatitude(), lorepo.getDong(),
                category.getName(), category.toEntity());

        Club save = clubRepository.save(savedClub);

        ClubDTO clubDTO = ClubDTO.fromEntity(save);

        List<ImageDTO> imageDTOList = imageService.createImageDTOList(clubDTO, ccd.getUrls(), ImageType.CLUB);

        imageService.addImage(imageDTOList);


        ClubMemberDTO clubMemberDTO = ClubMemberDTO.builder()
                .memberDTO(memberDto)
                .clubDTO(ClubDTO.fromEntity(savedClub))
                .content(ccd.getMemberContent())
                .nickname(ccd.getNickname())
                .memberRole(true)
                .build();


        //clubMember 추가
        Long clubMemberId = this.addClubMember(clubMemberDTO);

        clubPostCategoryService.defaultClubPostCategory(clubDTO.getId(), "자유게시판");


        return savedClub.getId();
    }

    @Override
    public List<ClubDTO> listClub(Double latitude, Double longitude) {

        //모임 리스트 조회
        List<Club> clubs = clubRepository.findClubsByLocation(latitude, longitude);


        System.out.println("clubs = " + clubs.get(0));
        // Entity -> Dto로 전환
        List<ClubDTO> clubDTOS = clubs.stream()
                .map(ClubDTO::new)
                .collect(Collectors.toList());

        return clubDTOS;
    }

    @Override
    public List<ClubDTO> listClub() {

        List<Club> all = clubRepository.findAll();
        return all.stream()
                .map((c)-> ClubDTO.fromEntity(c))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClubDTO> listMyClub(Long memberId) {

        // 내 모임 리스트 조회
        List<ClubMember> clubMembers = clubMemberRepository.findAllByMemberId(memberId);

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
    public List<ListClubByCategoryDTO> listClubByCategory(Long categoryId) {

        List<ListClubByCategoryDTO> collect = clubRepository.findClubsByCategoryId(categoryId).stream()
                .map(ListClubByCategoryDTO::fromEntity)
                .collect(Collectors.toList());



        System.out.println("collect = " + collect.get(0));

        return collect;
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
    public ListClubByCategoryDTO findClub(Long clubId) {

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 없습니다."));

        return ListClubByCategoryDTO.fromEntity(club);
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
    public Long addJoinRequestClub(ClubJoinRequestDTO cjrDTO, Long memberId) {

        MemberDTO requestMember = memberService.getMember(memberId);

        ClubDTO clubDTO = this.getClub(cjrDTO.getClubDTO().getId());

        ClubMemberJoinRequest clubMemberJoinRequest = ClubMemberJoinRequest.builder()
                .member(requestMember.toEntity())
                .club(clubDTO.toEntity())
                .nickname(cjrDTO.getNickname())
                .content(cjrDTO.getContent())
                .activeFlag(true)
                .build();

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
    public void deleteJoinRequest(Long clubId, Long memberId) {
        clubMemberJoinRequestRepository.deleteByClubIdAndMemberId(clubId, memberId);
    }



    @Override
    public Long addClubMember(ClubMemberDTO clubMemberDTO) {

        clubMemberDTO.setActiveFlag(true);
        ClubMember clubMember = clubMemberDTO.toEntity();

        System.out.println("clubMemberDTO = " + clubMemberDTO);

        //모임원 등록
        ClubMember saveClubMember = clubMemberRepository.save(clubMember);

        return saveClubMember.getId();
    }

    @Override
    public GetClubMemberDTO getClubMember(Long clubId, Long memberId) {

        MemberDTO memberDTO = memberService.getMember(memberId);


        ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, memberId)
                .orElseThrow(() -> new IllegalStateException("모임원에 구성되지 않았습니다."));

        GetClubMemberDTO clubMemberDTO = GetClubMemberDTO.fromEntity(clubMember);

        clubMemberDTO.setProfileURL(memberDTO.getProfileURL());
        return clubMemberDTO;

    }

    @Override
    public ClubMemberDTO getClubMember(Long clubMemberId) {
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId).orElseThrow(() -> new IllegalArgumentException("모임원이 없습니다."));

        return ClubMemberDTO.fromEntity(clubMember);
    }


    @Override
    public List<ClubMemberDTO> listClubMember(Long clubId) {

        // 모임원 리스트 조회
        List<ClubMember> clubMembers = clubMemberRepository.findByClubId(clubId);

        return clubMembers.stream()
                .map(ClubMemberDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Integer countClubMember(Long clubId) {
        return clubMemberRepository.countByClubId (clubId);
    }

    @Override
    public void updateClubMember(ClubMemberDTO clubMemberDTO) {
        ClubMember clubMember = clubMemberRepository.findById(clubMemberDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("모임원 없습니다."));

        clubMember.updateClubMember(clubMemberDTO.getNickname(), clubMemberDTO.getContent());
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

    @Override
    public GetClubMemberDTO checkJoinClub(Long clubId,Long memberId) {

        Boolean isJoined = clubMemberRepository.existsByClubIdAndMemberId(clubId, memberId);

        if(isJoined){

            ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, memberId)
                    .orElseThrow(() -> new IllegalArgumentException("조회된 모임원이 없습니다."));
            return GetClubMemberDTO.fromEntity(clubMember);
        }else {
            return GetClubMemberDTO.builder().build();
        }
    }

    @Override
    public Boolean checkJoinRequest(Long clubId, Long memberId) {

        return clubMemberJoinRequestRepository.existsByClubIdAndMemberId(clubId, memberId);
    }

}
