package com.fiveguys.koguma.service.post;

import com.fiveguys.koguma.data.dto.*;
import com.fiveguys.koguma.data.entity.Category;
import com.fiveguys.koguma.data.entity.CategoryType;
import com.fiveguys.koguma.data.entity.Post;
import com.fiveguys.koguma.repository.common.CategoryRepository;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.repository.post.PostRepository;
import com.fiveguys.koguma.service.common.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private  final CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @Override
    public Page<Post> listPost() {

        PageRequest pageRequest = PageRequest.of(0, 10);

        return  postRepository.findAll(pageRequest);
    }


    @Override
    public Page<Post> listPostByMember(MemberDTO memberDTO) {

        PageRequest pageRequest = PageRequest.of(0, 10);

        return  postRepository.findAllByMember(memberDTO.toEntity(), pageRequest);

    }


    @Override
    public void addPost(PostDTO postDTO) {
        postRepository.save(postDTO.toEntity());

    }

    //추가될 내용 회원 프사, 동네정보, 이미지, 장소공유, 좋아요 수 => 이것들은 front구현시 호출해서 사용
    @Override
    public PostDTO getPost(Long id) {

        Post post = postRepository.findById(id).orElseThrow(()->new NoResultException("해당 게시글 정보가 존재하지 않습니다."));

        return PostDTO.fromEntity(post);
    }

    @Override
    public void updatePost(PostDTO postDTO) {

        Long postId = postDTO.getId();

        //기존 게시글 조회
        Post existingPost = postRepository.findById(postId)
                        .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        existingPost.setTitle(postDTO.getTitle());
        existingPost.setCategory(postDTO.toEntity().getCategory());
        existingPost.setContent(postDTO.getContent());

        postRepository.save(postDTO.toEntity());
        }

    @Override
    public void deletePost(PostDTO postDTO) {

        Long postId = postDTO.getId();

        Post existingPost = postRepository.findById(postId)
                        .orElseThrow(()-> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        //게시글 비활성화 상태 변경
        existingPost.setActiveFlag(false);

        postRepository.save(existingPost);
    }

    @Override
    public void increaseViews(Long id) {

        Post post = postRepository.findById(id).orElseThrow(
                ()->new NoResultException("해당 게시글의 정보가 존재하지 않습니다.")
        );
        post.increaseViews(post.getViews());
    }

    @Override
    public Page<Post> listPostByViews(PostDTO postDTO) {

        PageRequest pageRequest = PageRequest.of(0, 10);

        return  postRepository.findTop10ByOrderByViewsDesc(pageRequest);
    }

    //검색을 위한 카테고리 리스트 정렬
    @Override
    public List<CategoryDTO>  listCategoryForSelect() {

        return categoryService.listCategory(CategoryType.POST);
    }
    //카테고리 별 검색 결과
    @Override
    public Page<Post> listCategoryBySearch(CategoryDTO categoryDTO) {

        PageRequest pageRequest = PageRequest.of(0, 10);

        return  postRepository.findAllByCategory(categoryDTO.toEntity(), pageRequest);

    }



    @Override
    public Page<Post> listSearchKeyword(PostDTO postDTO) {

        PageRequest pageRequest = PageRequest.of(0, 10);

        return postRepository.findByTitleContainingOrContentContaining(postDTO.getTitle(),postDTO.getContent(), pageRequest);
    }


}
