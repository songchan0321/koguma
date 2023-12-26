package com.fiveguys.koguma.data.dto.club;

import com.fiveguys.koguma.data.entity.ClubComment;
import kotlinx.datetime.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubPostCommentDTO {

    private Long id;
    private Long clubId;
    private Long clubPostId;
    private String content;
    private String clubMemberNickname;
    private LocalDateTime regDate;
    private String memberProfile;
    private String clubTitle;
    private int count;

    public static ClubPostCommentDTO fromEntity(ClubComment entity){
        return ClubPostCommentDTO.builder()
                .id(entity.getId())
                .clubId(entity.getClub().getId())
                .clubPostId(entity.getClubPost().getId())
                .content(entity.getContent())
                .memberProfile(entity.getClubMember().getMember().getProfileURL())
                .clubTitle(entity.getClub().getTitle())
                .clubMemberNickname(entity.getClubMember().getNickname())
                .build();
    }
}
