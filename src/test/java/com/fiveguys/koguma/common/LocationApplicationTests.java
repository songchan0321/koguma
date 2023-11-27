package com.fiveguys.koguma.common;

import com.fiveguys.koguma.data.entity.Location;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.repository.common.LocationRepository;
import com.fiveguys.koguma.service.common.LocationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

public class LocationApplicationTests {

    private LocationService locationService;

//    @Test
//    @DisplayName("위치 등록 테스트")
//    @Transactional
//    public void addLocation() throws Exception {
//        Location addLocation = locationService.addLocation(Location.builder()
//                        .id(1L)
//                        .member(Member.builder().build())
//                        .dong("인헌동")
//                        .latitude(37.4923615)
//                        .longitude(127.0292881)
//                        .repAuthLocationFlag(true)
//                        .searchRange(3)
//                        .build());
//
//    }

}
