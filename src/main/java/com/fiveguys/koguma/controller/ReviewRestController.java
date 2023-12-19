package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ReviewDTO;
import com.fiveguys.koguma.data.entity.Review;
import com.fiveguys.koguma.service.common.AuthService;
import com.fiveguys.koguma.service.product.ProductService;
import com.fiveguys.koguma.service.product.ReviewService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/review")
public class ReviewRestController {

    private final ReviewService reviewService;
    private final ProductService productService;
    private final AuthService authService;
    @GetMapping("/{productId}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long productId,@RequestParam String type,@CurrentMember MemberDTO memberDTO) throws Exception {
        ReviewDTO reviewDTO = reviewService.getReview(productId,Boolean.parseBoolean(type));
        return ResponseEntity.status(HttpStatus.OK).body(reviewDTO);
    }
    @PostMapping("/new") //reviewDTO add시 productDTO와 buyerDTO 넣어야함
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO reviewDTO,@CurrentMember MemberDTO memberDTO) throws Exception {

        reviewDTO.setMemberDTO(reviewDTO.getProductDTO().getBuyerDTO());  // 판매자
        if(reviewService.isPossibleAdd(reviewDTO)) { // true = 리뷰가 존재 false = 리뷰가 없음

            return ResponseEntity.status(HttpStatus.OK).build();

        }
        reviewDTO = reviewService.addReview(reviewDTO);

        return ResponseEntity.status(HttpStatus.OK).body(reviewDTO);
    }

}
