package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.club.ClubPostDTO;
import com.fiveguys.koguma.data.entity.*;
import com.fiveguys.koguma.repository.club.ClubMemberRepository;
import com.fiveguys.koguma.repository.club.ClubPostCategoryRepository;
import com.fiveguys.koguma.repository.club.ClubPostRepository;
import com.fiveguys.koguma.repository.club.ClubRepository;
import com.fiveguys.koguma.repository.post.PostRepository;
import com.fiveguys.koguma.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubPostService {

    private final PostRepository postRepository;
    private final MemberService memberService;
    private final ClubPostRepository clubPostRepository;
    private final ClubRepository clubRepository;
    private final ClubPostCategoryRepository clubPostCategoryRepository;
    private final ClubMemberRepository clubMemberRepository;



    public Long addClubPost(ClubPostDTO cpd, MemberDTO memberDTO){

        //멤버조회
        Member member = memberDTO.toEntity();
        //모임조회
        Club findClub = clubRepository.findById(cpd.getClubId()).get();
        //모임 카테고리 조회
        ClubPostCategory cpc = clubPostCategoryRepository.findById(cpd.getClubCategoryId()).get();
        //모임원 조회
         ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(findClub.getId(), member.getId()).get();
        //모임 포스트 엔티티 생성 후 데이터 값 넣기
        ClubPost clubPost = ClubPost.createClubPost(cpd.getTitle(), cpd.getContent(), findClub, findClub.getTitle(), member,
                cpc, cpc.getName(), clubMember.getNickname(), cpd.getImages());

        clubPostRepository.save(clubPost);

        return clubPostRepository.save(clubPost).getId();
    }


    public ClubPostDTO getClubPost(Long clubPostId){

        ClubPost clubPost = clubPostRepository.findById(clubPostId).get();

        clubPost.increaseViews();

        return ClubPostDTO.fromEntity(clubPost);

    }

    public List<ClubPostDTO> listMyClubPost(Long memberId){
        // 내 모임 리스트 조회
        List<ClubMember> clubMembers = clubMemberRepository.findAllByMemberId(memberId);

        // clubMember -> club 으로 전환
        List<Club> clubs = clubMembers.stream()
                .map(ClubMember::getClub)
                .collect(Collectors.toList());

        List<ClubPost> clubPosts = clubPostRepository.findByClubIn(clubs);

        List<ClubPostDTO> clubPostDTOS = clubPosts.stream()
                .map(ClubPostDTO::fromEntity)
                .collect(Collectors.toList());

       return clubPostDTOS;

    }

    public List<ClubPostDTO> listClubPost(Long clubId){


        List<ClubPost> clubPosts = clubPostRepository.findByClubId(clubId);

        return clubPosts.stream()
                .map(ClubPostDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ClubPostDTO> listClubPostByCategory(Long categoryId){

        List<ClubPost> clubPosts = clubPostRepository.findByClubPostCategoryId(categoryId);

        return clubPosts.stream()
                .map(ClubPostDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public void updatePost(){
    }
    
}
