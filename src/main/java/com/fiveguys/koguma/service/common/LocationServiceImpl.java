package com.fiveguys.koguma.service.common;


import java.util.ArrayList;
import java.util.List;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.entity.Location;
import com.fiveguys.koguma.repository.common.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{


    private final LocationRepository locationRepository;

    public List<LocationDTO> listLocation(Long id) {
        List<Location> locations = locationRepository.findAllByMemberId(id);
        List<LocationDTO> locationDTOList = new ArrayList<>();

        for (Location location : locations){
            LocationDTO locationDTO = LocationDTO.fromEntity(location);
            locationDTOList.add(locationDTO);
        }

        return locationDTOList;
    }


    @Transactional
    public void setRepLocation(Long id) {
        Location location = locationRepository.findById(id).orElse(null);
        if (location != null){
            Location beforeRepLocation = (Location) locationRepository.findByRepAuthLocationFlagIs(true);
            beforeRepLocation.setRepAuthLocationFlag(false);
            location.setRepAuthLocationFlag(true);
        }
    }


    public void addLocation(LocationDTO locationDTO) throws Exception {

        List<Location> locations =  locationRepository.findAllByMemberId(locationDTO.getId());
        if (locations.size() > 3){
            throw new Exception("인증된 위치가 초과 됐습니다.");
        }
        else
            locationRepository.save(locationDTO.toEntity());

    }

    public void deleteLocation(Long id) {
        Location location = locationRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 인증된 위치를 찾을 수 없습니다."));
        locationRepository.deleteById(location.getId());
    }

    @Transactional
    public void updateSearchRange(Long id,int range) {
        Location location = locationRepository.findById(id).orElse(null);
        locationRepository.findById(id).orElseThrow().setSearchRange(range);

    }

    public LocationDTO addShareLocation() {
        return null;
    }



}

