package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Club;
import lombok.Builder;
import lombok.Data;

@Data
public class ClubDTO {

    private Long id;
    private String title;
    private String content;
    private Integer maxCapacity;

    @Builder
    public ClubDTO(Long id, String title, String content, Integer maxCapacity){
        this.id = id;
        this.title = title;
        this.content = content;
        this.maxCapacity = maxCapacity;
    }

    public Club toEntity(){
        return Club.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .maxCapacity(this.maxCapacity)
                .build();
    }

    public static ClubDTO fromDTO(Club entity){
        return ClubDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .maxCapacity(entity.getMaxCapacity())
                .build();
    }

}
