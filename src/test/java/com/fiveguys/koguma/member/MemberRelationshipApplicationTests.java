package com.fiveguys.koguma.member;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.MemberRelationship;
import com.fiveguys.koguma.data.entity.MemberRelationshipType;
import com.fiveguys.koguma.service.member.MemberRelationshipService;
import com.fiveguys.koguma.service.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberRelationshipApplicationTests {

    @Autowired
    private MemberRelationshipService memberRelationshipService;

    /*@Test
    @DisplayName("차단 추가 테스트")
    @Transactional
    public void addBlockTest() {

        Member sourceMember = addMember("sourceUser");
        Member targetMember = addMember("targetUser");
        MemberRelationshipDTO memberRelationshipDTO = createMemberRelationshipDTO(sourceMember, targetMember, "Blocking content");


        memberRelationshipService.addBlock(memberRelationshipDTO, sourceMember, targetMember, "Blocking content");


        MemberRelationshipDTO addedBlock = memberRelationshipService.getBlock(memberRelationshipDTO.getId());

        assertAll(
                () -> assertEquals(sourceMember, addedBlock.getSourceMemberId()),
                () -> assertEquals(targetMember, addedBlock.getTargetMemberId()),
                () -> assertEquals("Blocking content", addedBlock.getContent()),
                () -> assertEquals(MemberRelationshipType.BLOCK, addedBlock.getMemberRelationshipType())
        );
    }


    private Member addMember(String nickname) {
        MemberDTO memberDTO = MemberDTO.builder()
                .nickname(nickname)
                .pw("password")
                .phone("010-1234-5678")
                .score(36.5F)
                .email(nickname + "@cex.com")
                .roleFlag(false)
                .socialFlag(false)
                .build();
        return memberService.addMember(memberDTO, nickname, "password", "010-1234-5678", 36.5F, nickname + "@cex.com", false, false);
    }


    private MemberRelationshipDTO createMemberRelationshipDTO(Member sourceMember, Member targetMember, String content) {
        return MemberRelationshipDTO.builder()
                .sourceMemberId(sourceMember)
                .targetMemberId(targetMember)
                .content(content)
                .memberRelationshipType(MemberRelationshipType.BLOCK)
                .build();
    }*/
}
