package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.CategoryType;
import com.fiveguys.koguma.data.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface LocationService {

    List<LocationDTO> listLocation(MemberDTO memberDTO);
    LocationDTO getLocation(long locationId);
    LocationDTO addLocation(MemberDTO memberDTO,LocationDTO locationDTO) throws Exception;
    void deleteLocation(MemberDTO memberDTO, Long id) throws Exception;
    LocationDTO updateSearchRange(LocationDTO locationDTO);
    LocationDTO addShareLocation(Long latitude,Long longitude);
    LocationDTO getMemberRepLocation(Long memberId);
    void setRepLocation(Long memberId,Long locationId);
    String reverseGeoCoder(double latitude, double longitude);
    Map<String, String> geoCoder(String address);
    List<?> locationFilter(CategoryType categoryType, LocationDTO locationDTO, Pageable pageable, String keyword) throws Exception;
}
