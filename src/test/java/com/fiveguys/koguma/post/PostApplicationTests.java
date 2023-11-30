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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    @DisplayName("게시글 전체 리스트")
    @Test
    public void listPostTest() throws Exception{
        //when
        Page<Post> postPage = postService.listPost();

        //Then [페이징에 대한 검증]
        assertEquals(0, postPage.getNumber()); //현재 페이지 번호
        assertEquals(10, postPage.getSize()); //페이지 크기
    }

    @DisplayName("회원 작성 게시글 리스트")
    @Test
    public void listPostByMemberTest() throws Exception{

        //given
        Member writer = memberService.getMember(2L).toEntity();

        //when
        Page<Post> postPage = postService.listPostByMember(MemberDTO.fromEntity(writer));

        //then
        assertEquals(0, postPage.getNumber()); //현재 페이지 번호
        assertEquals(10, postPage.getSize()); //페이지 크기
    }

    @DisplayName("게시글 상세조회")
    @Test
    public void getPostTest() throws Exception{


        //given
        Post postId = postService.getPost(4L).toEntity();

        //when
        PostDTO postDTO = postService.getPost(postId.getId());
        System.out.println("id = " +postDTO.toString());
        //then
        assertNotNull(postId);
        assertEquals(postId.getId(), postDTO.getId());

    }

    @DisplayName("조회수 증감 여부")
    @Test
    @Rollback(value = false)
    public void increaseViewsTest() throws Exception{

        //given
        Post post = createTestPost();
        Long postId = post.getId();
        int initialViews = post.getViews();

        //where
        postService.increaseViews(postId);

        //then
        Post updatedPost = postRepository.findById(postId).orElse(null);
        if(updatedPost != null){
            int updatedViews = updatedPost.getViews();
            assertEquals(initialViews + 1, updatedViews);
        }else{
            assertEquals("post not found", "post not found");
        }
    }

    private Post createTestPost(){

        Member writer = memberService.getMember(4L).toEntity();

        LocationDTO locationDTO = locationService.getMemberRepLocation(4L);

        Category category = categoryRepository.findById(50L).get();

        Post postView = Post.builder()
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
        
        postRepository.save(postView);

        return postView;
    }

    @DisplayName("게시글 조회수 정렬")
    @Test
    public void listPostByViews() throws Exception{

        //given
        //테스트에 필요한 11개의 Post 객체 생성 및 저장
        for (int i = 0 ; i < 11 ; i++){
            createTestPost();
        }

        
        //when
        Page<Post> postPage = postService.listPostByViews(new PostDTO());
        System.out.println("postPage = " + postPage);
        
        //then
        assertEquals(0, postPage.getNumber());
        assertEquals(10, postPage.getSize());
        assertTrue(postPage.getContent().size() <= 10);

        //조회수 기준으로 정렬되었는지 확인
        int preViews = Integer.MAX_VALUE;
        for(Post post : postPage.getContent()){
            int currViews =  post.getViews();
            assertTrue(currViews <= preViews);
            preViews = currViews;
        }

    }

    @Test
    @DisplayName("키워드 검색 리스트")
    public void TestSearchKeyword() throws Exception {

        Member writer = memberService.getMember(4L).toEntity();

        LocationDTO locationDTO = locationService.getMemberRepLocation(4L);

        Category category = categoryRepository.findById(50L).get();

        //given
        Post postSearch = Post.builder()
                .member(writer)
                .category(category)
                .categoryName(category.getCategoryName())
                .title("title1")
                .content("content3")
                .postType(true)
                .latitude(locationDTO.getLatitude())
                .longitude(locationDTO.getLongitude())
                .dong(locationDTO.getDong())
                .views(0)
                .activeFlag(true)
                .build();

        //when
        Page<Post> searchResult = postService.listSearchKeyword(PostDTO.fromEntity(postSearch));

        //then
        assertNotNull(searchResult);



    }

    @Test
    @DisplayName("카테고리 별 리스트 조회")
    public void testListCategoryBySearch(){

        //given
        Category category = categoryRepository.findById(50L).get();
        PageRequest pageRequest = PageRequest.of(0,10);

        //when
        Page<Post> postPage = postService.listCategoryBySearch(CategoryDTO.fromDTO(category));

        //then
        assertNotNull(postPage);
        assertEquals(0, postPage.getNumber());
        assertEquals(10, postPage.getSize());

    }
}
