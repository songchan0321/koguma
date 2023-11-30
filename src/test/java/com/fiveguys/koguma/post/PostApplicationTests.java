package com.fiveguys.koguma.post;

import com.fiveguys.koguma.data.dto.CategoryDTO;
import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PostDTO;
import com.fiveguys.koguma.data.entity.Category;
import com.fiveguys.koguma.data.entity.ClubPostCategory;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Post;
import com.fiveguys.koguma.repository.common.CategoryRepository;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.repository.post.PostRepository;
import com.fiveguys.koguma.service.common.CategoryService;
import com.fiveguys.koguma.service.common.LocationService;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.post.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class PostApplicationTests {

    @Autowired
    PostService postService;

    @Autowired
    MemberService memberService;

    @Autowired
    LocationService locationService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @DisplayName("게시글 생성")
    @Test
    public void addPostTest() throws Exception{

        Member writer = memberService.getMember(4L).toEntity();

        LocationDTO locationDTO = locationService.getMemberRepLocation(4L);

        Category category = categoryRepository.findById(50L).get();



        Post post = Post.builder()
                .member(writer)
                .category(category)
                .categoryName(category.getCategoryName())
                .title("몬스터")
                .content("우마이")
                .postType(true)
                .latitude(locationDTO.getLatitude())
                .longitude(locationDTO.getLongitude())
                .dong(locationDTO.getDong())
                .views(0)
                .activeFlag(true)
                .build();

        postRepository.save(post);
    }
}
