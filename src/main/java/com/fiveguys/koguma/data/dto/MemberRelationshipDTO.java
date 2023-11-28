package com.fiveguys.koguma.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MemberRelationshipDTO {

    public Long id;
    public Long sourceMemberId;
    public Long targetMemberId;
    public String content;
    public boolean type;
    public Date regDate;

}
