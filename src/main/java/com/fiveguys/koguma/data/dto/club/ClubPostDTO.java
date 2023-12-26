package com.fiveguys.koguma.data.dto.club;

import com.fiveguys.koguma.data.dto.ImageDTO;
import com.fiveguys.koguma.data.entity.ClubPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubPostDTO {

    private Long id;
    private Long clubId;
    private Long clubCategoryId;
    private String categoryName;
    private String title;
    private String content;
    private String clubName;
    private String clubMemberNickname;
    private String images;
    private List<ImageDTO> profileImage;
    private Boolean activeFlag;
    private LocalDateTime regDate;
    private String memberProfile;
    private int views;



    public static ClubPostDTO fromEntity(ClubPost clubPost){
        return ClubPostDTO.builder()
                .id(clubPost.getId())
                .clubId(clubPost.getClub().getId())
                .clubCategoryId(clubPost.getClubPostCategory().getId())
                .categoryName(clubPost.getCategoryName())
                .title(clubPost.getTitle())
                .clubName(clubPost.getClubName())
                .clubMemberNickname(clubPost.getClubMemberNickname())
                .content(clubPost.getContent())
                .regDate(clubPost.getRegDate())
                .images(clubPost.getImages())
                .profileImage(clubPost.getClub().getImages().stream().map(ImageDTO::fromEntity).collect(Collectors.toList()))
                .views(clubPost.getViews())
                .build();
    }
}
