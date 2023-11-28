package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.ClubPostCategory;
import lombok.Builder;
import lombok.Data;

@Data
public class ClubPostCategoryDTO {

    private Long id;
    private ClubDTO clubDTO;
    private String name;

    @Builder
    public ClubPostCategoryDTO(Long id, ClubDTO clubDTO, String name){
        this.id = id;
        this.clubDTO = clubDTO;
        this.name = name;
    }

    public ClubPostCategory toEntity(){
        return ClubPostCategory.builder()
                .id(this.id)
                .club(clubDTO.toEntity())
                .name(this.name)
                .build();
    }

    public static ClubPostCategoryDTO fromEntity(ClubPostCategory entity){
        return ClubPostCategoryDTO.builder()
                .id(entity.getId())
                .clubDTO(ClubDTO.fromEntity(entity.getClub()))
                .name(entity.getName())
                .build();
    }

}
