package com.fiveguys.koguma.data.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CreateClubDTO {

    private Long categoryId;
    private String title;
    private String content;
    private String nickname;
    private String memberContent;
    private List<String> urls;
    private Integer maxCapacity;

    @Builder
    public CreateClubDTO(Long categoryId, String title,
                         String content, Integer maxCapacity, String nickname,
                         String memberContent){
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.maxCapacity = maxCapacity;
        this.nickname = nickname;
        this.memberContent = memberContent;
    }

    public CreateClubDTO toEntity(){
        return CreateClubDTO.builder()
                .categoryId(this.categoryId)
                .title(this.title)
                .content(this.content)
                .nickname(this.nickname)
                .memberContent(this.memberContent)
                .maxCapacity(this.maxCapacity)
                .build();
    }
}
