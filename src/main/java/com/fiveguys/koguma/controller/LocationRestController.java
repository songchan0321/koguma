package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Location;
import com.fiveguys.koguma.service.common.AuthService;
import com.fiveguys.koguma.service.common.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationRestController {

    private final LocationService locationService;
    private final AuthService authService;

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocation(@PathVariable Long id) throws Exception {
        MemberDTO memberDTO = authService.getAuthMember();
        LocationDTO locationDTO = locationService.getLocation(id);
        if (!Objects.equals(memberDTO.getId(), locationDTO.getMemberDTO().getId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(locationDTO);
    }
    @GetMapping("list")
    public ResponseEntity<Page<Location>> listLocation(@RequestParam int page) throws Exception {
        MemberDTO memberDTO = authService.getAuthMember();
        Page<Location> listLocation = locationService.listLocation(memberDTO,page);

        return ResponseEntity.status(HttpStatus.OK).body(listLocation);
    }
    @PostMapping("/new")
    public ResponseEntity<LocationDTO> addLocation(@RequestBody LocationDTO locationDTO) throws Exception {
        MemberDTO memberDTO = authService.getAuthMember();

        String dong = locationService.reverseGeoCoder(
                locationDTO.getLatitude(), locationDTO.getLongitude());
        locationDTO.setDong(dong);
        locationDTO = locationService.addLocation(memberDTO,locationDTO);

        return ResponseEntity.status(HttpStatus.OK).body(locationDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable Long id) throws Exception {
        MemberDTO memberDTO = authService.getAuthMember();
        LocationDTO locationDTO = locationService.getLocation(id);
        if (!Objects.equals(memberDTO.getId(), locationDTO.getMemberDTO().getId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        locationService.deleteLocation(memberDTO,id);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("위치 삭제 완료");
    }

    @PutMapping("/")
    public ResponseEntity<LocationDTO> updateSearchRange(@RequestBody Map<String, Integer> json) throws Exception {
        MemberDTO memberDTO = authService.getAuthMember();
        LocationDTO locationDTO = locationService.getLocation(json.get("locationId"));
        if (!Objects.equals(memberDTO.getId(), locationDTO.getMemberDTO().getId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        locationDTO = locationService.updateSearchRange(locationDTO,json.get("range"));

        return ResponseEntity.status(HttpStatus.OK).body(locationDTO);
    }
    @PostMapping("/rep/{id}")
    public ResponseEntity<String> setRepLocation(@PathVariable Long id) throws Exception {
        MemberDTO memberDTO = authService.getAuthMember();
        LocationDTO locationDTO = locationService.getLocation(id);
        if (!Objects.equals(memberDTO.getId(), locationDTO.getMemberDTO().getId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        locationService.setRepLocation(memberDTO.getId(),id);
        return ResponseEntity.status(HttpStatus.OK).body(locationDTO.getDong()+" 설정!!");
    }



}
