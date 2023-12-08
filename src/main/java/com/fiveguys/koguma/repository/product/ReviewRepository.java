package com.fiveguys.koguma.repository.product;

import com.fiveguys.koguma.data.entity.Review;
import com.fiveguys.koguma.data.entity.ReviewId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
    void findByIdMemberId(Long id);


    Optional<Review> findByIdProductId(Long productId);


    Page<Review> findAllByIdProductSellerId(Long id, Pageable pageable);
}
