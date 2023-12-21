package com.fiveguys.koguma.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveguys.koguma.data.dto.*;
import com.fiveguys.koguma.data.entity.*;
import com.fiveguys.koguma.service.common.*;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.product.MemberProductSuggestService;
import com.fiveguys.koguma.service.product.ProductService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/product")
public class ProductRestController {

    private final MemberService memberService;
    private final ProductService productService;
    private final LocationService locationService;
    private final AuthService authService;
    private final LikeFilterAssociationService likeFilterAssociationService;
    private final MemberProductSuggestService memberProductSuggestService;
    private final AlertService alertService;
    private final ImageService imageService;

    @GetMapping("/member")
    public ResponseEntity<MemberDTO> Product() throws Exception {

        MemberDTO memberDTO = authService.getAuthMember();
        return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
    }
    @GetMapping("/list")
    public ResponseEntity<List<?>> listProduct(@RequestParam String keyword, @CurrentMember MemberDTO memberDTO) throws Exception {


        LocationDTO locationDTO = locationService.getMemberRepLocation(memberDTO.getId());

//        Pageable pageable = PageRequest.of(0, 9);
//
//        List<?> productList = locationService.locationFilter(CategoryType.PRODUCT, locationDTO, pageable, keyword);
        List<ProductDTO> productDTOList = productService.listProductByLocation(locationDTO,keyword);

        return ResponseEntity.status(HttpStatus.OK).body(productDTOList);
    }


    @GetMapping("/get/{no}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long no,@CurrentMember MemberDTO memberDTO) throws Exception {

        ProductDTO productDTO = productService.getProduct(no);



        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }
    @GetMapping("/valid/{no}")
    public ResponseEntity<Boolean> validProduct(@PathVariable Long no,@CurrentMember MemberDTO memberDTO) throws Exception {

        ProductDTO productDTO = productService.getProduct(no);

        if (!(productDTO.getSellerDTO().getId().equals(memberDTO.getId()))) {
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }

        return ResponseEntity.status(HttpStatus.OK).body(true);

    }


    @PostMapping("/new")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO,@CurrentMember MemberDTO memberDTO) {
        ObjectMapper objectMapper = new ObjectMapper();

        List<String> urls = productDTO.getImages();
        productDTO.setSellerDTO(memberDTO);
        LocationDTO locationDTO = locationService.getMemberRepLocation(memberDTO.getId());

        productDTO.setDong(locationDTO.getDong());
        productDTO.setLatitude(locationDTO.getLatitude());
        productDTO.setLongitude(locationDTO.getLongitude());
        productDTO.setActiveFlag(true);
        productDTO.setTradeStatus(ProductStateType.SALE);
        productDTO = productService.addProduct(productDTO);
        List<ImageDTO> imageDTOList = imageService.createImageDTOList(productDTO,urls,ImageType.PRODUCT);
        imageDTOList.forEach(System.out::println);
        imageService.addImage(imageDTOList);
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }
    @GetMapping("/update/{no}")
    public ResponseEntity<ProductDTO> getUpdateProductInfo(@PathVariable Long no,@CurrentMember MemberDTO memberDTO) throws Exception {
        ProductDTO productDTO = productService.getProduct(no);

        if (!(productDTO.getSellerDTO().getId().equals(memberDTO.getId()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId,@CurrentMember MemberDTO memberDTO) throws Exception {
        ProductDTO productDTO = productService.getProduct(productId);

        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) throws JsonProcessingException {

        int newPrice = productDTO.getPrice();
        int oldPrice = productService.getProduct(productDTO.getId()).getPrice();


        productDTO = productService.updateProduct(productDTO);
        if (newPrice < oldPrice){
            List<MemberDTO> memberList = likeFilterAssociationService.findLikeProductByMember(productDTO.getId());
            for (MemberDTO dto : memberList) {
                alertService.addAlert(dto,"상품",productDTO.getTitle()+"상품의 가격이 인하되었습니다.","/product/get/"+productDTO.getId());
            }
        }


        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @GetMapping("/list/like")
    public ResponseEntity<List<LikeFilterAssociationDTO>> likeProductList(@CurrentMember MemberDTO memberDTO) throws Exception {


        return ResponseEntity.status(HttpStatus.OK)
                .body(likeFilterAssociationService.likeProductList(memberDTO.getId()));
    }

    @GetMapping("/get/like/{productId}")
    public ResponseEntity<LikeFilterAssociationDTO> getLikeProduct(@PathVariable Long productId,@CurrentMember MemberDTO memberDTO){
        return  ResponseEntity.status(HttpStatus.OK).body(likeFilterAssociationService.getLikeProduct(productId,memberDTO.getId()));
    }

    @PostMapping("/like/{productId}")
    public ResponseEntity<String> addLikeProduct(@PathVariable Long productId,@CurrentMember MemberDTO memberDTO) throws Exception {
        ProductDTO productDTO = productService.getProduct(productId);
        LikeFilterAssociationDTO likeFilterAssociationDTO = likeFilterAssociationService.getLikeProduct(productId, memberDTO.getId());

        // 이미 좋아요가 등록되어 있는 경우
        if (likeFilterAssociationDTO != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 좋아요가 등록되어 있습니다.");
        }

        LikeFilterAssociationDTO newLikeFilterAssociationDTO = LikeFilterAssociationDTO.builder()
                .productDTO(productDTO)
                .memberDTO(memberDTO)
                .build();

        likeFilterAssociationService.addLikeProduct(newLikeFilterAssociationDTO);
        memberService.setScore(0.3F,productDTO.getSellerDTO());
        return ResponseEntity.status(HttpStatus.OK).body(productDTO.getTitle() + " 좋아요 등록 완료");
    }

    @DeleteMapping("/like/{productId}")
    public ResponseEntity<String> deleteLikeProduct(@PathVariable Long productId,@CurrentMember MemberDTO memberDTO) {
        LikeFilterAssociationDTO likeFilterAssociationDTO = likeFilterAssociationService.getLikeProduct(productId,memberDTO.getId());
        System.out.println("likeFilterAssociationDTO = " + likeFilterAssociationDTO);

        likeFilterAssociationService.deleteLikeProduct(likeFilterAssociationDTO.getId());
        memberService.setScore(0.3F,likeFilterAssociationDTO.getProductDTO().getSellerDTO());
        return ResponseEntity.status(HttpStatus.OK).body("좋아요 삭제 완료");
    }

    @PutMapping("/tradestate")
    public ResponseEntity<String> updateStateProduct(@RequestParam String productId,@RequestParam(required = false) String buyerId, @RequestParam String type,@CurrentMember MemberDTO memberDTO) throws Exception {
        ProductDTO productDTO = productService.getProduct(Long.parseLong(productId));
        System.out.println("productDTO = " + productDTO);
        if (buyerId != null) {
            productDTO.setBuyerDTO(memberService.getMember(Long.valueOf(buyerId)));
            productDTO.setBuyDate(LocalDateTime.now());
        }
        if (type.equals(String.valueOf(ProductStateType.SALE))){
            productDTO.setBuyerDTO(null);
            productDTO.setBuyDate(null);
        }

        productService.updateState(productDTO, ProductStateType.valueOf(type.toUpperCase()));
        return ResponseEntity.status(HttpStatus.OK).body("상태 업데이트 성공");
    }
    @GetMapping("/sale/list")
    public ResponseEntity<List<ProductDTO>> listStateProduct(@RequestParam String type,@CurrentMember MemberDTO memberDTO) throws Exception {

        List<ProductDTO> productList = productService.listStateProduct(memberDTO.getId(),ProductStateType.valueOf(type.toUpperCase()));

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
    @GetMapping("/buy/list")
    public ResponseEntity<List<ProductDTO>> listBuyProduct(@CurrentMember MemberDTO memberDTO) throws Exception {

        List<ProductDTO> productList = productService.listBuyProduct(memberDTO.getId());

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @PostMapping("/suggest")
    public ResponseEntity<String> addSugestProduct(@RequestBody Map<String,String> json,@CurrentMember MemberDTO memberDTO) throws Exception {
        System.out.println(json.get("productId"));
        System.out.println(json.get("price"));
        ProductDTO productDTO = productService.getProduct(Long.valueOf(json.get("productId")));
        MemberProductSuggestDTO memberProductSuggestDTO =
                MemberProductSuggestDTO.builder()
                        .productDTO(productDTO)
                        .memberDTO(memberDTO)
                .price(Integer.parseInt(json.get("price")))
                .build();

        memberProductSuggestService.addSuggetPrice(memberProductSuggestDTO);
        alertService.addAlert(productDTO.getSellerDTO(),"가격제안",memberDTO.getNickname()+"님이 가격제안을 했습니다.","/product/suggest/list/"+productDTO.getId());

        return ResponseEntity.status(HttpStatus.OK).body("가격제안 성공");
    }
    @GetMapping("/suggest/list/{productId}")
    public ResponseEntity<List<MemberProductSuggestDTO>> listSuggestProduct(@PathVariable Long productId, @CurrentMember MemberDTO memberDTO) {


        return ResponseEntity.status(HttpStatus.OK).body(memberProductSuggestService.listSuggestPrice(productId));
    }
    @GetMapping("/suggest/count/{productId}")
    public ResponseEntity<Integer> listSuggestCount(@PathVariable Long productId, @CurrentMember MemberDTO memberDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(memberProductSuggestService.getSuggestCount(productId));
    }
    @PostMapping("/raise/{productNo}")
    public ResponseEntity<String> raiseProduct(@PathVariable Long productNo,@CurrentMember MemberDTO memberDTO) throws Exception {
        try {

            ProductDTO productDTO = productService.getProduct(productNo);
            productService.raiseProduct(productNo);

            return ResponseEntity.status(HttpStatus.OK).body("상품 끌어올리기 완료");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("끌어올리기 실패: " + e.getMessage());
        }
    }
    @GetMapping("/buyer/list/{productNo}")
    public ResponseEntity<List<MemberProductSuggestDTO>> selectBuyer(@PathVariable Long productNo,@CurrentMember MemberDTO memberDTO) throws Exception {

        List<MemberProductSuggestDTO> list = memberProductSuggestService.listSuggestPrice(productNo);

        return ResponseEntity.status(HttpStatus.OK).body(list);

    }


}


