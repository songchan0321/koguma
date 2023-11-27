package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.entity.MemberRelationship;

import java.time.LocalDateTime;

public class MemberRelationshipServiceImpl {

    @Override
    public MemberRelationship addBlock(Long sourceMemberId, String targetMemberNickname, String content, Boolean type) {
        // 차단 또는 팔로잉 로직 구현
        MemberRelationship relationship = new MemberRelationship();
        relationship.setSourceMember(memberRepository.findById(sourceMemberId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 회원이 존재하지 않습니다.")));
        relationship.setTargetMember(memberRepository.findByNickname(targetMemberNickname));
        relationship.setContent(content);
        relationship.setType(type);
        relationship.setRegDate(LocalDateTime.now());

        return relationshipRepository.save(relationship);
    }
}
