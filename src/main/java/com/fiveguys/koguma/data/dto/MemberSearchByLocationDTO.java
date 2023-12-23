package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.MemberRoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class MemberSearchByLocationDTO {
    private Long id;
    private String nickname;
    private String dong;
    private Float score;
    private String profileURL;

    @Builder
    public MemberSearchByLocationDTO(Long id, String nickname, String dong, Float score, String profileURL) {
        this.id = id;
        this.nickname = nickname;
        this.dong = dong;
        this.score = score;
        this.profileURL = profileURL;
    }




    public static MemberSearchByLocationDTO fromEntity(Member member) {
        MemberSearchByLocationDTO.MemberSearchByLocationDTOBuilder builder = MemberSearchByLocationDTO.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .score(member.getScore())
                .profileURL(member.getProfileURL());
        return builder.build();

    }

}

