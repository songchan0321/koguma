package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.*;
import com.fiveguys.koguma.data.entity.CategoryType;
import com.fiveguys.koguma.service.club.ClubMeetUpService;
import com.fiveguys.koguma.service.club.ClubService;
import com.fiveguys.koguma.service.common.CategoryService;
import com.fiveguys.koguma.service.common.LocationService;
import com.fiveguys.koguma.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/club")
@CrossOrigin("*")
public class ClubRestController {

    private final ClubService clubService;
    private final ClubMeetUpService clubMeetUpService;
    private final CategoryService categoryService;
    private final LocationService locationService;
    private final MemberService memberService;

    @GetMapping(path = "/new")
    public ResponseEntity<?> addClub(){

        List<CategoryDTO> categoryDTOS = categoryService.listCategory(CategoryType.CLUB);

        return ResponseEntity.ok(categoryDTOS);
    }

    @PostMapping(path = "/new/{memberId}")
    public ResponseEntity<Long> addClub(@RequestBody CreateClubDTO createClubDTO,
                                     @PathVariable Long memberId){

        System.out.println("createClubDTO = " + createClubDTO);

        return ResponseEntity.ok(clubService.addClub(createClubDTO, memberId));
    }

    //// TODO: 2023-12-04, 위도 및 경도 기반 리스트 location 담당자에게 문의 필요
    @PostMapping(path = "/list")
    public ResponseEntity<?> listClub(){
        return null;
    }

    @GetMapping(path = "/list/category/{categoryId}")
    public ResponseEntity<List<ClubDTO>> listClubByCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(clubService.listClubByCategory(categoryId));
    }

    @GetMapping(path = "/{clubId}")
    public ResponseEntity<ClubDTO> getClub(@PathVariable Long clubId){
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

    @PostMapping(path = "/join/{memberId}")
    public ResponseEntity<?> addJoinRequest(@RequestBody ClubJoinRequestDTO clubJoinRequestDTO,
                                            @PathVariable Long memberId){
        return ResponseEntity.ok(clubService.addJoinRequestClub(clubJoinRequestDTO, memberId));
    }

    @DeleteMapping("/cancel/join/{memberId}")
    public void cancelJoinRequest(@PathVariable Long memberId){
        clubService.deleteByMemberId(memberId);
    }

    //todo: leaderId 검증 작업 필요
    @PostMapping("/list/join")
    public ResponseEntity<List<ClubJoinRequestDTO>> listJoinRequest(@RequestParam Long clubId,
                                             @RequestParam Long leaderId){
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
    @GetMapping("/member")
    public ResponseEntity<ClubMemberDTO> getClubMember(@RequestParam Long clubMemberId){
        return ResponseEntity.ok(clubService.getClubMember(clubMemberId));
    }

    @GetMapping("/list/members/{clubId}")
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

    @GetMapping (path = "/add/meet-up/{clubId}")
    public ResponseEntity<ClubMeetUpDTO> addClubMeetUp(@PathVariable Long clubId,@RequestBody ClubMeetUpDTO clubMeetUpDTO){
        Long meetUpId = clubMeetUpService.addClubMeetUp(clubMeetUpDTO, clubId);

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

    @PostMapping(path = "/meet-up/cancel")
    public ResponseEntity<?> cancel(@RequestParam Long clubMeetUpId, @RequestParam Long clubMemberId) {

        clubMeetUpService.cancel(clubMeetUpId, clubMemberId);

        return ResponseEntity.ok().build();
    }


}
