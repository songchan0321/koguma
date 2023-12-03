package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.CategoryDTO;
import com.fiveguys.koguma.data.dto.ClubDTO;
import com.fiveguys.koguma.data.dto.ClubMeetUpDTO;
import com.fiveguys.koguma.data.entity.Category;
import com.fiveguys.koguma.data.entity.Club;
import com.fiveguys.koguma.data.entity.ClubMeetUp;
import com.fiveguys.koguma.repository.club.ClubRepository;
import com.fiveguys.koguma.repository.common.CategoryRepository;
import com.fiveguys.koguma.service.common.CategoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ClubMeetUpServiceImplTest {

    @Autowired
    ClubMeetUpService clubMeetUpService;
    @Autowired
    ClubService clubService;
    @Autowired
    ClubRepository clubRepository;
    @Autowired
    CategoryRepository categoryRepository;


    //@Test
    public void 일정_생성_더미() throws Exception{
        //given


        for (long i = 21; i < 70; i++ ){

            for(int j = 0; j < 3; j++){

                ClubMeetUpDTO cmuDTO = ClubMeetUpDTO.builder()
                        .title("만남테스트")
                        .content("만남 본문")
                        .maxCapacity(100)
                        .meetDate(LocalDateTime.parse("2023-12-01T14:30:00"))
                        .roadAddr("만남 도로명주소")
                        .activeFlag(true)
                        .build();

                clubMeetUpService.addClubMeetUp(cmuDTO, (Long) i);

            }


        }

        //when

        //then
    }

    //@Test
    public void 모임_일정_생성_및_조회() throws Exception{

        //given
        ClubMeetUpDTO cmuDTO = ClubMeetUpDTO.builder()
                .title("만남테스트")
                .content("만남 본문")
                .maxCapacity(100)
                .meetDate(LocalDateTime.parse("2023-12-01T14:30:00"))
                .roadAddr("만남 도로명주소")
                .activeFlag(true)
                .build();

        //when
        Long cmuId = clubMeetUpService.addClubMeetUp(cmuDTO, 20L);
        ClubMeetUpDTO findCmuD = clubMeetUpService.getClubMeetUp(cmuId);

        //then
        Assertions.assertThat(findCmuD.getId()).isEqualTo(cmuId);

    }

    //@Test
    public void 모임_일정_리스트_조회() throws Exception{
        //given
        //clubId 20인 모임에 meetUp 3개 입력

        //when
        ClubDTO clubDTO = clubService.getClub(20L);
        List<ClubMeetUpDTO> clubMeetUpDTOS = clubMeetUpService.listClubMeetUp(clubDTO.getId());

        //then
        Assertions.assertThat(clubDTO.getId()).isEqualTo(20L);
        Assertions.assertThat(clubMeetUpDTOS.size()).isEqualTo(3);

    }

    //@Test
    public void 모임_일정_수정() throws Exception{
        //given
        ClubMeetUpDTO cmuDTO = ClubMeetUpDTO.builder()
                .title("만남테스트")
                .content("만남 본문")
                .maxCapacity(100)
                .meetDate(LocalDateTime.parse("2023-12-01T14:30:00"))
                .roadAddr("만남 도로명주소")
                .activeFlag(true)
                .build();

        Long cmuId = clubMeetUpService.addClubMeetUp(cmuDTO, 20L);

        ClubMeetUpDTO updateCmuDtO = ClubMeetUpDTO.builder()
                .id(cmuId)
                .title("수정")
                .content("일정내용수정")
                .maxCapacity(100)
                .meetDate(LocalDateTime.parse("2023-12-02T14:30:00"))
                .roadAddr("일정 도로명 수정")
                .build();

        //when
        clubMeetUpService.updateClubMeetUp(updateCmuDtO);
        ClubMeetUpDTO findUpdateCmuDto = clubMeetUpService.getClubMeetUp(cmuId);

        //then
        Assertions.assertThat(findUpdateCmuDto.getContent()).isEqualTo(updateCmuDtO.getContent());
        Assertions.assertThat(findUpdateCmuDto.getClubDTO().getId()).isEqualTo(20L);
    }

    //@Test
    public void 모임_일정_참여() throws Exception{
        //given


        //when

        //then
    }


}