package com.fiveguys.koguma.data.dto.club;

import com.fiveguys.koguma.data.entity.ClubPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<String> images;
    private Boolean activeFlag;
    private LocalDateTime regDate;


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
                .build();
    }
}
