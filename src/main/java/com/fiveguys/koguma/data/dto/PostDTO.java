package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Category;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Post;
import lombok.*;

import java.nio.file.LinkOption;

@Data
public class PostDTO {
    private Long id;
    private MemberDTO memberDTO;
    private CategoryDTO categoryDTO;
    private String categoryName;
    private ClubPostCategoryDTO clubPostCategoryDTO;
    private String clubPostCategoryName;
    private String title;
    private String content;
    private Boolean postType;
    private Double latitude;
    private Double longitude;
    private String dong;
    private int views;
    private Boolean activeFlag;


    @Builder
    public PostDTO(Long id, MemberDTO memberDTO, CategoryDTO categoryDTO, String categoryName,
                   ClubPostCategoryDTO clubPostCategoryDTO, String clubPostCategoryName,
                   String title, String content, Boolean postType, Double latitude,
                   Double longitude, String dong, int views, Boolean activeFlag){
        this.id = id;
        this.memberDTO = memberDTO;
        this.categoryDTO = categoryDTO;
        this.categoryName = categoryName;
        this.clubPostCategoryDTO = clubPostCategoryDTO;
        this.clubPostCategoryName = clubPostCategoryName;
        this.title = title;
        this.content = content;
        this.postType = postType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dong = dong;
        this.views = views;
        this.activeFlag = activeFlag;

    }



    public Post toEntity(){
        return  Post.builder()
                .id(this.id)
                .member(memberDTO.toEntity())
                .category(categoryDTO.toEntity())
                .categoryName(this.categoryName)
                .clubPostCategory(clubPostCategoryDTO.toEntity())
               // .clubPostCategoryName(this.clubPostCategoryName)
                .title(this.title)
                .content(this.content)
                .postType(this.postType)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .dong(this.dong)
                .views(this.views)
                .activeFlag(this.activeFlag)
                .build();

    }


    public static PostDTO fromEntity(Post post){
        return PostDTO.builder()
                .id(post.getId())
                .memberDTO(MemberDTO.fromEntity(post.getMember()))
                //.categoryDTO(CategoryDTO.fromEntity(post.getCategory()))
                .categoryName(post.getCategoryName())
                .clubPostCategoryDTO(ClubPostCategoryDTO.fromEntity(post.getClubPostCategory()))
                .clubPostCategoryName(post.getClubPostCategoryName())
                .title(post.getTitle())
                .content(post.getContent())
                .postType(post.getPostType())
                .latitude(post.getLatitude())
                .longitude(post.getLongitude())
                .views(post.getViews()) // 조회수 정보 추가
                .activeFlag(post.getActiveFlag())
                .build();
    }
}
