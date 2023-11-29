package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.repository.member.MemberRelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional

public class MemberRelationshipServiceImpl implements MemberRelationshipService {

    private final MemberRelationshipRepository memberRelationshipRepository;
    @Override
    public void addBlock(MemberRelationshipDTO memberRelationshipDTO, Member sourceMemberId, Member targetMemberId, String content) {
        memberRelationshipDTO.setTargetMemberId(targetMemberId);
        memberRelationshipDTO.setContent(content);

        //relationship.setSourceMember(memberRepository.findById(sourceMemberId)
        //        .orElseThrow(() -> new RuntimeException("해당 ID의 회원이 존재하지 않습니다.")));
        //relationship.setTargetMember(memberRepository.findByNickname(targetMemberNickname));

        //memberRelationshipRepository.save(addBlock);
        memberRelationshipDTO.setTargetMemberId(memberRelationshipDTO.getTargetMemberId());
        memberRelationshipRepository.save(memberRelationshipDTO.toEntity());
    }

    @Override
    public void deleteBlock(MemberRelationshipDTO memberRelationshipDTO) {

    }

    @Override
    public void listBlock(MemberRelationshipDTO memberRelationshipDTO){

    }

    @Override
    public void getBlock(Long id){

    }

    @Override
    public void addFollowing(MemberRelationshipDTO memberRelationshipDTO){

    }

    @Override
    public void deleteFollowing(Long id, String nickname){

    }

    @Override
    public void listFollowing(Long id, String nickname){

    }



}
