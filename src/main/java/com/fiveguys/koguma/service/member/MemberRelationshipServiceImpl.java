package com.fiveguys.koguma.service.member;

import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.MemberRelationship;
import com.fiveguys.koguma.data.entity.MemberRelationshipType;
import com.fiveguys.koguma.repository.member.MemberRelationshipRepository;
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

    @Override
    public void addBlock(MemberRelationshipDTO memberRelationshipDTO) {

        memberRelationshipRepository.save(memberRelationshipDTO.toEntity());
    }

    @Override
    public void deleteBlock(MemberRelationshipDTO memberRelationshipDTO) {
        memberRelationshipDTO.setSourceMember(null);
        memberRelationshipDTO.setTargetMember(null);
        memberRelationshipDTO.setContent(null);
        memberRelationshipRepository.save(memberRelationshipDTO.toEntity());
        /*Optional<MemberRelationship> memberRelationshipOptional = memberRelationshipRepository.findById(id);

        if (memberRelationshipOptional.isPresent()) {
            MemberRelationship memberRelationship = memberRelationshipOptional.get();
            memberRelationshipRepository.delete(memberRelationship);
        }*/
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
            throw new NoResultException("차단 정보 없음");
        }


        return MemberRelationshipDTO.fromEntity(followingRelationships.get(0));
    }

    @Override
    public void addFollowing(MemberRelationshipDTO memberRelationshipDTO) {

        memberRelationshipRepository.save(memberRelationshipDTO.toEntity());

    }

    @Override
    public void deleteFollowing(Long id) {
        Optional<MemberRelationship> memberRelationshipOptional = memberRelationshipRepository.findById(id);

        if (memberRelationshipOptional.isPresent()) {
            MemberRelationship memberRelationship = memberRelationshipOptional.get();

            memberRelationshipRepository.delete(memberRelationship);


        }
    }


}

   /* @Override
    public List<MemberRelationshipDTO> listFollowing(Long sourceMemberId) {
            List<MemberRelationship> memberRelationships = memberRelationshipRepository.findAll();

            return memberRelationships.stream()
                    .map(MemberRelationshipDTO::fromEntity)
                    .collect(Collectors.toList());
    }*/




