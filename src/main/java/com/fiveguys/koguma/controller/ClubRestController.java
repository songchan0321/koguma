package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.*;
import com.fiveguys.koguma.data.dto.club.MeetUpStateDTO;
import com.fiveguys.koguma.data.entity.CategoryType;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.service.club.ClubMeetUpService;
import com.fiveguys.koguma.service.club.ClubService;
import com.fiveguys.koguma.service.common.CategoryService;
import com.fiveguys.koguma.service.common.LocationService;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/club")
@CrossOrigin(origins = "*")
public class ClubRestController {

    private final ClubService clubService;
    private final ClubMeetUpService clubMeetUpService;
    private final CategoryService categoryService;
    private final LocationService locationService;
    private final MemberService memberService;

    @GetMapping(path = "/add")
    public ResponseEntity<?> addClub(){

        List<CategoryDTO> categoryDTOS = categoryService.listCategory(CategoryType.CLUB);

        return ResponseEntity.ok(categoryDTOS);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Long> addClub(@RequestBody CreateClubDTO createClubDTO,
                                     @CurrentMember MemberDTO memberDTO){
        return ResponseEntity.ok(clubService.addClub(createClubDTO, memberDTO.getId()));
    }

    //// TODO: 위도 및 경도 기반 리스트 location 담당자에게 문의 필요, UI 시 작업 진행
    @GetMapping(path = "/list")
    public ResponseEntity<List<ClubDTO>> listClub(@CurrentMember MemberDTO memberDTO){

        LocationDTO repLo = locationService.getMemberRepLocation(memberDTO.getId());

        if (repLo.getLatitude() != null){
            List<ClubDTO> clubDTOS = clubService.listClub(repLo.getLatitude(), repLo.getLongitude());
            return ResponseEntity.ok(clubDTOS);
        }
        return ResponseEntity.ok(clubService.listClub());
    }


    @GetMapping(path = "/list/category/{categoryId}")
    public ResponseEntity<List<ClubDTO>> listClubByCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(clubService.listClubByCategory(categoryId));
    }

    @GetMapping(path = "/{clubId}")
    public ResponseEntity<ClubDTO> getClub(@PathVariable Long clubId,
                                           @CurrentMember MemberDTO memberDTO){
        return ResponseEntity.ok(clubService.getClub(clubId));
    }

    @GetMapping(path = "/update/{clubId}")
    public ResponseEntity<ClubDTO> updateClub(@PathVariable Long clubId){
        return ResponseEntity.ok(clubService.getClub(clubId));
    }

    //todo:: 예외 로직 추가 필요, 리턴값 생각 필요
    @PutMapping(path = "/update/{memberId}")
    public ResponseEntity<ClubDTO> updateClub(@RequestBody ClubDTO clubDTO,
                                              @PathVariable Long memberId){
        clubService.updateClub(clubDTO);
        return ResponseEntity.ok(clubDTO);
    }

    @PostMapping(path = "/join/request")
    public ResponseEntity<?> addJoinRequest(@RequestBody ClubJoinRequestDTO clubJoinRequestDTO,
                                            @CurrentMember MemberDTO memberDTO){
        return ResponseEntity.ok(clubService.addJoinRequestClub(clubJoinRequestDTO, memberDTO.getId()));
    }

    @DeleteMapping("/cancel/join/{memberId}")
    public void cancelJoinRequest(@PathVariable Long memberId){
        clubService.deleteByMemberId(memberId);
    }

    //todo: leaderId 검증 작업 필요
    @GetMapping("/join/requests/{clubId}")
    public ResponseEntity<List<ClubJoinRequestDTO>> listJoinRequest(@PathVariable Long clubId){
        return ResponseEntity.ok(clubService.listClubJoinRequest(clubId));
    }

    //todo: 시큐리티 후 재 진행 필요
    @PostMapping("/accept/join")
    public ResponseEntity<?> acceptJoinRequest(@RequestParam Long joinId,
                                               @RequestParam Long clubId,
                                               @RequestParam Long leaderId){

        clubService.acceptJoinRequest(joinId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reject/join")
    public ResponseEntity<?> rejectJoinRequest(@RequestParam Long joinId,
                                               @RequestParam Long clubId,
                                               @RequestParam Long leaderId){
        clubService.rejectJoinRequest(joinId);
        return ResponseEntity.ok().build();
    }

    //todo: memberDTO 다 없애야할듯
    @GetMapping("/member/{clubId}")
    public ResponseEntity<ClubMemberDTO> getClubMember(@PathVariable Long clubId, @CurrentMember MemberDTO memberDTO){
        return ResponseEntity.ok(clubService.getClubMember(clubId, memberDTO.getId()));
    }

    @GetMapping("/member/profile/{clubMemberId}")
    public ResponseEntity<ClubMemberDTO> getClubMember(@PathVariable Long clubMemberId){
        return ResponseEntity.ok(clubService.getClubMember(clubMemberId));
    }

    @GetMapping("/members/{clubId}")
    public ResponseEntity<List<ClubMemberDTO>> listClubMember(@PathVariable Long clubId){

        return ResponseEntity.ok(clubService.listClubMember(clubId));
    }

    @DeleteMapping("/delete/members")
    public ResponseEntity<?> deleteClubMember(@RequestParam Long clubMemberId){
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/member/update")
    public ResponseEntity<?> updateClubMember(@RequestBody ClubMemberDTO clubMemberDTO){
        clubService.updateClubMember(clubMemberDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping (path = "/add/meet-up")
    public ResponseEntity<ClubMeetUpDTO> addClubMeetUp(@RequestBody ClubMeetUpDTO clubMeetUpDTO){


        Long meetUpId = clubMeetUpService.addClubMeetUp(clubMeetUpDTO, clubMeetUpDTO.getClubDTO().getId());

        ClubMeetUpDTO clubMeetUp = clubMeetUpService.getClubMeetUp(meetUpId);

        return ResponseEntity.ok(clubMeetUp);
    }

    @GetMapping(path = "/meet-up/{meetUpId}")
    public ResponseEntity<ClubMeetUpDTO> getClubMeetUp(@PathVariable Long meetUpId){

        ClubMeetUpDTO clubMeetUp = clubMeetUpService.getClubMeetUp(meetUpId);

        return ResponseEntity.ok(clubMeetUp);
    }

    @PutMapping(path = "/meet-up/update/{meetUpId}")
    public ResponseEntity<?> updateClubMeetUp(@PathVariable Long meetUpId, @RequestBody ClubMeetUpDTO clubMeetUpDTO){

        clubMeetUpService.updateClubMeetUp(meetUpId, clubMeetUpDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/meet-up/delete")
    public ResponseEntity<?> deleteClubMeetUp(@RequestParam Long leaderId, @RequestParam Long meetUpId){
        clubMeetUpService.deleteClubMeetUp(leaderId,meetUpId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/meet-up/list/{clubId}")
    public ResponseEntity<List<ClubMeetUpDTO>> listClubMeetUp(@PathVariable Long clubId){
        return ResponseEntity.ok(clubMeetUpService.listClubMeetUp(clubId));
    }

    @PostMapping(path = "/meet-up/join")
    public ResponseEntity<?> joinClubMeetUp(@RequestParam Long clubMeetUpId, @RequestParam Long clubMemberId){

        clubMeetUpService.joinClubMeetUp(clubMeetUpId, clubMemberId);

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/meet-up/members/{meetUpId}")
    public ResponseEntity<?> listJoinedMeeUpMember(@PathVariable Long meetUpId){

        List<ClubMemberMeetUpJoinDTO> cmmj = clubMeetUpService.listClubMeetUpMember(meetUpId);
        return ResponseEntity.ok(cmmj);
    }

    @GetMapping(path = "/meet-up/cancel")
    public ResponseEntity<?> joinMeetUpCancel(@RequestBody MeetUpStateDTO meetUpStateDTO) {

        ClubMemberDTO clubMember = clubService.getClubMember(meetUpStateDTO.getClubId(), meetUpStateDTO.getMeetUpId());

        clubMeetUpService.cancel(meetUpStateDTO.getMeetUpId(), clubMember.getId());

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/category/{type}")
    public ResponseEntity<?> listCategory(@PathVariable String type){

        CategoryType categoryType = CategoryType.valueOf(type);

        List<CategoryDTO> categoryDTOS = categoryService.listCategory(categoryType);
        return ResponseEntity.ok(categoryDTOS);
    }

    @GetMapping(path = "/meet-up/check/join")
    public ResponseEntity<Boolean> checkJoinMeetUp(@CurrentMember MemberDTO memberDTO,
                                                   @RequestParam Long clubId,
                                                   @RequestParam Long meetUpId){

        ClubMemberDTO clubMember = clubService.getClubMember(clubId, memberDTO.getId());


        return ResponseEntity.ok(clubMeetUpService.checkJoinMeetUp(meetUpId,clubMember.getId()));
    }

    @GetMapping("/member/check/{clubId}")
    public ResponseEntity<?> checkClubMember(@PathVariable Long clubId, @CurrentMember MemberDTO memberDTO){

        ClubMemberDTO clubMemberDTO = clubService.checkJoinClub(clubId, memberDTO.getId());

        return ResponseEntity.ok(clubMemberDTO);
    }

}
