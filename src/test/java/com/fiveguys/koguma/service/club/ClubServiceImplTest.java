package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.*;
import com.fiveguys.koguma.data.entity.*;
import com.fiveguys.koguma.repository.club.ClubMemberRepository;
import com.fiveguys.koguma.repository.club.ClubRepository;
import com.fiveguys.koguma.repository.common.CategoryRepository;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.repository.product.ProductRepository;
import com.fiveguys.koguma.service.common.CategoryService;
import com.fiveguys.koguma.service.common.CategorySpecifications;
import com.fiveguys.koguma.service.common.LocationService;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.product.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
class ClubServiceImplTest {

    @Autowired
    ClubService clubService;
    @Autowired
    ClubRepository clubRepository;
    @Autowired
    ClubMemberRepository clubMemberRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    MemberService memberService;
    @Autowired
    LocationService locationService;
    @Autowired
    ProductRepository productRepository;






    //@Test
//    public void 모임_생성() throws Exception{
//
//        //given
//        Category category = categoryRepository.findById(37L).get();
//
//        Club club = Club.builder()
//                .title("타이틀1")
//                .content("내용1")
//                .maxCapacity(1000)
//                .latitude(37.494949)
//                .longitude(14.1212)
//                .dong("역삼동")
//                .joinActiveFlag(true)
//                .activeFlag(true)
//                .categoryName(category.getCategoryName())
//                .category(category)
//                .build();
//
//        ClubDTO clubDTO = ClubDTO.fromEntity(club);
//        Member member = memberRepository.findById(1L).get();
//        MemberDTO memberDTO = MemberDTO.fromEntity(member);
//
//        ClubMemberDTO clubMemberDTO = ClubMemberDTO.builder()
//                .nickname("닉네임")
//                .content("모임원자기소개")
//                .clubDTO(clubDTO)
//                .memberDTO(memberDTO)
//                .build();
//
//        //when
//        Long clubId = clubService.addClub(clubDTO, clubMemberDTO);
//        ClubDTO findClub = clubService.getClub(clubId);
//
//        //then
//        Assertions.assertThat(findClub.getId()).isEqualTo(clubId);
//    }
//
   @Test
    public void 모임_생성_더미() throws Exception{
        Category category = categoryRepository.findById(29L).get();


        for(int i = 4;  i < 300; i++){
            Club club = Club.builder()
                    .title("모임test" + i)
                    .content("모임소개" + i)
                    .dong("모임행정동" + i)
                    .longitude(37.123456)
                    .latitude(126.789012)
                    .maxCapacity(100)
                    .category(category)
                    .categoryName(category.getCategoryName())
                    .joinActiveFlag(true)
                    .activeFlag(true)
                    .build();

            clubRepository.save(club);
        }
    }

    //@Test
    public void 회원생성() throws Exception{
        //given

        for (long i = 5; i < 30; i++){
            Club club =clubRepository.findById(i).get();
            Member member = memberRepository.findById(i).get();
            ClubMember clubMember = ClubMember.createClubMember(club, member, "모임원"+i, "모임원내용"+i);
            clubMemberRepository.save(clubMember);
        }


        //when

        //then
    }

//    @Test
//    public void 모임원_생성() throws Exception{
//        for (long i = 21; i < 70; i++ ){
//
//            for(int j = 0; j < 3; j++){
//
//                ClubMember.createClubMember()
//
//                clubMeetUpService.addClubMeetUp(cmuDTO, (Long) i);
//
//            }
//
//
//        }
//    }

//
//    //@Test
//    public void 모임_가입신청() throws Exception{
//
//        //given
//        Category category = categoryRepository.findById(37L).get();
//
//        Club club = Club.builder()
//                .title("테스트모임이름1")
//                .content("테스트모임내용2")
//                .maxCapacity(100)
//                .latitude(37.494949)
//                .longitude(14.1212)
//                .dong("역삼동")
//                .joinActiveFlag(true)
//                .activeFlag(true)
//                .categoryName(category.getCategoryName())
//                .category(category)
//                .build();
//
//        ClubDTO clubDTO = ClubDTO.fromEntity(club);
//
//        Member member = memberRepository.findById(1L).get();
//        MemberDTO memberDTO = MemberDTO.fromEntity(member);
//
//        ClubMemberDTO clubMemberDTO = ClubMemberDTO.builder()
//                .nickname("닉네임")
//                .content("모임원자기소개")
//                .clubDTO(clubDTO)
//                .memberDTO(memberDTO)
//                .build();
//
//        Long clubId = clubService.addClub(clubDTO, clubMemberDTO);
//        ClubDTO findClubDTO = clubService.getClub(clubId);
//
//        ClubJoinRequestDTO clubJoinRequestDTO = ClubJoinRequestDTO.builder()
//                .clubDTO(findClubDTO)
//                .memberDTO(memberDTO)
//                .content("가입신청 테스트 본문")
//                .activeFlag(true)
//                .build();
//
//        //when
//        Long joinRequestClubId = clubService.addJoinRequestClub(clubJoinRequestDTO);
//        ClubJoinRequestDTO findClubJoinRequest = clubService.getClubJoinRequest(joinRequestClubId);
//
//        //then
//        Assertions.assertThat(joinRequestClubId).isEqualTo(findClubJoinRequest.getId());
//        Assertions.assertThat(clubId).isEqualTo(findClubJoinRequest.getClubDTO().getId());
//    }
//
//    //@Test
//    public void 모임_가입신청_리스트() throws Exception{
//
//        //given
//        Category category = categoryRepository.findById(37L).get();
//
//        Club club = Club.builder()
//                .title("테스트모임이름1")
//                .content("테스트모임내용2")
//                .maxCapacity(100)
//                .latitude(37.494949)
//                .longitude(14.1212)
//                .dong("역삼동")
//                .joinActiveFlag(true)
//                .activeFlag(true)
//                .categoryName(category.getCategoryName())
//                .category(category)
//                .build();
//
//        ClubDTO clubDTO = ClubDTO.fromEntity(club);
//
//        Member member1 = memberRepository.findById(1L).get();
//        MemberDTO memberDTO1 = MemberDTO.fromEntity(member1);
//
//        Member member2 = memberRepository.findById(2L).get();
//        MemberDTO memberDTO2 = MemberDTO.fromEntity(member2);
//
//        Member member3 = memberRepository.findById(3L).get();
//        MemberDTO memberDTO3 = MemberDTO.fromEntity(member3);
//
//        Member member4 = memberRepository.findById(4L).get();
//        MemberDTO memberDTO4 = MemberDTO.fromEntity(member4);
//
//        ClubMemberDTO clubMemberDTO = ClubMemberDTO.builder()
//                .nickname("닉네임")
//                .content("모임원자기소개")
//                .clubDTO(clubDTO)
//                .memberDTO(memberDTO1)
//                .build();
//
//        Long clubId = clubService.addClub(clubDTO, clubMemberDTO);
//        ClubDTO findClubDTO = clubService.getClub(clubId);
//
//        ClubJoinRequestDTO clubJoinRequestDTO1 = ClubJoinRequestDTO.builder()
//                .clubDTO(findClubDTO)
//                .memberDTO(memberDTO2)
//                .content("가입신청 테스트 본문2")
//                .activeFlag(true)
//                .build();
//
//        ClubJoinRequestDTO clubJoinRequestDTO2 = ClubJoinRequestDTO.builder()
//                .clubDTO(findClubDTO)
//                .memberDTO(memberDTO3)
//                .content("가입신청 테스트 본문3")
//                .activeFlag(true)
//                .build();
//
//        ClubJoinRequestDTO clubJoinRequestDTO3 = ClubJoinRequestDTO.builder()
//                .clubDTO(findClubDTO)
//                .memberDTO(memberDTO4)
//                .content("가입신청 테스트 본문4")
//                .activeFlag(true)
//                .build();
//
////        Long joinRequestClubId1 = clubService.addJoinRequestClub(clubJoinRequestDTO1);
////        Long joinRequestClubId2 = clubService.addJoinRequestClub(clubJoinRequestDTO2);
////        Long joinRequestClubId3 = clubService.addJoinRequestClub(clubJoinRequestDTO3);
//
//        //when
//        List<ClubJoinRequestDTO> clubJoinRequestDTOS = clubService.listClubJoinRequest(clubId);
//
//        //then
//        Assertions.assertThat(clubJoinRequestDTOS.size()).isEqualTo(3);
//
//
//
//    }
//
//    //@Test
//    public void 모임_가입_신청_승인() throws Exception{
//        //given
//        Category category = categoryRepository.findById(37L).get();
//
//        Club club = Club.builder()
//                .title("테스트모임이름1")
//                .content("테스트모임내용2")
//                .maxCapacity(100)
//                .latitude(37.494949)
//                .longitude(14.1212)
//                .dong("역삼동")
//                .joinActiveFlag(true)
//                .activeFlag(true)
//                .categoryName(category.getCategoryName())
//                .category(category)
//                .build();
//
//        ClubDTO clubDTO = ClubDTO.fromEntity(club);
//
//        Member member = memberRepository.findById(1L).get();
//        MemberDTO memberDTO = MemberDTO.fromEntity(member);
//
//        ClubMemberDTO clubMemberDTO = ClubMemberDTO.builder()
//                .nickname("닉네임")
//                .content("모임원자기소개")
//                .clubDTO(clubDTO)
//                .memberDTO(memberDTO)
//                .build();
//
//        Long clubId = clubService.addClub(clubDTO, clubMemberDTO);
//        ClubDTO findClubDTO = clubService.getClub(clubId);
//
//        ClubJoinRequestDTO clubJoinRequestDTO = ClubJoinRequestDTO.builder()
//                .clubDTO(findClubDTO)
//                .memberDTO(memberDTO)
//                .nickname("4가입신청 닉네임")
//                .content("가입신청 테스트 본문")
//                .activeFlag(true)
//                .build();
//
//        Long joinRequestClubId = clubService.addJoinRequestClub(clubJoinRequestDTO);
//
//        //when
//        Long clubMemberId = clubService.acceptJoinRequest(joinRequestClubId);
//        ClubMember findClubMember = clubMemberRepository.findById(clubMemberId).get();
//
//        //then
//        Assertions.assertThat(findClubMember.getContent()).isEqualTo(clubJoinRequestDTO.getContent());
//    }
//
//    //@Test
//    public void 모임원_리스트_조회() throws Exception{
//        //given
//
//        // 테스트 데이터 로컬 club ID -> 20~23 // 2023.11.29
//        // 모임원 테스트 데이터 clubMemberId -> 13~16 // club 20 : 2명, 21 : 2명
//
//        //when
//        List<ClubMemberDTO> clubMemberDTOS = clubService.listClubMember(20L);
//
//        //then
//        Assertions.assertThat(clubMemberDTOS.size()).isEqualTo(2);
//
//
//    }
//
//    //@Test
//    public void 모임장_모임원_상세조회() throws Exception{
//        //given
//
//        // 테스트 데이터 로컬 club ID -> 20~23 // 2023.11.29
//        // 모임원 테스트 데이터 clubMemberId -> 13~16 // club 20 : 2명, 21 : 2명
//        // 모임장: Club 20 -> clubMemberId 13
//        // 모임장: Club 21 -> clubMemberId 15
//
//        //when
//        ClubMemberDTO cmd = clubService.getClubMember(13L);
//        ClubMemberDTO cmd1 = clubService.getClubMember(14L);
//
//
//        //then
//        Assertions.assertThat(cmd.getMemberRole()).isEqualTo(true);
//        Assertions.assertThat(cmd1.getMemberRole()).isEqualTo(false);
//    }
//
//    //@Test
//    public void 모임장_위임() throws Exception{
//        //given
//        // 테스트 데이터 로컬 club ID -> 20~23 // 2023.11.29
//        // 모임원 테스트 데이터 clubMemberId -> 13~16 // club 20 : 2명, 21 : 2명
//        // 모임장: Club 20 -> clubMemberId 13, 모임원: clubMemberId 14
//        // 모임장: Club 21 -> clubMemberId 15, 모임원: clubMemberId 16
//
//        //when
//        clubService.changeClubLeader(13L, 14L);
//        ClubMemberDTO demoteCM = clubService.getClubMember(13L);
//        ClubMemberDTO newLeader = clubService.getClubMember(14L);
//
//        //then
//        Assertions.assertThat(demoteCM.getMemberRole()).isEqualTo(false);
//        Assertions.assertThat(newLeader.getMemberRole()).isEqualTo(true);
//    }
//
//
//
    //@Test
    public void 카테고리_테스트() throws Exception{

        List<CategoryDTO> categoryDTOS = categoryService.listCategory(CategoryType.POST);

        for (CategoryDTO categoryDTO : categoryDTOS) {
            System.out.println("categoryDTO = " + categoryDTO);
        }

    }

    //@Test
    public void 카테고리_생성() throws Exception{
        //given

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .categoryType(CategoryType.CLUB)
                .name("운동")
                .build();

        Category entity = categoryDTO.toEntity();

        categoryRepository.save(entity);

        //when

        //then
    }
}