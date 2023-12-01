package com.fiveguys.koguma.product;

import com.fiveguys.koguma.data.dto.*;
import com.fiveguys.koguma.data.entity.*;
import com.fiveguys.koguma.repository.common.LocationRepository;
import com.fiveguys.koguma.service.common.LocationService;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.product.ProductService;
import com.fiveguys.koguma.service.product.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ProductApplicationTests {
    @Autowired
    private ProductService productService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private ReviewService reviewService;

    @Test
    @DisplayName("상품 등록 테스트")
    public void addProduct() throws Exception {


        Member seller = memberService.getMember(4L).toEntity();
        //Member buyer = memberService.getMember(5L).toEntity();
        Member buyer = memberService.getMember(5L).toEntity();

        LocationDTO locationDTO = locationService.getMemberRepLocation(4L);


        Product product = Product.builder()
                .seller(seller)
                .buyer(buyer)
                .dong(locationDTO.getDong())
                .latitude(locationDTO.getLatitude())
                .longitude(locationDTO.getLongitude())
                .categoryId(20L)
                .title("칙촉")
                .content("맛있음")
                .tradeStatus(ProductStateType.SALE)
                .views(0)
                .price(1000)
                .categoryName("식품")
                .activeFlag(false)
                .build();
        productService.addProduct(ProductDTO.fromEntity(product));
    }

    @Test
    @DisplayName("상품 끌어올리기 테스트")
    public void raiseProduct() throws Exception {
        productService.raiseProduct(1L);
    }
    @Test
    @DisplayName("상품 리스트 조회 테스트")
    public void listProduct() throws Exception {
        Page<Product> productPage = productService.listProduct(2L, 0, 10);

        productPage.getContent().stream()
                .forEach(product -> System.out.println(product.toString())
                );
    }
    @Test
    @DisplayName("상품 조회테스트")
    public void getProduct() throws Exception{
        System.out.println((productService.getProduct(1L).toString()));
    }
    @Test
    @DisplayName("상품 거래상태 수정 테스트")
    public void updateState() throws Exception{
        ProductDTO productDTO = productService.getProduct(1L);
        productService.updateProduct(productDTO);
    }
    @Test
    @DisplayName("상품 리뷰 추가 테스트")
    public void 상품리뷰() throws Exception{

        Member seller = memberService.getMember(5L).toEntity();
        Product product = productService.getProduct(1L).toEntity();

         ReviewDTO reviewDTO = ReviewDTO.builder()
                 .id(ReviewId.builder()
                         .product(product)
                         .member(seller)
                         .build())
                .content("김태현 돼지")
                .commet("ㅇㅈ")
                .rating('5')
                .activeFlag(true)
                .build();
        reviewService.addReview(reviewDTO);


    }

}
