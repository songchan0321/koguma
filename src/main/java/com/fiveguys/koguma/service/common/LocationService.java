package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Location;

import java.util.List;

public interface LocationService {

    List<LocationDTO> listLocation(Long memberId);
    LocationDTO getLocation(Long locationId);
    void addLocation(LocationDTO locationDTO) throws Exception;
    void deleteLocation(MemberDTO memberDTO, Long id) throws Exception;
    void updateSearchRange(LocationDTO locationDTO,int range);
    LocationDTO addShareLocation(Long latitude,Long longitude);
    LocationDTO getMemberRepLocation(Long memberId);
    void setRepLocation(Long memberId,Long locationId);
    String reverseGeoCoder(double latitude, double longitude);
//    List<Object> LocationFilter(LocationDTO locationDTO);
}
