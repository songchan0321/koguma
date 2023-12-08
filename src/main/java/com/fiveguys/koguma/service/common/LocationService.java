package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.CategoryType;
import com.fiveguys.koguma.data.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocationService {

    Page<Location> listLocation(MemberDTO memberDTO,int page);
    LocationDTO getLocation(long locationId);
    LocationDTO addLocation(MemberDTO memberDTO,LocationDTO locationDTO) throws Exception;
    void deleteLocation(MemberDTO memberDTO, Long id) throws Exception;
    LocationDTO updateSearchRange(LocationDTO locationDTO,int range);
    LocationDTO addShareLocation(Long latitude,Long longitude);
    LocationDTO getMemberRepLocation(Long memberId);
    void setRepLocation(Long memberId,Long locationId);
    String reverseGeoCoder(double latitude, double longitude);
    Page<Object> locationFilter(CategoryType categoryType, LocationDTO locationDTO, Pageable pageable, String keyword) throws Exception;
}
