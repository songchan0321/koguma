package com.fiveguys.koguma.service.post;

import com.fiveguys.koguma.data.dto.*;
import com.fiveguys.koguma.data.entity.CategoryType;
import com.fiveguys.koguma.data.entity.Comment;
import com.fiveguys.koguma.data.entity.Post;
import com.fiveguys.koguma.data.entity.Product;
import com.fiveguys.koguma.repository.common.CategoryRepository;
import com.fiveguys.koguma.repository.common.QueryRepository;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.repository.post.PostRepository;
import com.fiveguys.koguma.service.common.CategoryService;
import com.fiveguys.koguma.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {


    private final CategoryService categoryService;
    private final PostRepository postRepository;
    private final QueryRepository queryRepository;


    @Override
    public Page<Post> listPost(PageRequest pageRequest) {


        return postRepository.findAllByOrderByIdDesc(pageRequest);
    }


    @Override
    public Page<Post> listPostByMember(MemberDTO memberDTO, PageRequest pageRequest) {

        return  postRepository.findAllByMemberOrderByIdDesc(memberDTO.toEntity(), pageRequest);

    }
    @Override
    public List<PostDTO> listPostByLocation(LocationDTO locationDTO,String keyword,Long categoryId) throws Exception {
        List<Post> postList = queryRepository.findAllByDistancePost(locationDTO,keyword,categoryId);
        return postList.stream().filter(Post::getActiveFlag).map(PostDTO::fromEntityContainImage).collect(Collectors.toList());
    }

    @Override
    public PostDTO addPost(PostDTO postDTO, MemberDTO memberDTO) {

        postDTO.setMemberDTO(memberDTO);


        return PostDTO.fromEntity(postRepository.save(postDTO.toEntity()));
    }


//    @Override
//    public PostDTO getPost(Long id) {
//
//        Post post = postRepository.findById(id).orElseThrow(()->new NoResultException("해당 게시글 정보가 존재하지 않습니다."));
//
//        return PostDTO.fromEntity(post);
//    }
    @Override
    public PostDTO getPost(Long id) {
        System.out.println("getPOst 들어옴");
        Post post = postRepository.findByPostIdWithImages(id);
        List<ImageDTO> imageList = post.getImage().stream().map(ImageDTO::fromEntity).collect(Collectors.toList());
        PostDTO postDTO = PostDTO.fromEntity(post);
        postDTO.setImageDTO(imageList);
        return postDTO;
    }



    @Override
    public void updatePost(PostDTO postDTO, MemberDTO memberDTO) {

        Long postId = postDTO.getId();

        //기존 게시글 조회
            Post existingPost = postRepository.findById(postId)
                            .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
            existingPost.setTitle(postDTO.getTitle());
            existingPost.setCategory(postDTO.toEntity().getCategory());
            existingPost.setContent(postDTO.getContent());

            postRepository.save(existingPost);
    }

    @Override
    public void deletePost(Long id, MemberDTO memberDTO) {



        Post existingPost = postRepository.findById(id)
                        .orElseThrow(()-> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        //게시글 비활성화 상태 변경
        existingPost.setActiveFlag(false);

        postRepository.save(existingPost);
    }

    public void increaseViews(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoResultException("해당 게시글의 정보가 존재하지 않습니다."));
        post.increaseViews(post.getViews()); // 조회수 증가 작업은 여기서는 하지 않음
        postRepository.save(post); // 저장을 통해 조회수 업데이트

    }

    @Override
    public Page<Post> listPostByViews(Pageable pageRequest) {

        return  postRepository.findTop10ByOrderByViewsDesc(pageRequest);

    }

    //카테고리 별 검색 결과
//    @Override
//    public Page<Post> listCategoryBySearch(CategoryDTO categoryDTO, PageRequest pageRequest) {
//
//        return  postRepository.findAllByCategoryOrderByIdDesc(categoryDTO.toEntity(), pageRequest);
//
//    }
    @Override
    public List<PostDTO> listCategoryBySearch(LocationDTO locationDTO,Long categoryId) throws Exception {

        List<Post> postList = queryRepository.findAllByDistancePost(locationDTO,null,categoryId);
        return postList.stream().filter(Post::getActiveFlag).map(PostDTO::fromEntityContainImage).collect(Collectors.toList());

    }


    @Override
    public Page<Post> listSearchKeyword(String keyword, PageRequest pageRequest) {


        return postRepository.findByTitleOrContentContaining(keyword, pageRequest);
    }


}
