package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.entity.MemberRelationship;
import com.fiveguys.koguma.data.entity.MemberRelationshipType;
import com.fiveguys.koguma.repository.member.MemberRelationshipRepository;
import com.fiveguys.koguma.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional

public class MemberRelationshipServiceImpl implements MemberRelationshipService {

    private final MemberRelationshipRepository memberRelationshipRepository;
    private final MemberRepository memberRepository;

    @Override
    public void addBlock(MemberRelationshipDTO memberRelationshipDTO) {

        memberRelationshipRepository.save(memberRelationshipDTO.toEntity());
    }

    @Override
    public void deleteBlock(Long sourceMember, Long targetMember) {
        List<MemberRelationship> blockRelationships = memberRelationshipRepository
                .findBySourceMemberIdAndTargetMemberIdAndMemberRelationshipType(sourceMember, targetMember, MemberRelationshipType.BLOCK);

        blockRelationships.forEach(relationship -> {
            relationship.setContent(null);
            relationship.setMemberRelationshipType(null);
        });

        memberRelationshipRepository.saveAll(blockRelationships);
    }


    @Override
    public List<MemberRelationshipDTO> listBlock(Long sourceMemberId) {
        List<MemberRelationship> memberRelationships = memberRelationshipRepository.findAllBySourceMemberIdAndMemberRelationshipType(sourceMemberId, MemberRelationshipType.BLOCK);
        return memberRelationships.stream()
                .filter(memberRelationship -> memberRelationship.getMemberRelationshipType() == MemberRelationshipType.BLOCK)
                .map(MemberRelationshipDTO::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public List<MemberRelationshipDTO> listFollowing(Long sourceMemberId) {
        List<MemberRelationship> memberRelationships = memberRelationshipRepository.findAllBySourceMemberIdAndMemberRelationshipType(sourceMemberId, MemberRelationshipType.FOLLOWING);
        return memberRelationships.stream()
                .filter(memberRelationship -> memberRelationship.getMemberRelationshipType() == MemberRelationshipType.FOLLOWING)
                .map(MemberRelationshipDTO::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public MemberRelationshipDTO getBlock(Long sourceMemberId) {
        List<MemberRelationship> blockRelationships = memberRelationshipRepository.findBySourceMemberIdAndMemberRelationshipType(sourceMemberId, MemberRelationshipType.BLOCK);

        if (blockRelationships.isEmpty()) {
            throw new NoResultException("차단 정보 없음");
        }


        return MemberRelationshipDTO.fromEntity(blockRelationships.get(0));
    }

    @Override
    public MemberRelationshipDTO getFollowing(Long sourceMemberId) {
        List<MemberRelationship> followingRelationships = memberRelationshipRepository.findBySourceMemberIdAndMemberRelationshipType(sourceMemberId, MemberRelationshipType.FOLLOWING);

        if (followingRelationships.isEmpty()) {
            throw new NoResultException("팔로잉 정보 없음");
        }


        return MemberRelationshipDTO.fromEntity(followingRelationships.get(0));
    }

    @Override
    public void addFollowing(MemberRelationshipDTO memberRelationshipDTO) {

        memberRelationshipRepository.save(memberRelationshipDTO.toEntity());

    }

    @Override
    public void deleteFollowing(Long sourceMember, Long targetMember) {
        List<MemberRelationship> followingRelationships = memberRelationshipRepository
                .findBySourceMemberIdAndTargetMemberIdAndMemberRelationshipType(sourceMember, targetMember, MemberRelationshipType.FOLLOWING);

        followingRelationships.forEach(relationship -> {
            relationship.setContent(null);
            relationship.setMemberRelationshipType(null);
        });

        memberRelationshipRepository.saveAll(followingRelationships);
    }


}

   /* @Override
    public List<MemberRelationshipDTO> listFollowing(Long sourceMemberId) {
            List<MemberRelationship> memberRelationships = memberRelationshipRepository.findAll();

            return memberRelationships.stream()
                    .map(MemberRelationshipDTO::fromEntity)
                    .collect(Collectors.toList());
    }*/




