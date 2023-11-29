package com.fiveguys.koguma.service.post;

import com.fiveguys.koguma.data.dto.CategoryDTO;
import com.fiveguys.koguma.data.dto.ClubPostCategoryDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PostDTO;
import com.fiveguys.koguma.data.entity.Category;
import com.fiveguys.koguma.data.entity.ClubPostCategory;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Post;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<PostDTO> listPost() {

        List<Post> allPost = postRepository.findAll();

        return allPost.stream().map(PostDTO::fromEntity).collect(Collectors.toList());
    }

    //추가될 리스트 항목 썸네일, 좋아요 수, 동네정보, 타임스템프, 댓글 수
    @Override
    public List<PostDTO> listPostByMember(MemberDTO memberDTO) {

        List<Post> Posts = postRepository.findAllByMember(memberDTO.toEntity());

        return Posts.stream()
                .map(PostDTO::fromEntity).collect(Collectors.toList());
    }


    @Override
    public Long addPost(PostDTO postDTO, MemberDTO memberDTO) {

        Post post = postDTO.toEntity();
        Member member = memberDTO.toEntity();

        Member saveWriterId = memberRepository.save(member);
        Post postId = postRepository.save(post);

        return post.getId();

    }

    //추가될 내용 회원 프사, 동네정보, 이미지, 장소공유, 좋아요 수 => 이것들은 front구현시 호출해서 사용
    @Override
    public PostDTO getPost(PostDTO postDTO) {
        Post post = postRepository.findById(postDTO.getId()).orElseThrow();

        return PostDTO.fromEntity(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, MemberDTO memberDTO) {

        //게시글 id에 해당하는 게시글 조회
        Optional<Post> optionalPost = postRepository.findById(postDTO.getId());

        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();

            //Security로 현재 로그안한 사용자 정보 얻기
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String currentUserId = authentication.getName();

            }
        return  null;
        }

    //완전히 삭제되니까 수정
    @Override
    public void deletePost(PostDTO postDTO, MemberDTO memberDTO) {
        postRepository.deleteById(postDTO.getId());
    }

    @Override
    public int increaseViews(PostDTO postDTO) {
        Optional<Post> optionalPost = postRepository.findById(postDTO.getId());

        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.increaseViews();
            postRepository.save(post);
        }else{
            throw new IllegalArgumentException("Cannot increase Views");
        }
        return 0;
    }

    @Override
    public List<PostDTO> listPostByViews(PostDTO postDTO) {

        List<Post> topPosts = postRepository.findTop10ByOrderByViewsDesc();
        return topPosts.stream().map(PostDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO getCategorySearch(PostDTO postDTO, CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public List<PostDTO> listCategoryPost(PostDTO postDTO, CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public List<PostDTO> listCategorySearch(PostDTO postDTO, CategoryDTO categoryDTO) {
        return null;
    }


    @Override
    public void searchKeyword(PostDTO postDTO) {

    }

    @Override
    public List<PostDTO> listKeywordSearch(PostDTO postDTO) {
        return null;
    }

}
