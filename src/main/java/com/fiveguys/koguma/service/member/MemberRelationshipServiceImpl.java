package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.MemberRelationship;
import com.fiveguys.koguma.data.entity.MemberRelationshipType;
import com.fiveguys.koguma.repository.member.MemberRelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional

public class MemberRelationshipServiceImpl implements MemberRelationshipService {

    private final MemberRelationshipRepository memberRelationshipRepository;
    @Override
    public void addBlock(MemberRelationshipDTO memberRelationshipDTO, Member sourceMemberId, Member targetMemberId, String content) {

        memberRelationshipDTO.setSourceMemberId(sourceMemberId);
        memberRelationshipDTO.setTargetMemberId(targetMemberId);
        memberRelationshipDTO.setContent(content);
        memberRelationshipDTO.setMemberRelationshipType(MemberRelationshipType.BLOCK);


        memberRelationshipDTO.setSourceMemberId(memberRelationshipDTO.getSourceMemberId());
        memberRelationshipRepository.save(memberRelationshipDTO.toEntity());
    }

    @Override
    public void deleteBlock(MemberRelationshipDTO memberRelationshipDTO) {
        memberRelationshipDTO.setSourceMemberId(null);
        memberRelationshipDTO.setTargetMemberId(null);
        memberRelationshipDTO.setContent(null);
        memberRelationshipDTO.setMemberRelationshipType(null);

        memberRelationshipRepository.save(memberRelationshipDTO.toEntity());

    }

    @Override
    public List<MemberRelationshipDTO> listBlock() {
        List<MemberRelationship> memberRelationships = memberRelationshipRepository.findAll();
        return memberRelationships.stream()
                .map(MemberRelationshipDTO::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public MemberRelationshipDTO getBlock(Long id){
        return MemberRelationshipDTO.fromEntity(memberRelationshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 회원이 존재하지 않습니다.")));

    }

    @Override
    public void addFollowing(MemberRelationshipDTO memberRelationshipDTO, Member sourceMemberId, Member targetMemberId){
        memberRelationshipDTO.setSourceMemberId(sourceMemberId);
        memberRelationshipDTO.setTargetMemberId(targetMemberId);
        memberRelationshipDTO.setMemberRelationshipType(MemberRelationshipType.Following);

        memberRelationshipDTO.setSourceMemberId(memberRelationshipDTO.getSourceMemberId());
        memberRelationshipRepository.save(memberRelationshipDTO.toEntity());

    }

    @Override
    public void deleteFollowing(MemberRelationshipDTO memberRelationshipDTO){

    }

    @Override
    public List<MemberRelationshipDTO> listFollowing() {
        List<MemberRelationship> memberRelationships = memberRelationshipRepository.findAll();
        return memberRelationships.stream()
                .map(MemberRelationshipDTO::fromEntity)
                .collect(Collectors.toList());
    }



}
