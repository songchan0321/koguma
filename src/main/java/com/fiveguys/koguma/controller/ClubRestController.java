package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.*;
import com.fiveguys.koguma.data.dto.club.*;
import com.fiveguys.koguma.data.entity.CategoryType;
import com.fiveguys.koguma.data.entity.MeetUpType;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.repository.club.ClubMemberRepository;
import com.fiveguys.koguma.service.club.ClubMeetUpService;
import com.fiveguys.koguma.service.club.ClubService;
import com.fiveguys.koguma.service.common.CategoryService;
import com.fiveguys.koguma.service.common.LocationService;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    private final ClubMemberRepository clubMemberRepository;

    @GetMapping(path = "/add")
    public ResponseEntity<?> addClub(){

        List<CategoryDTO> categoryDTOS = categoryService.listCategory(CategoryType.CLUB);

        return ResponseEntity.ok(categoryDTOS);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Long> addClub(@RequestBody CreateClubDTO createClubDTO,
                                     @CurrentMember MemberDTO memberDTO){

        LocationDTO memberRepLocation = locationService.getMemberRepLocation(memberDTO.getId());




        System.out.println("createClubDTO = " + createClubDTO);
        Long saveClubId = clubService.addClub(createClubDTO, memberDTO.getId());
        System.out.println("saveClubId = " + saveClubId);

        return ResponseEntity.ok(saveClubId);
    }

//    @GetMapping(path = "/list")
//    public ResponseEntity<List<ClubDTO>> listClub(@CurrentMember MemberDTO memberDTO){
//
//        LocationDTO repLo = locationService.getMemberRepLocation(memberDTO.getId());
//
//        if (repLo.getLatitude() != null){
//            List<ClubDTO> clubDTOS = clubService.listClub(repLo.getLatitude(), repLo.getLongitude());
//            return ResponseEntity.ok(clubDTOS);
//        }
//        return ResponseEntity.ok(clubService.listClub());
//    }

//    @GetMapping(path = "/all")
//    public ResponseEntity<?> listClubs(@CurrentMember MemberDTO memberDTO){
//
//        List<ClubDTO> clubDTOS = clubService.listClub();
//        for (ClubDTO clubDTO : clubDTOS) {
//            System.out.println("clubDTO = " + clubDTO);
//        }
//
//        return ResponseEntity.ok(clubDTOS);
//    }

    @GetMapping(path = "/joins")

    public ResponseEntity<?> listMyClub(@CurrentMember MemberDTO memberDTO){

        List<ListClubByCategoryDTO> clubDTOS = clubService.listMyClub(memberDTO.getId());

        return ResponseEntity.ok(clubDTOS);
    }

//    @GetMapping(path = "/list/category/{categoryId}")
//    public ResponseEntity<?> listClubByCategory(@PathVariable Long categoryId){
//        List<ListClubByCategoryDTO> listClubByCategoryDTOS = clubService.listClubByCategory(categoryId);
//
//        return ResponseEntity.ok(clubService.listClubByCategory(categoryId));
//    }

    @GetMapping(path = "/list")
    public ResponseEntity<?> listClubByCategory(@RequestParam String keyword, @RequestParam String category,
                                                @CurrentMember MemberDTO memberDTO) throws Exception{

        System.out.println("category : "+category);
        Long categoryId = null;
        if (category != null && !category.isEmpty()) {
            categoryId = Long.valueOf(category);
        }
        LocationDTO locationDTO = locationService.getMemberRepLocation(memberDTO.getId());

        List<ListClubByCategoryDTO> listClubByCategoryDTOS = clubService.listClubByCategory(locationDTO, keyword, categoryId);

        return ResponseEntity.ok(listClubByCategoryDTOS);
    }

    @GetMapping(path = "/{clubId}")
    public ResponseEntity<?> getClub(@PathVariable Long clubId,
                                           @CurrentMember MemberDTO memberDTO){
        return ResponseEntity.ok(clubService.findClub(clubId));
    }

    @GetMapping(path = "/update/{clubId}")
    public ResponseEntity<ClubDTO> updateClub(@PathVariable Long clubId){
        return ResponseEntity.ok(clubService.getClub(clubId));
    }


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

    @GetMapping("/join/request/cancel/{clubId}")
    public void cancelJoinRequest(@PathVariable Long clubId, @CurrentMember MemberDTO memberDTO){
        clubService.deleteJoinRequest(clubId,memberDTO.getId());
    }


    @GetMapping("/join/requests/{clubId}")
    public ResponseEntity<List<ClubJoinRequestDTO>> listJoinRequest(@PathVariable Long clubId){
        return ResponseEntity.ok(clubService.listClubJoinRequest(clubId));
    }

    @GetMapping("/accept/join/{joinId}")
    public ResponseEntity<?> acceptJoinRequest(@PathVariable Long joinId)
                                               {

        clubService.acceptJoinRequest(joinId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reject/join/{joinId}")
    public ResponseEntity<?> rejectJoinRequest(@PathVariable Long joinId
                                               ){
        clubService.rejectJoinRequest(joinId);
        return ResponseEntity.ok().build();
    }

    //todo: memberDTO 다 없애야할듯
    @GetMapping("/member/{clubId}")
    public ResponseEntity<GetClubMemberDTO> getClubMember(@PathVariable Long clubId, @CurrentMember MemberDTO memberDTO){
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

    @GetMapping("/members/count/{clubId}")
    public ResponseEntity<?> countClubMember(@PathVariable Long clubId){

        Integer test = clubService.countClubMember(clubId);
        System.out.println("test = " + test);
        return ResponseEntity.ok(clubService.countClubMember(clubId));
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
    public ResponseEntity<?> addClubMeetUp(@RequestBody CreateClubMeetUpDTO clubMeetUpDTO){

        Long meetUpId = clubMeetUpService.addClubMeetUp(clubMeetUpDTO, clubMeetUpDTO.getClubId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/meet-up/list/{clubId}/{meetUpType}")
    public ResponseEntity<?> listMeetUp(@PathVariable Long clubId, @PathVariable String meetUpType){



        return ResponseEntity.ok(clubMeetUpService.listClubMeetUp(clubId, meetUpType));
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



    @GetMapping(path = "/meet-up/join")
    public ResponseEntity<?> joinClubMeetUp(@RequestParam Long clubMeetUpId, @RequestParam Long clubMemberId){

        clubMeetUpService.joinClubMeetUp(clubMeetUpId, clubMemberId);

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/meet-up/members/{meetUpId}")
    public ResponseEntity<?> listJoinedMeeUpMember(@PathVariable Long meetUpId){

        List<ClubMemberMeetUpJoinDTO> cmmj = clubMeetUpService.listClubMeetUpMember(meetUpId);
        return ResponseEntity.ok(cmmj);
    }

    @PostMapping(path = "/meet-up/cancel")
    public ResponseEntity<?> joinMeetUpCancel(@RequestBody MeetUpStateDTO meetUpStateDTO,
                                              @CurrentMember MemberDTO memberDTO) {

        GetClubMemberDTO clubMember = clubService.getClubMember(meetUpStateDTO.getClubId(), memberDTO.getId());

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

        GetClubMemberDTO clubMember = clubService.getClubMember(clubId, memberDTO.getId());

        System.out.println("clubMember.getId = " + clubMember.getId());
        System.out.println("meetUpId = " + meetUpId);

        boolean b = clubMeetUpService.checkJoinMeetUp(meetUpId, clubMember.getId());
        System.out.println("checkJoinMeetUp == >>  = " + b);
        return ResponseEntity.ok(b);
    }

    @GetMapping("/member/check/{clubId}")
    public ResponseEntity<?> checkClubMember(@PathVariable Long clubId, @CurrentMember MemberDTO memberDTO){

        GetClubMemberDTO clubMemberDTO = clubService.checkJoinClub(clubId, memberDTO.getId());

        return ResponseEntity.ok(clubMemberDTO);
    }

    @GetMapping("/check/join/request/{clubId}")
    public ResponseEntity<?> checkJoinRequest(@PathVariable Long clubId, @CurrentMember MemberDTO memberDTO){

        Boolean b = clubService.checkJoinRequest(clubId, memberDTO.getId());

        return ResponseEntity.ok(b);
    }

    @GetMapping("/meet-up/addr")
    public ResponseEntity<?> meetUpGeoCoder(@RequestParam String address){
        Map<String, String> stringStringMap = locationService.geoCoder(address);

        System.out.println("stringStringMap = " + stringStringMap);

        return null;
    }

    @GetMapping("/meet-up/join/count/{meetUpId} ")
    public ResponseEntity<?> joinMeetUpMemberCount(@PathVariable Long meetUpId){

        return ResponseEntity.ok(clubMeetUpService.countJoinMember(meetUpId));
    }

    @GetMapping("/near/map")
    public ResponseEntity<?> nearClubMap(@CurrentMember MemberDTO memberDTO){

        List<ListClubByCategoryDTO> clubDTOS = clubService.listClub();


        return ResponseEntity.ok(clubDTOS);
    }
}
