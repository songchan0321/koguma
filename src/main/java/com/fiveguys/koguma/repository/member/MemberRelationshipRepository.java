package com.fiveguys.koguma.repository.member;

import com.fiveguys.koguma.data.entity.MemberRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fiveguys.koguma.data.entity.MemberRelationshipType;

import java.util.List;

@Repository
public interface MemberRelationshipRepository extends JpaRepository<MemberRelationship, Long>{


    List<MemberRelationship> findAllBySourceMemberIdAndMemberRelationshipType(Long sourceMemberId, MemberRelationshipType memberRelationshipType);

    List<MemberRelationship> findBySourceMemberIdAndMemberRelationshipType(Long sourceMemberId, MemberRelationshipType memberRelationshipType);

    List<MemberRelationship> findBySourceMemberIdAndTargetMemberIdAndMemberRelationshipType(Long sourceMemberId, Long targetMemberId, MemberRelationshipType memberRelationshipType);

    MemberRelationship findBySourceMemberId(Long sourceMemberId);

    MemberRelationship findByTargetMemberIdAndSourceMemberIdAndMemberRelationshipType(Long targetMemberId, Long sourceMemberId, MemberRelationshipType memberRelationshipType);
}