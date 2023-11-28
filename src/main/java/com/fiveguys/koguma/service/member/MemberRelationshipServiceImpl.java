package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.entity.MemberRelationship;
import com.fiveguys.koguma.repository.member.MemberRelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional

public class MemberRelationshipServiceImpl implements MemberRelationshipService {

    private final MemberRelationshipRepository memberRelationshipRepository;
    @Override
    public void addBlock(MemberRelationshipDTO memberRelationshipDTO, Long sourceMemberId, Long targetMemberId, String content) {
        memberRelationshipDTO.setTargetMemberId(targetMemberId);
        memberRelationshipDTO.setContent(content);

        //relationship.setSourceMember(memberRepository.findById(sourceMemberId)
        //        .orElseThrow(() -> new RuntimeException("해당 ID의 회원이 존재하지 않습니다.")));
        //relationship.setTargetMember(memberRepository.findByNickname(targetMemberNickname));

        //memberRelationshipRepository.save(addBlock);
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
