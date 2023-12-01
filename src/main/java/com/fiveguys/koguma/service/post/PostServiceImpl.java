package com.fiveguys.koguma.service.post;

import com.fiveguys.koguma.data.dto.*;
import com.fiveguys.koguma.data.entity.Category;
import com.fiveguys.koguma.data.entity.Post;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

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

        postRepository.save(postDTO.toEntity());
        }

    @Override
    public void deletePost(PostDTO postDTO, MemberDTO memberDTO) {
        postRepository.deleteById(postDTO.getId());
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

        return  postRepository.findTop10ByOrderByViewsDesc(postDTO.toEntity(), pageRequest);
    }

    //검색을 위한 카테고리 리스트 정렬
    @Override
    public List<CategoryDTO>  listCategoryForSearch(PostDTO postDTO, CategoryDTO categoryDTO) {

        return null;

    }
    //카테고리 별 검색 결과
    @Override
    public Page<Post> listCategoryBySearch(CategoryDTO categoryDTO) {

        PageRequest pageRequest = PageRequest.of(0, 10);

        return  postRepository.findAllByCategory(categoryDTO.toEntity(), pageRequest);

    }

    //게시글이 저장 될 카테고리 선택 리스트
    @Override
    public List<PostDTO> listCategoryForAdd(PostDTO postDTO, CategoryDTO categoryDTO) {
        return null;
    }


    @Override
    public Page<Post> listSearchKeyword(PostDTO postDTO) {

        PageRequest pageRequest = PageRequest.of(0, 10);

        return postRepository.findByTitleContainingOrContentContaining(postDTO.getTitle(),postDTO.getContent(), pageRequest);
    }


}
