package com.fiveguys.koguma.service.product;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ReviewDTO;
import com.fiveguys.koguma.data.entity.Review;
import com.fiveguys.koguma.data.entity.ReviewId;
import org.springframework.data.domain.Page;

public interface ReviewService {

    ReviewDTO addReview(ReviewDTO reviewDTO) throws Exception;
    ReviewDTO getReview(Long productId,boolean seller);
    Boolean isPossibleAdd(ReviewDTO reviewDTO);




}
