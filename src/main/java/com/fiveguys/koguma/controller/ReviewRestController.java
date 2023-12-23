package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.ChatroomDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.data.dto.ReviewDTO;
import com.fiveguys.koguma.data.entity.Product;
import com.fiveguys.koguma.data.entity.Review;
import com.fiveguys.koguma.service.common.AlertService;
import com.fiveguys.koguma.service.common.AuthService;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.product.ProductService;
import com.fiveguys.koguma.service.product.ReviewService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/review")
public class ReviewRestController {

    private final ProductService productService;
    private final ReviewService reviewService;
    private final MemberService memberService;
    private final AlertService alertService;
    @GetMapping("/get/{reviewId}")
    public ResponseEntity<Map<String,Object>> getReview(@PathVariable Long reviewId,@CurrentMember MemberDTO memberDTO) {
        ReviewDTO reviewDTO = reviewService.getReview(reviewId);
        String role = reviewService.checkMemberRole(reviewDTO,memberDTO);
        Map<String,Object> map = new HashMap<>();       // getReview 했을때 누가 판매자인지 구매자인지 몰라서 role 추가함
        map.put("role",role);
        map.put("reviewDTO",reviewDTO);
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
    @PostMapping("/get/opp/reviewid")
    public ResponseEntity<Long> getOppReviewId(@RequestBody ProductDTO productDTO, @CurrentMember MemberDTO sourceDTO)  {
        Map<String,Object> target = getTargetDTO(productDTO,sourceDTO);

         Long targetToSourceReviewId = reviewService.getOppReviewId((MemberDTO)target.get("targetDTO"),productDTO,(String)target.get("targetType"));
        return ResponseEntity.status(HttpStatus.OK).body(targetToSourceReviewId);
    }
    @PostMapping("/get/my/reviewid")
    public ResponseEntity<Long> getMyReviewId(@RequestBody ProductDTO productDTO, @CurrentMember MemberDTO sourceDTO) {
        Map<String,Object> source = getSourceDTO(productDTO,sourceDTO);

        Long SourceTotargetReviewId = reviewService.getMyReviewId((MemberDTO)source.get("sourceDTO"),productDTO,(String)source.get("sourceType"));
        return ResponseEntity.status(HttpStatus.OK).body(SourceTotargetReviewId);
    }
    @GetMapping("/get/my/reviewid/{productId}")
    public ResponseEntity<Long> getMyReviewId(@PathVariable Long productId, @CurrentMember MemberDTO sourceDTO) {
        ProductDTO productDTO = productService.getProduct(productId);
        Map<String,Object> source = getSourceDTO(productDTO,sourceDTO);

        Long SourceTotargetReviewId = reviewService.getMyReviewId((MemberDTO)source.get("sourceDTO"),productDTO,(String)source.get("sourceType"));
        return ResponseEntity.status(HttpStatus.OK).body(SourceTotargetReviewId);
    }
    @PostMapping("/new") //reviewDTO add시 productDTO와 buyerDTO 넣어야함
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO reviewDTO,@CurrentMember MemberDTO memberDTO) throws Exception {


        Map<String,Object> myRole = getSourceDTO(reviewDTO.getProductDTO(),memberDTO);  // 현재 접속한 사용자가 판매자인지 구매자인지
        if (myRole.get("sourceType").equals("seller"))
            reviewDTO.setSellerFlag(true);
        else
            reviewDTO.setSellerFlag(false);
        System.out.println("---------------");
        System.out.println("reviewDTO : "+reviewDTO);
        if (reviewService.isPossibleAdd((String) myRole.get("sourceType"),reviewDTO.getProductDTO())){ // 리뷰가 존재하면 리뷰 못쓰게 해야함
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        Map<String,Object> target = getTargetDTO(reviewDTO.getProductDTO(),memberDTO);
        MemberDTO targetDTO = (MemberDTO) target.get("targetDTO"); // 리뷰를 보내기위해 타겟 dto 가져옴
        System.out.println("--!!!!!!!!!!!!!!!--------------");
        System.out.println("targetDTO = " + targetDTO);
        reviewDTO = reviewService.addReview(reviewDTO);

        alertService.addAlert(targetDTO,"후기",targetDTO.getNickname()+"님이 리뷰를 작성했어요.","/product/get/review/"+reviewDTO.getId());
        float score = reviewService.calculateScore(reviewDTO);
//        MemberDTO target = (MemberDTO) getTargetDTO(reviewDTO.getProductDTO(),memberDTO).get("targetDTO");
        memberService.setScore(score,targetDTO);
        return ResponseEntity.status(HttpStatus.OK).body(reviewDTO);
    }

    private boolean isBuyerByReview(ProductDTO productDTO, MemberDTO memberDTO) {
        return productDTO.getBuyerDTO().getId().equals(memberDTO.getId());
    }
    private MemberDTO checkSourceRole(ProductDTO productDTO,MemberDTO memberDTO){
        if (productDTO.getBuyerDTO().getId().equals(memberDTO.getId()))
            return productDTO.getBuyerDTO();
        else
            return productDTO.getSellerDTO();
    }
    private Map<String,Object> getTargetDTO(ProductDTO productDTO,MemberDTO source){ //내 정보와 product의 dto를 비교해서
        if (productDTO.getBuyerDTO().getId().equals(source.getId())) {         //내가 buyer라면 상대는 seller 내가 seller라면 상대는 buyer
            Map<String,Object> target = new HashMap<>();
            target.put("targetDTO",productDTO.getSellerDTO());
            target.put("targetType","seller");
            System.out.println("target = seller" + productDTO.getSellerDTO());
            return target;
        }
        else{
            Map<String,Object> target = new HashMap<>();
            target.put("targetDTO",productDTO.getBuyerDTO());
            target.put("targetType","buyer");
            System.out.println("target = buyer" + productDTO.getBuyerDTO());
            return target;
        }
    }
    private Map<String,Object> getSourceDTO(ProductDTO productDTO,MemberDTO source){ //내 역할
        if (productDTO.getBuyerDTO().getId().equals(source.getId())) {
            Map<String,Object> target = new HashMap<>();
            target.put("sourceDTO",productDTO.getBuyerDTO());
            target.put("sourceType","buyer");
            System.out.println("source = buyer" + productDTO.getBuyerDTO());
            return target;
        }
        else{
            Map<String,Object> target = new HashMap<>();
            target.put("sourceDTO",productDTO.getSellerDTO());
            target.put("sourceType","seller");
            System.out.println("source = seller" + productDTO.getSellerDTO());
            return target;
        }
    }
}
