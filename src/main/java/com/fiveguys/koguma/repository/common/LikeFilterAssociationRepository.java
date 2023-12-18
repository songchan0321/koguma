package com.fiveguys.koguma.repository.common;

import com.fiveguys.koguma.data.dto.LikeFilterAssociationDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.data.entity.LikeFilterAssociation;
import com.fiveguys.koguma.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeFilterAssociationRepository extends JpaRepository<LikeFilterAssociation,Long> {


    List<LikeFilterAssociation> findAllByMemberId(Long memberId);

    Optional<LikeFilterAssociation> findByMemberIdAndProductId(Long memberId, Long productId);

    @Query("SELECT lfa FROM LikeFilterAssociation lfa LEFT JOIN FETCH lfa.product p LEFT JOIN FETCH p.image WHERE lfa.member.id = :memberId")
    List<LikeFilterAssociation> findAllWithProductAndImagesByMemberId(@Param("memberId") Long memberId);
}
