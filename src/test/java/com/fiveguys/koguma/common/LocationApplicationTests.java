package com.fiveguys.koguma.common;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Location;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.repository.common.LocationRepository;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.service.common.LocationService;
import com.fiveguys.koguma.service.member.MemberService;
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

import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest
@Transactional
public class LocationApplicationTests {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private MemberService memberService;


//    @Test
//    @DisplayName("위치 등록 테스트")
//    @Transactional
//    public void addLocation() throws Exception {
//
//        MemberDTO memberDTO = memberService.getMember(6L);
//        locationService.addLocation(LocationDTO.builder()
//                        .memberDTO(memberDTO)
//                        .dong("인헌동")
//                        .latitude(37.4923615)
//                        .longitude(127.0292881)
//                        .repAuthLocationFlag(true)
//                        .searchRange(3)
//                        .build());
//    }

//    @Test
//    @DisplayName("위치 리스트 테스트")
//    @Transactional
//    public void listLocation() throws Exception {
//        List<LocationDTO> locations = locationService.listLocation(4L);
//        for (LocationDTO locationDTO : locations){
//            System.out.println(locationDTO.toString());
//        }
//    }
    @Test
    @DisplayName("위치 삭제 테스트")
    @Transactional
    public void deleteLocation() throws Exception {

        MemberDTO memberDTO = memberService.getMember(4L);

        Optional<Location> beforeLocation = locationRepository.findById(2L);
        locationService.deleteLocation(memberDTO,2L);
    }
    @Test
    @DisplayName("위치 조회 테스트")
    @Transactional
    public void getLocation() throws Exception {
        LocationDTO locationDTO = locationService.getLocation(1L);
        System.out.println(locationDTO.toString());
    }
    @Test
    @DisplayName("대표 위치 변경 테스트")
    public void setRepLocation() throws Exception {
        locationService.setRepLocation(4L,2L);
    }
    @Test
    @DisplayName("검색 범위 테스트")
    @Transactional
    public void updateSearchRange() throws Exception {
        LocationDTO locationDTO = locationService.getLocation(1L);
        System.out.println(locationDTO.toString());
        locationService.updateSearchRange(locationDTO,4);

        System.out.println(locationService.getLocation(1L).toString());
    }
    @Test
    @DisplayName("멤버 대표 테스트")
    @Transactional
    public void getMemberRepLocation() throws Exception {
        LocationDTO locationDTO = locationService.getMemberRepLocation(6L);
        System.out.println(locationDTO.toString());
    }

    @Test
    @DisplayName("reverseGeocoder test")
    @Transactional
    public void reverseGeocoder(){
        String dong = locationService.reverseGeoCoder(37.4923615,127.0292881);
        System.out.println(dong);
    }


}

