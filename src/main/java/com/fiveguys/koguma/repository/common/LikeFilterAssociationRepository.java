package com.fiveguys.koguma.repository.common;

import com.fiveguys.koguma.data.entity.LikeFilterAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeFilterAssociationRepository extends JpaRepository<LikeFilterAssociation,Long> {


}
