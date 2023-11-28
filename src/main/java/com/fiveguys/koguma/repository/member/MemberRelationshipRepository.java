package com.fiveguys.koguma.repository.member;

import com.fiveguys.koguma.data.entity.MemberRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRelationshipRepository extends JpaRepository<MemberRelationship, Long>{

}