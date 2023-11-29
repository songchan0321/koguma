package com.fiveguys.koguma.common;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Location;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.repository.common.LocationRepository;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.service.common.LocationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class LocationApplicationTests {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private MemberRepository memberRepository;




    @Test
    @DisplayName("위치 등록 테스트")
    @Transactional
    public void addLocation() throws Exception {

        Member member1 = Member.builder()
                .pw("sungyuun")
                .nickname("ㅁ")
                .email("ㅁ")
                .phone("01001213")
                .score(36.5F)
                .roleFlag(true)
                .socialFlag(true)
                .activeFlag(true)
                .build();

        Member mem = memberRepository.save(member1);
        locationService.addLocation(LocationDTO.builder()
                        .memberDTO(MemberDTO.fromEntity(mem))
                        .dong("인헌동")
                        .latitude(37.4923615)
                        .longitude(127.0292881)
                        .repAuthLocationFlag(true)
                        .searchRange(3)
                        .build());
    }

    @Test
    @DisplayName("위치 리스트 테스트")
    @Transactional
    public void listLocation() throws Exception {
        List<LocationDTO> locations = locationService.listLocation(4L);
        for (LocationDTO locationDTO : locations){
            System.out.println(locationDTO.toString());
        }
    }
    @Test
    @DisplayName("위치 삭제 테스트")
    @Transactional
    public void deleteLocation() throws Exception {
        Optional<Location> beforeLocation = locationRepository.findById(1L);
        locationService.deleteLocation(1L);
        Optional<Location> afterLocation = locationRepository.findById(1L);
        System.out.println("ad");
        Assertions.assertThat(beforeLocation).isEqualTo(afterLocation);
    }
}

