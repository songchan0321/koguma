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
                                      String title, String content, Boolean postType, Double latitude,
                   Double longitude, String dong, int views, Boolean activeFlag){
        this.id = id;
        this.memberDTO = memberDTO;
        this.categoryDTO = categoryDTO;
        this.categoryName = categoryName;
        this.title = title;
        this.content = content;
        this.postType = postType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dong = dong;
        this.views = views;
        this.activeFlag = activeFlag;

    }

    public PostDTO() {

    }


    public Post toEntity(){
        Post.PostBuilder postBuilder = Post.builder()
                .id(this.id)
                .categoryName(this.categoryName)
                .title(this.title)
                .content(this.content)
                .postType(this.postType)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .dong(this.dong)
                .views(this.views)
                .activeFlag(this.activeFlag);

        // memberDTO가 null이 아닌 경우에만 member를 설정
        if (this.memberDTO != null) {
            postBuilder.member(this.memberDTO.toEntity());
        }

        // categoryDTO가 null이 아닌 경우에만 category를 설정
        if (this.categoryDTO != null) {
            postBuilder.category(this.categoryDTO.toEntity());
        }

        return postBuilder.build();
    }


    public static PostDTO fromEntity(Post post){
        PostDTO.PostDTOBuilder builder = PostDTO.builder()
                .id(post.getId())
                .categoryName(post.getCategoryName())
                .title(post.getTitle())
                .content(post.getContent())
                .postType(post.getPostType())
                .latitude(post.getLatitude())
                .longitude(post.getLongitude())
                .views(post.getViews()) // 조회수 정보 추가
                .activeFlag(post.getActiveFlag());

        if(post.getMember() != null){
            builder = builder.memberDTO(MemberDTO.fromEntity(post.getMember()));
        }

        if(post.getCategory() != null){
            builder =builder.categoryDTO(CategoryDTO.fromDTO(post.getCategory()));
        }

        return builder.build();
    }
}
