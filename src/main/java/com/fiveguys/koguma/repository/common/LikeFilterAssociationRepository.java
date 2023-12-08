package com.fiveguys.koguma.repository.common;

import com.fiveguys.koguma.data.entity.LikeFilterAssociation;
import com.fiveguys.koguma.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeFilterAssociationRepository extends JpaRepository<LikeFilterAssociation,Long> {


    Page<Product> findAllByMemberId(Long memberId, Pageable pageable);
}
