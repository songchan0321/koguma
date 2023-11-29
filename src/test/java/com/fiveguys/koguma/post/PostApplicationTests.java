package com.fiveguys.koguma.post;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PostDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Post;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.repository.post.PostRepository;
import com.fiveguys.koguma.service.post.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostApplicationTests {

    @Autowired
    PostService postService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @DisplayName("게시글 생성")
    @Test
    public void addPostTest() throws Exception{

        //given
        Post post = Post.builder()
                .title("title1")
                .content("content1")
                .postType(true)
                .latitude(37.494949)
                .longitude(14.1212)
                .dong("비전2동")
                .views(5)
                .activeFlag(true)
                .build();

        PostDTO postDTO = PostDTO.fromEntity(post);
        Member member = memberRepository.findById(2L).get();
        MemberDTO memberDTO = MemberDTO.fromEntity(member);

        //when
        Long postId = postService.addPost(postDTO, memberDTO);
        PostDTO findPost = postService.getPost(postId);

        //then
        assertThat(findPost.getId()).isEqualTo(postId);
        assertThat(findPost.getTitle()).isEqualTo(postDTO.getTitle());
        assertThat(findPost).isNotNull(); // 추가: findPost가 null이 아닌지 확인


    }
}
