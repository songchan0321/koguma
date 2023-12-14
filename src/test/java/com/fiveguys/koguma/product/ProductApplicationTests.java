package com.fiveguys.koguma.product;

import com.fiveguys.koguma.data.dto.*;
import com.fiveguys.koguma.data.entity.*;
import com.fiveguys.koguma.repository.common.QueryRepository;
import com.fiveguys.koguma.repository.product.ProductRepository;
import com.fiveguys.koguma.service.common.LocationService;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.product.MemberProductSuggestService;
import com.fiveguys.koguma.service.product.ProductService;
import com.fiveguys.koguma.service.product.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("local")
public class ProductApplicationTests {
    @Autowired
    private ProductService productService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private MemberProductSuggestService memberProductSuggestService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private QueryRepository queryRepository;

    @Test
    @DisplayName("상품 등록 테스트")
    @Transactional
    public void addProduct() throws Exception {


        Member seller = memberService.getMember(1L).toEntity();
        //Member buyer = memberService.getMember(5L).toEntity();
        Member buyer = memberService.getMember(2L).toEntity();

        LocationDTO locationDTO = locationService.getMemberRepLocation(1L);


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
    @Transactional
    public void raiseProduct() throws Exception {
        productService.raiseProduct(1L);
    }
    @Test
    @DisplayName("상품 리스트 조회 테스트")
    @Transactional
    public void listProduct() throws Exception {
        Page<Product> productPage = productService.listProduct(2L, 0, 10);

        productPage.getContent().stream()
                .forEach(product -> System.out.println(product.toString())
                );
    }
    @Test
    @DisplayName("상품 조회테스트")
    @Transactional
    public void getProduct() throws Exception{
        System.out.println((productService.getProduct(1L).toString()));
    }
    @Test
    @DisplayName("상품 거래상태 수정 테스트")
    @Transactional
    public void updateState() throws Exception{
        ProductDTO productDTO = productService.getProduct(1L);
        productService.updateProduct(productDTO);
    }
    @Test
    @DisplayName("상품 리뷰 추가 테스트")
    @Transactional
    public void 상품리뷰() throws Exception{

        Member seller = memberService.getMember(5L).toEntity();
        Product product = productService.getProduct(5L).toEntity();


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


    @Test
    @DisplayName("상품 리뷰 조회 테스트")
    @Transactional
    public void 상품리뷰조회() throws Exception{
        System.out.println(reviewService.getReview(1L));
    }

    @Test
    @DisplayName("상품 가격제안추가 테스트")
    @Transactional
    public void 상품가격제안추가() throws Exception{

        Member buyer = memberService.getMember(1L).toEntity();
        Product product = productService.getProduct(7L).toEntity();

        MemberProductSuggestDTO memberProductSuggestDTO =
                MemberProductSuggestDTO.builder()
                        .id(MemberProductSuggestId.builder()
                                .member(buyer)
                                .product(product)
                                .build())
                        .price(100000)
                        .build();
        memberProductSuggestService.addSuggetPrice(memberProductSuggestDTO);


    }
//    @Test
//    @DisplayName("상품 가격제안리스트 테스트")
//    @Transactional
//    public void 상품가격제안리스트조회() throws Exception{
//
//        List<MemberProductSuggestDTO> memberProductSuggestDtoList = memberProductSuggestService.listSuggestPrice(1L);
//        memberProductSuggestDtoList.stream().forEach(list -> System.out.println(list));
//
//    }

    @Test
    @DisplayName("상품 추가 더미데이터 ")
    @Transactional
    public Product generateRandomProduct() {          //더미데이터 생성용


        Member seller = memberService.getMember(4L).toEntity();
        //Member buyer = memberService.getMember(5L).toEntity();
        Member buyer = memberService.getMember(5L).toEntity();


        Random random = new Random();

        double minLatitude = 37.40;
        double maxLatitude = 37.49;
        double randomLatitude = round(minLatitude + (maxLatitude - minLatitude) * random.nextDouble(), 6);

        double minLongitude = 127.00;
        double maxLongitude = 127.09;
        double randomLongitude = round(minLongitude + (maxLongitude - minLongitude) * random.nextDouble(), 6);

        String dong = locationService.reverseGeoCoder(randomLatitude,randomLongitude);

        return Product.builder()
                .seller(seller)
                .buyer(buyer)
                .dong(dong)
                .latitude(randomLatitude)
                .longitude(randomLongitude)
                .categoryId(20L)
                .title("17곡 곡물 그대로")
                .content("맛있음")
                .tradeStatus(ProductStateType.SALE)
                .views(0)
                .price(3000)
                .categoryName("식품")
                .activeFlag(false)
                .build();
    }

    public double round(double value, int decimalPlaces) {
        String pattern = "#." + "0".repeat(decimalPlaces);
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return Double.parseDouble(decimalFormat.format(value));
    }
    @Test
    @Transactional
    public void 도전() throws Exception{


        int numberOfProducts = 300; // Change this value based on how many products you want to generate
        for (int i = 0; i < numberOfProducts; i++) {
            Product product = generateRandomProduct();
            productRepository.save(product);
            System.out.println("Product " + (i + 1) + ": " + product);
        }

    }
//    @Test
//    @Transactional
//    public void 위치기반상품리스트조회테스트() throws Exception{
//
//        LocationDTO locationDTO = locationService.getMemberRepLocation(4L);
//
//        Pageable pageable = PageRequest.of(1,10);
//        List<?> productList = queryRepository.findAllByDistance(CategoryType.PRODUCT,locationDTO,pageable,null);
//        List<ProductDTO> productDTOList = productList.stream()
//                .map(x -> ProductDTO.fromEntity((Product) x))
//                .collect(Collectors.toList());
//
//        productDTOList.forEach(System.out::println);
//    }
}
