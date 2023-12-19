package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Location;
import com.fiveguys.koguma.service.common.AuthService;
import com.fiveguys.koguma.service.common.LocationService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationRestController {

    private final LocationService locationService;
    private final AuthService authService;

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocation(@PathVariable Long id,@CurrentMember MemberDTO memberDTO) throws Exception {

        LocationDTO locationDTO = locationService.getLocation(id);
        if (!Objects.equals(memberDTO.getId(), locationDTO.getMemberDTO().getId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(locationDTO);
    }
    @GetMapping("/list")
    public ResponseEntity<List<LocationDTO>> listLocation(@CurrentMember MemberDTO memberDTO) throws Exception {

        List<LocationDTO> listLocation = locationService.listLocation(memberDTO);

        return ResponseEntity.status(HttpStatus.OK).body(listLocation);
    }
    @PostMapping("/new")
    public ResponseEntity<LocationDTO> addLocation(@RequestBody LocationDTO locationDTO,@CurrentMember MemberDTO memberDTO) throws Exception {


        String dong = locationService.reverseGeoCoder(
                locationDTO.getLatitude(), locationDTO.getLongitude());
        locationDTO.setDong(dong);
        locationDTO.setSearchRange(2);
        locationDTO = locationService.addLocation(memberDTO,locationDTO);
        System.out.println("locationDTO : "+locationDTO);
        return ResponseEntity.status(HttpStatus.OK).body(locationDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable Long id,@CurrentMember MemberDTO memberDTO) throws Exception {

        locationService.deleteLocation(memberDTO,id);

        return ResponseEntity.status(HttpStatus.OK).body("위치 삭제 완료");
    }
    @GetMapping("/rep")
    public ResponseEntity<LocationDTO> getRepLocation(@CurrentMember MemberDTO memberDTO){
        return ResponseEntity.status(HttpStatus.OK).body(locationService.getMemberRepLocation(memberDTO.getId()));

    }

//    @PutMapping("/")
//    public ResponseEntity<LocationDTO> updateSearchRange(@RequestBody Map<String, Integer> json,@CurrentMember MemberDTO memberDTO) throws Exception {
//
//        LocationDTO locationDTO = locationService.getLocation(json.get("locationId"));
//        if (!Objects.equals(memberDTO.getId(), locationDTO.getMemberDTO().getId())){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        locationDTO = locationService.updateSearchRange(locationDTO,json.get("range"));
//
//        return ResponseEntity.status(HttpStatus.OK).body(locationDTO);
//    }
    @PutMapping("/")
    public ResponseEntity<LocationDTO> updateSearchRange(@RequestBody LocationDTO locationDTO,@CurrentMember MemberDTO memberDTO) throws Exception {

        locationDTO = locationService.updateSearchRange(locationDTO);

        return ResponseEntity.status(HttpStatus.OK).body(locationDTO);
    }
    @PutMapping("/rep/{id}")
    public ResponseEntity<String> setRepLocation(@PathVariable Long id,@CurrentMember MemberDTO memberDTO) throws Exception {

        LocationDTO locationDTO = locationService.getLocation(id);
        locationService.setRepLocation(memberDTO.getId(),id);
        return ResponseEntity.status(HttpStatus.OK).body(locationDTO.getDong()+" 설정!!");
    }

    @RequestMapping(value = "/kakao/getDot", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> getGeoDot(
            @RequestParam String address
//             @CurrentMember MemberDTO memberDTO
    ) {
        System.out.println("??");
        return ResponseEntity.ok().body(locationService.geoCoder(address));
    }

}
