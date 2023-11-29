package com.fiveguys.koguma.product;

import com.fiveguys.koguma.data.dto.CategoryDTO;
import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.data.entity.CategoryType;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Product;
import com.fiveguys.koguma.data.entity.ProductStateType;
import com.fiveguys.koguma.repository.common.LocationRepository;
import com.fiveguys.koguma.service.common.LocationService;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ProductApplicationTests {
    @Autowired
    private ProductService productService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private LocationService locationService;

    @Test
    @DisplayName("상품 등록 테스트")
    public void addProduct() throws Exception {

        Member seller = memberService.getMember(4L).toEntity();
        Member buyer = memberService.getMember(5L).toEntity();
//        Member buyer = memberService.getMember(5L).toEntity();
        LocationDTO locationDTO = locationService.getMemberRepLocation(2L);


        Product product = Product.builder()
                .seller(seller)
                .buyer(null)
                .dong(locationDTO.getDong())
                .latitude(locationDTO.getLatitude())
                .longitude(locationDTO.getLongitude())
                .categoryId(20L)
                .title("17곡 곡물 그대로")
                .content("맛있음")
                .tradeStatus(ProductStateType.SALE)
                .views(0)
                .price(3000)
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
}
