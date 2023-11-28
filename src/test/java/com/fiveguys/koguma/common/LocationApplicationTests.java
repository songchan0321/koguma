package com.fiveguys.koguma.common;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.entity.Location;
import com.fiveguys.koguma.service.common.LocationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;



@SpringBootTest
public class LocationApplicationTests {

    @Autowired
    private LocationService locationService;


    @Test
    @DisplayName("위치 등록 테스트")
    @Transactional
    public void addLocation() throws Exception {
        Location addLocation = locationService.addLocation(LocationDTO.builder()
                        .id(1L)
//                        .memberId(1L)
                        .dong("인헌동")
                        .latitude(37.4923615)
                        .longitude(127.0292881)
                        .repAuthLocationFlag(true)
                        .searchRange(3)
                        .build());

    }

}
