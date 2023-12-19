package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.ClubPostCategory;
import lombok.Builder;
import lombok.Data;

@Data
public class ClubPostCategoryDTO {

    private Long id;
    private Long clubId;
    private String name;

    @Builder
    public ClubPostCategoryDTO(Long id, Long clubId, String name){
        this.id = id;
        this.clubId = clubId;
        this.name = name;
    }

    public ClubPostCategory toEntity(){
        return ClubPostCategory.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }

    public static ClubPostCategoryDTO fromEntity(ClubPostCategory entity){
        return ClubPostCategoryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

}
