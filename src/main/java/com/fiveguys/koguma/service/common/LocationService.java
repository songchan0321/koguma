package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.LocationDTO;

import java.util.List;

public interface LocationService {

    List<LocationDTO> listLocation(Long id);
    LocationDTO getLocation(Long id);
    void updateLocation(Long id);
    void addLocation(Long id);

    void deleteLocation(Long id);
    void updateSearchRange();
    LocationDTO addShareLocation();
    //LocationFilter();
    LocationDTO getCoordinate(String ip);
}
