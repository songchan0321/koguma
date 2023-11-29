package com.fiveguys.koguma.service.common;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
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
    public void setRepLocation(Long memberId,Long locationId) {

        Location location = locationRepository.findById(locationId).orElse(null);

        if (location != null){
            Location beforeRepLocation = locationRepository.findByMemberIdAndRepAuthLocationFlag(memberId,true);
            beforeRepLocation.setRepAuthLocationFlag(false);
            location.setRepAuthLocationFlag(true);
        }
    }


    public void addLocation(LocationDTO locationDTO) throws Exception {

        List<Location> locations =  locationRepository.findAllByMemberId(locationDTO.getMemberDTO().getId());
        if (locations.size() >= 3){
            throw new Exception("인증된 위치가 초과 됐습니다.");
        } else if (locations.isEmpty()) {
            locationDTO.setRepAuthLocationFlag(true);
            locationRepository.save(locationDTO.toEntity());
        } else {
            locationDTO.setRepAuthLocationFlag(false);
            locationRepository.save(locationDTO.toEntity());
        }
    }

    @Transactional
    public void deleteLocation(MemberDTO memberDTO, Long id) throws Exception {
        List<Location> locations =  locationRepository.findAllByMemberId(memberDTO.getId());
        if (locations.size() == 1){
            throw new Exception("인증된 위치는 1개 이상 존재해야 합니다.");
        }
        else {
            Location location = locationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 인증된 위치를 찾을 수 없습니다."));
            if (location.isRepAuthLocationFlag()) {
                locationRepository.deleteById(location.getId());
                locations.get(0).setRepAuthLocationFlag(true);
            }
        }
    }
    public LocationDTO getLocation(Long id){

        Location location = locationRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("위치를 가져올 수 없습니다."));
        return LocationDTO.fromEntity(location);
    }

    @Transactional
    public void updateSearchRange(LocationDTO locationDTO,int range) {
        Location location = locationRepository.findById(locationDTO.getId()).orElseThrow(()->new IllegalArgumentException("업데이트 할 수 없습니다."));
        location.setSearchRange(range);
    }

    public LocationDTO addShareLocation(Long latitude,Long longitude) {
        //이미지 추가후 구현
        return null;
    }

    public LocationDTO getMemberRepLocation(Long id) {
        Location location = locationRepository.findByMemberIdAndRepAuthLocationFlag(id,true);
        return LocationDTO.fromEntity(location);
    }


}

