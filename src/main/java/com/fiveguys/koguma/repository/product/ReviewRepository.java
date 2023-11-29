package com.fiveguys.koguma.repository.product;

import com.fiveguys.koguma.data.entity.Review;
import com.fiveguys.koguma.data.entity.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
}
