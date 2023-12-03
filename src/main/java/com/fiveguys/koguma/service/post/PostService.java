package com.fiveguys.koguma.service.post;

import com.fiveguys.koguma.data.dto.CategoryDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PostDTO;
import com.fiveguys.koguma.data.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    Page<Post> listPost();

    Page<Post> listPostByMember(MemberDTO memberDTO);

    void addPost(PostDTO postDTO);

    PostDTO getPost(Long id);

    void updatePost(PostDTO postDTO);

    void deletePost(PostDTO postDTO);

    Page<Post> listPostByViews(PostDTO postDTO);

    List<CategoryDTO>  listCategoryForSelect();

    Page<Post> listCategoryBySearch (CategoryDTO categoryDTO);



    Page<Post> listSearchKeyword(PostDTO postDTO);


    //조회수 증가 메서드
    void increaseViews(Long id);

}
