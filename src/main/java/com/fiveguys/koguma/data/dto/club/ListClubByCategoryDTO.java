package com.fiveguys.koguma.data.dto.club;

import com.fiveguys.koguma.data.dto.CategoryDTO;
import com.fiveguys.koguma.data.dto.ImageDTO;
import com.fiveguys.koguma.data.entity.Club;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ListClubByCategoryDTO {


    private Long id;
    private String title;
    private String content;
    private Integer maxCapacity;
    private Double latitude;
    private Double longitude;
    private String dong;
    private Boolean joinActiveFlag;
    private Boolean activeFlag;
    private String categoryName;
    private CategoryDTO categoryDTO;
    private List<ImageDTO> profileImage;
    private Integer currentCount;

    @Builder
    public ListClubByCategoryDTO(Long id, String title, String content, Integer maxCapacity,
                   Double latitude, Double longitude, String dong,
                   Boolean joinActiveFlag, Boolean activeFlag,
                   String categoryName, CategoryDTO categoryDTO, List<ImageDTO> profileImage,Integer currentCount){
        this.id = id;
        this.title = title;
        this.content = content;
        this.maxCapacity = maxCapacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dong = dong;
        this.joinActiveFlag = joinActiveFlag;
        this.activeFlag = activeFlag;
        this.categoryName = categoryName;
        this.categoryDTO = categoryDTO;
        this.profileImage = profileImage;
        this.currentCount = currentCount;
    }

    public ListClubByCategoryDTO(Club club){
        this.id = club.getId();
        this.title = club.getTitle();
        this.content = club.getContent();
        this.maxCapacity = club.getMaxCapacity();
        this.latitude = club.getLatitude();
        this.longitude = club.getLongitude();
        this.dong = club.getDong();
        this.joinActiveFlag = club.isJoinActiveFlag();
        this.activeFlag = club.isActiveFlag();
        this.categoryName = club.getCategoryName();
        this.categoryDTO = CategoryDTO.fromDTO(club.getCategory());
    }

    public Club toEntity(){
        return Club.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .maxCapacity(this.maxCapacity)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .dong(this.dong)
                .joinActiveFlag(this.joinActiveFlag)
                .activeFlag(this.activeFlag)
                .categoryName(this.categoryName)
                .category(categoryDTO.toEntity())
                .build();
    }

    public static ListClubByCategoryDTO fromEntity(Club entity){
        return ListClubByCategoryDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .maxCapacity(entity.getMaxCapacity())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .dong(entity.getDong())
                .joinActiveFlag(entity.isJoinActiveFlag())
                .activeFlag(entity.isActiveFlag())
                .categoryName(entity.getCategoryName())
                .categoryDTO(CategoryDTO.fromDTO(entity.getCategory()))
                .profileImage(entity.getImages().stream().map(ImageDTO::fromEntity).collect(Collectors.toList()))
                .currentCount(entity.getClubMembers().size())
                .build();
    }

}
