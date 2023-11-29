package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.ClubDTO;
import com.fiveguys.koguma.data.dto.ClubMemberDTO;
import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Category;
import com.fiveguys.koguma.data.entity.Club;
import com.fiveguys.koguma.data.entity.Location;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.repository.common.CategoryRepository;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.service.common.LocationService;
import com.fiveguys.koguma.service.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ClubServiceImplTest {

    @Autowired ClubService clubService;
    @Autowired MemberRepository memberRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    public void beforeEach(){


    }

    @Test
    public void 모임_생성() throws Exception{

        //given
//        Category category = categoryRepository.findById(1L).get();
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
//                .category(category)
//                .build();
//
//        ClubDTO clubDTO = ClubDTO.fromEntity(club);
//        Member member = memberRepository.findById(1L).get();
//        MemberDTO memberDTO = MemberDTO.fromEntity(member);
//
//        ClubMemberDTO clubMemberDTO = ClubMemberDTO.builder()
//                .nickName("닉네임")
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
    }


}