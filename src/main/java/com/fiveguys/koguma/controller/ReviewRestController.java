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
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long id) throws Exception {
        ReviewDTO reviewDTO = reviewService.getReview(id);
        return ResponseEntity.status(HttpStatus.OK).body(reviewDTO);
    }
    @GetMapping("/list")
    public ResponseEntity<Page<Review>> listReview(@RequestParam int page,@CurrentMember MemberDTO memberDTO) throws Exception {

        Page<Review> reviewList = reviewService.listReview(memberDTO,page);
        return ResponseEntity.status(HttpStatus.OK).body(reviewList);
    }
    @PostMapping("/new") //reviewDTO add시 productDTO와 buyerDTO 넣어야함
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO reviewDTO,@CurrentMember MemberDTO memberDTO) throws Exception {

        reviewDTO = reviewService.addReview(reviewDTO);

        return ResponseEntity.status(HttpStatus.OK).body(reviewDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id,@CurrentMember MemberDTO memberDTO) throws Exception {

        ReviewDTO reviewDTO = reviewService.getReview(id);

        if (!Objects.equals(reviewDTO.getId().getMember().getId(), memberDTO.getId()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("삭제 할 권한이 없습니다.");
        reviewService.deleteReview(reviewDTO.toEntity().getId());
        return ResponseEntity.status(HttpStatus.OK).body("리뷰 삭제 완료");
    }
}
