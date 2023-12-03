package com.fiveguys.koguma.common;

import com.fiveguys.koguma.data.dto.LikeFilterAssociationDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.service.common.CategoryService;
import com.fiveguys.koguma.service.common.LikeFilterAssociationService;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.post.PostService;
import com.fiveguys.koguma.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class LikeFilterAssociationTests {

    @Autowired
    private LikeFilterAssociationService likeFilterAssociationService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PostService postService;
    @Autowired
    private CategoryService categoryService;

    @Test
    @Transactional
    public void addLikeProuct(){

        MemberDTO memberDTO = memberService.getMember(2L);
        ProductDTO productDTO = productService.getProduct(6L);

        LikeFilterAssociationDTO likeFilterAssociationDTO = LikeFilterAssociationDTO.builder()
                .memberDTO(memberDTO)
                .productDTO(productDTO)
                .build();

        likeFilterAssociationService.addLikeProduct(likeFilterAssociationDTO);
    }
    @Test
    public void deleteLikeProduct(){

    }

}
