package com.fiveguys.koguma.service.common;


import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Location;
import com.fiveguys.koguma.repository.common.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{


    private final LocationRepository locationRepository;

    @Value("${ncloud.reverseGeo.clientId}")
    private String clientId;

    @Value("${ncloud.reverseGeo.clientSecret}")
    private String clientSecret;

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
    public String reverseGeoCoder(double latitude, double longitude){   //좌표 입력하여 상세 주소 얻기
        String coord = longitude+","+latitude;
        String dong = null;
        final String requestMethod = "GET";
        final String hostName = "https://naveropenapi.apigw.ntruss.com";     //요청코드
        final String requestUrl= "/map-reversegeocode/v2/gc";                //https://api-gov.ncloud-docs.com/docs/ai-naver-mapsreversegeocoding-gc
        String option = "?coords="+coord+"&orders=admcode&output=json";
        final String requestFullUrl = hostName + requestUrl+option;          //legalcode,addr,admcode,roadaddr

        HttpClient httpClient = HttpClient.newHttpClient();                 //legalcode = 법정동 admcode = 행정동

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestFullUrl))
                .header("X-NCP-APIGW-API-KEY-ID", clientId)
                .header("X-NCP-APIGW-API-KEY", clientSecret)
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();
            System.out.println(responseBody);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            // 필요한 값 추출
            JsonNode area3Node = jsonNode.path("results").get(0).path("region").path("area3");
            dong = area3Node.path("name").asText();
            System.out.println("Area3 Name: " + dong);



        } catch (Exception e) {
            e.printStackTrace();
        }
        return dong;
    }

//    public List<Object> LocationFilter(LocationDTO locationDTO){
//
//        List<Object> filterList = new ArrayList<>();
//
//        double latitude = locationDTO.getLatitude();
//        double longitude = locationDTO.getLongitude();
//        int searchRange = locationDTO.getSearchRange();
//
//        filterList = locationRepository.findAllByDistance("products",longitude,latitude,searchRange);
//
//
//        return filterList;
//    }


}

