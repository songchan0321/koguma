package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.*;
import com.fiveguys.koguma.data.entity.*;
import com.fiveguys.koguma.service.common.AlertService;
import com.fiveguys.koguma.service.common.AuthService;
import com.fiveguys.koguma.service.common.LikeFilterAssociationService;
import com.fiveguys.koguma.service.common.LocationService;
import com.fiveguys.koguma.service.product.MemberProductSuggestService;
import com.fiveguys.koguma.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/product")
public class ProductRestController {

    private final ProductService productService;
    private final LocationService locationService;
    private final AuthService authService;
    private final LikeFilterAssociationService likeFilterAssociationService;
    private final MemberProductSuggestService memberProductSuggestService;
    private final AlertService alertService;

    @GetMapping("/member")
    public ResponseEntity<MemberDTO> Product() throws Exception {

        MemberDTO memberDTO = authService.getAuthMember();
        return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
    }
    @GetMapping("/list")
    public ResponseEntity<Page<Object>> listProduct(@RequestParam int page, @RequestParam String keyword) throws Exception {

        MemberDTO memberDTO = authService.getAuthMember();
        LocationDTO locationDTO = locationService.getMemberRepLocation(memberDTO.getId());

        Pageable pageable = PageRequest.of(page, 9);

        Page<Object> productList = locationService.locationFilter(CategoryType.PRODUCT, locationDTO, pageable, keyword);

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/get/{no}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long no) throws Exception {

        ProductDTO productDTO = productService.getProduct(no);

        MemberDTO memberDTO = authService.getAuthMember();
        if (!(productDTO.getSellerDTO().getId().equals(memberDTO.getId()))) {
            return ResponseEntity.status(HttpStatus.CHECKPOINT).body(productDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @PostMapping("/new")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {

        productService.addProduct(productDTO);

        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @GetMapping("/update/{no}")
    public ResponseEntity<ProductDTO> getUpdateProductInfo(@PathVariable Long no) throws Exception {
        ProductDTO productDTO = productService.getProduct(no);

        MemberDTO memberDTO = authService.getAuthMember();
        if (!(productDTO.getSellerDTO().getId().equals(memberDTO.getId()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @DeleteMapping("/delete/{no}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long no) throws Exception {
        ProductDTO productDTO = productService.getProduct(no);
        MemberDTO memberDTO = authService.getAuthMember();
        if (!(productDTO.getSellerDTO().getId().equals(memberDTO.getId()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        productService.deleteProduct(no);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {

        productDTO = productService.updateProduct(productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @GetMapping("/list/like")
    public ResponseEntity<Page<Product>> likeProductList(@RequestParam int page) throws Exception {

        MemberDTO memberDTO = authService.getAuthMember();
        return ResponseEntity.status(HttpStatus.OK)
                .body(likeFilterAssociationService.likeProductList(memberDTO.getId(),page));
    }

    @PostMapping("/like/{no}")
    public ResponseEntity<String> addLikeProduct(@PathVariable Long no) throws Exception {
        ProductDTO productDTO = productService.getProduct(no);
        MemberDTO memberDTO = authService.getAuthMember();
        LikeFilterAssociationDTO likeFilterAssociationDTO = LikeFilterAssociationDTO.builder().
                productDTO(productDTO).memberDTO(memberDTO).build();

        likeFilterAssociationService.addLikeProduct(likeFilterAssociationDTO);
        return ResponseEntity.status(HttpStatus.OK).body(productDTO.getTitle() + "좋아요 등록 완료");
    }

    @DeleteMapping("/like/{associationId}")
    public ResponseEntity<String> deleteLikeProduct(@PathVariable Long associationId) {
        likeFilterAssociationService.deleteLikeProduct(associationId);

        return ResponseEntity.status(HttpStatus.OK).body("좋아요 삭제 완료");
    }

    @PutMapping("/tradestate")
    public ResponseEntity<String> updateStateProduct(@RequestBody Map<String,String> json) throws Exception {
        ProductDTO productDTO = productService.getProduct(Long.parseLong(json.get("productNo")));
        MemberDTO memberDTO = authService.getAuthMember();
        if (!(productDTO.getSellerDTO().getId().equals(memberDTO.getId()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        productService.updateState(productDTO, ProductStateType.valueOf(json.get("state")));
        return ResponseEntity.status(HttpStatus.OK).body("상태 업데이트 성공");
    }
    @GetMapping("/tradestate/list")
    public ResponseEntity<Page<Product>> listStateProduct(@RequestParam int page,@RequestParam String type) throws Exception {
        MemberDTO memberDTO = authService.getAuthMember();
        Pageable pageable = PageRequest.of(page, 9);
        Page<Product> productList = productService.listStateProduct(memberDTO.getId(),pageable, ProductStateType.valueOf(type));

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
    @GetMapping("/buy/list")
    public ResponseEntity<Page<Product>> listBuyProduct(@RequestParam int page) throws Exception {
        MemberDTO memberDTO = authService.getAuthMember();
        Pageable pageable = PageRequest.of(page, 9);
        Page<Product> productList = productService.listBuyProduct(memberDTO.getId(),pageable);

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @PostMapping("/suggest")
    public ResponseEntity<String> addSugestProduct(@RequestBody ProductDTO productDTO) throws Exception {
        MemberDTO memberDTO = authService.getAuthMember();
        MemberProductSuggestDTO memberProductSuggestDTO =
                MemberProductSuggestDTO.builder().id(MemberProductSuggestId.builder()
                        .product(productDTO.toEntity())
                        .member(memberDTO.toEntity()).build())
                .price(productDTO.getPrice())
                .build();

        memberProductSuggestService.addSuggetPrice(memberProductSuggestDTO);
//        alertService.addAlert(productDTO.getSellerDTO(),productDTO.getTitle(),memberDTO.getNickname()+"님이 가격제안을 했습니다"),
        return ResponseEntity.status(HttpStatus.OK).body("가격제안 성공");
    }
    @GetMapping("/suggest/list")
    public ResponseEntity<Page<MemberProductSuggest>> listSuggestProduct(@RequestParam int page) throws Exception {
        MemberDTO memberDTO = authService.getAuthMember();
        Page<MemberProductSuggest> list = memberProductSuggestService.listSuggestPrice(memberDTO.getId(),page);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @PostMapping("/raise/{productNo}")
    public ResponseEntity<String> raiseProduct(@PathVariable Long productNo) throws Exception {
        try {
            MemberDTO memberDTO = authService.getAuthMember();
            ProductDTO productDTO = productService.getProduct(productNo);
            if (!(productDTO.getSellerDTO().getId().equals(memberDTO.getId()))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            productService.raiseProduct(productNo);

            return ResponseEntity.status(HttpStatus.OK).body("상품 끌어올리기 완료");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("끌어올리기 실패: " + e.getMessage());
        }
    }
    @GetMapping("/buyer/list/{productNo}")
    public ResponseEntity<Map<String,Object>> selectBuyer(@PathVariable Long productNo,@RequestParam int page) throws Exception {
        MemberDTO memberDTO = authService.getAuthMember();
        ProductDTO productDTO = productService.getProduct(productNo);
        if (!(productDTO.getSellerDTO().getId().equals(memberDTO.getId()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Page<MemberProductSuggest> list = memberProductSuggestService.listSuggestPrice(memberDTO.getId(),page);
        Map<String,Object> map = new HashMap<>();
        map.put("productDTO",productDTO);
        map.put("suggestList",list);

        return ResponseEntity.status(HttpStatus.OK).body(map);

    }

}


