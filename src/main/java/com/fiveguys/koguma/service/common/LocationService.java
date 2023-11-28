package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.LocationDTO;

import java.util.List;

public interface LocationService {

    List<LocationDTO> listLocation(Long id);
    void addLocation(LocationDTO locationDTO);

    void deleteLocation(Long id);
    void updateSearchRange(Long id,int range);
    LocationDTO addShareLocation();
    //LocationFilter();
}
