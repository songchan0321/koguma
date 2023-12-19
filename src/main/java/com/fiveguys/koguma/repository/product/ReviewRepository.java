package com.fiveguys.koguma.repository.product;

import com.fiveguys.koguma.data.dto.ReviewDTO;
import com.fiveguys.koguma.data.entity.Review;
import com.fiveguys.koguma.data.entity.ReviewId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {




    Boolean existsByMemberIdAndProductIdAndSeller(Long id, Long id1, boolean seller);// true면 판매자 false면 구매자

    Review findByAndProductIdAndSeller(Long productId, boolean seller);
}
