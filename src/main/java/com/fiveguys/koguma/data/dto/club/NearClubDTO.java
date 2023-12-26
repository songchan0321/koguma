package com.fiveguys.koguma.data.dto.club;

import com.fiveguys.koguma.data.dto.ImageDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NearClubDTO {

    private Long clubId;
    private Double latitude;
    private Double longitude;
    private String dong;
    private List<ImageDTO> profileImage;
    private MemberDTO memberDTO;

    public static NearClubDTO fromEntity(Club entity){
        return NearClubDTO.builder()
                .clubId(entity.getId())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .dong(entity.getDong())
                .profileImage(entity.getImages().stream().map(ImageDTO::fromEntity).collect(Collectors.toList()))
                .build();
    }
}
