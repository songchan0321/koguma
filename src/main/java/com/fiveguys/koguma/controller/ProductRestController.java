package com.fiveguys.koguma.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveguys.koguma.data.dto.*;
import com.fiveguys.koguma.data.entity.*;
import com.fiveguys.koguma.service.common.*;
import com.fiveguys.koguma.service.product.MemberProductSuggestService;
import com.fiveguys.koguma.service.product.ProductService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        Pageable pageable = PageRequest.of(0, 9);

        List<?> productList = locationService.locationFilter(CategoryType.PRODUCT, locationDTO, pageable, keyword);

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
    @GetMapping("/list/hi")
    public ResponseEntity<Page<Product>> listHIProduct(@RequestParam int page, @RequestParam String keyword,@CurrentMember MemberDTO memberDTO) throws Exception {


//        LocationDTO locationDTO = locationService.getMemberRepLocation(memberDTO.getId());

        Pageable pageable = PageRequest.of(page, 9);

        return ResponseEntity.status(HttpStatus.OK).body(productService.listProduct(memberDTO.getId(),page,10));
    }

    @GetMapping("/get/{no}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long no,@CurrentMember MemberDTO memberDTO) throws Exception {

        ProductDTO productDTO = productService.getProduct(no);


//        if (!(productDTO.getSellerDTO().getId().equals(memberDTO.getId()))) {
//            return ResponseEntity.status(HttpStatus.CHECKPOINT).body(productDTO);
//        }

        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

//    @PostMapping("/new")
//    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO,@CurrentMember MemberDTO memberDTO) {
//        productDTO.setSellerDTO(memberDTO);
//        LocationDTO locationDTO = locationService.getMemberRepLocation(memberDTO.getId());
//
//        productDTO.setDong(locationDTO.getDong());
//        productDTO.setLatitude(locationDTO.getLatitude());
//        productDTO.setLongitude(locationDTO.getLongitude());
//        productDTO = productService.addProduct(productDTO);
//
//        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
//    }
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

    @DeleteMapping("/delete/{no}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long no,@CurrentMember MemberDTO memberDTO) throws Exception {
        ProductDTO productDTO = productService.getProduct(no);

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
    public ResponseEntity<Page<Product>> likeProductList(@RequestParam int page,@CurrentMember MemberDTO memberDTO) throws Exception {


        return ResponseEntity.status(HttpStatus.OK)
                .body(likeFilterAssociationService.likeProductList(memberDTO.getId(),page));
    }

    @PostMapping("/like/{no}")
    public ResponseEntity<String> addLikeProduct(@PathVariable Long no,@CurrentMember MemberDTO memberDTO) throws Exception {
        ProductDTO productDTO = productService.getProduct(no);

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
    public ResponseEntity<String> updateStateProduct(@RequestBody Map<String,String> json,@CurrentMember MemberDTO memberDTO) throws Exception {
        ProductDTO productDTO = productService.getProduct(Long.parseLong(json.get("productNo")));

        if (!(productDTO.getSellerDTO().getId().equals(memberDTO.getId()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        productService.updateState(productDTO, ProductStateType.valueOf(json.get("state")));
        return ResponseEntity.status(HttpStatus.OK).body("상태 업데이트 성공");
    }
    @GetMapping("/tradestate/list")
    public ResponseEntity<Page<Product>> listStateProduct(@RequestParam int page,@RequestParam String type,@CurrentMember MemberDTO memberDTO) throws Exception {

        Pageable pageable = PageRequest.of(page, 9);
        Page<Product> productList = productService.listStateProduct(memberDTO.getId(),pageable, ProductStateType.valueOf(type));

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
    @GetMapping("/buy/list")
    public ResponseEntity<Page<Product>> listBuyProduct(@RequestParam int page,@CurrentMember MemberDTO memberDTO) throws Exception {

        Pageable pageable = PageRequest.of(page, 9);
        Page<Product> productList = productService.listBuyProduct(memberDTO.getId(),pageable);

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @PostMapping("/suggest")
    public ResponseEntity<String> addSugestProduct(@RequestBody ProductDTO productDTO,@CurrentMember MemberDTO memberDTO) throws Exception {

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
    public ResponseEntity<Page<MemberProductSuggest>> listSuggestProduct(@RequestParam int page,@CurrentMember MemberDTO memberDTO) throws Exception {

        Page<MemberProductSuggest> list = memberProductSuggestService.listSuggestPrice(memberDTO.getId(),page);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @PostMapping("/raise/{productNo}")
    public ResponseEntity<String> raiseProduct(@PathVariable Long productNo,@CurrentMember MemberDTO memberDTO) throws Exception {
        try {

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
    public ResponseEntity<Map<String,Object>> selectBuyer(@PathVariable Long productNo,@RequestParam int page,@CurrentMember MemberDTO memberDTO) throws Exception {

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


