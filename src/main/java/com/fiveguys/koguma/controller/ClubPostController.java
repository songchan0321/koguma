package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.ClubMemberDTO;
import com.fiveguys.koguma.data.dto.ClubPostCategoryDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.club.ClubPostDTO;
import com.fiveguys.koguma.data.dto.club.GetClubMemberDTO;
import com.fiveguys.koguma.service.club.ClubPostCategoryImpl;
import com.fiveguys.koguma.service.club.ClubPostService;
import com.fiveguys.koguma.service.club.ClubService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/club/post")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ClubPostController {

    private final ClubService clubService;
    private final ClubPostService clubPostService;
    private final ClubPostCategoryImpl clubPostCategory;

    @PostMapping("/add")
    public ResponseEntity<Long> addPost(@RequestBody ClubPostDTO clubPostDTO,
                                        @CurrentMember MemberDTO memberDTO){



        return ResponseEntity.ok(clubPostService.addClubPost(clubPostDTO, memberDTO));
    }

    @GetMapping("/{clubPostId}")
    public ResponseEntity<?> getPost(@PathVariable Long clubPostId, @CurrentMember MemberDTO memberDTO){

        ClubPostDTO clubPost = clubPostService.getClubPost(clubPostId);

        clubPost.setMemberProfile(memberDTO.getProfileURL());

        return ResponseEntity.ok(clubPost);
    }

    @GetMapping("/list/{clubId}")
    public ResponseEntity<?> listClubPost(@PathVariable Long clubId){

        List<ClubPostDTO> clubPostDTOS = clubPostService.listClubPost(clubId);

        return ResponseEntity.ok(clubPostDTOS);
    }

    @GetMapping("/lists/category/{categoryId}")
    public ResponseEntity<?> listClubPostByCategory(@PathVariable Long categoryId){

        List<ClubPostDTO> clubPostDTOS = clubPostService.listClubPostByCategory(categoryId);

        return ResponseEntity.ok(clubPostDTOS);
    }


    @PostMapping("/list/my")
    public ResponseEntity<?> listMyClubPost(@CurrentMember MemberDTO memberDTO){

        List<ClubPostDTO> clubPostDTOS = clubPostService.listMyClubPost(memberDTO.getId());

        for (ClubPostDTO clubPostDTO : clubPostDTOS) {
            System.out.println("clubPostDTO = " + clubPostDTO);
        }

        return ResponseEntity.ok(clubPostDTOS);
    }

//    @GetMapping("/list/category/{categoryId}")
//    public ResponseEntity<?> listClubPostByCategory(@PathVariable Long categoryId){
//        List<ClubPostDTO> clubPostDTOS = clubPostService.listClubPostByCategory(categoryId);
//
//        return ResponseEntity.ok(clubPostDTOS);
//    }



    @PostMapping("/category/add")
    public ResponseEntity<?> addClubPostCategory(@RequestBody ClubPostCategoryDTO ccd,
                                                 @CurrentMember MemberDTO memberDTO){

        System.out.println("ccd = " + ccd);

        GetClubMemberDTO clubMember = clubService.getClubMember(ccd.getClubId(), memberDTO.getId());

        Long categoryId = clubPostCategory.addClubPostCategory(ccd.getClubId(), clubMember.getId(), ccd.getName());

        return ResponseEntity.ok(categoryId);
    }


    @GetMapping("/categories/{clubId}")
    public ResponseEntity<?> listClubPostCategory(@PathVariable Long clubId){

        List<ClubPostCategoryDTO> clubPostCategoryDTOS = clubPostCategory.listClubPostCategories(clubId);

        return ResponseEntity.ok(clubPostCategoryDTOS);
    }

    @GetMapping("/list/category/{categoryId}")
    public ResponseEntity<?> getClubPostCategory(@PathVariable Long categoryId){
        ClubPostCategoryDTO findClubPostCate = clubPostCategory.getClubPostCategory(categoryId);
        return ResponseEntity.ok(findClubPostCate);
    }

}
