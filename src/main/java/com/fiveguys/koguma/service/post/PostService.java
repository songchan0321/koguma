package com.fiveguys.koguma.service.post;

import com.fiveguys.koguma.data.dto.CategoryDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PostDTO;
import com.fiveguys.koguma.data.entity.Category;
import com.fiveguys.koguma.data.entity.Post;

import java.util.List;

public interface PostService {

    List<PostDTO> listPost();

    List<PostDTO> listPostByMember(MemberDTO memberDTO);

    Long addPost(PostDTO postDTO, MemberDTO memberDTO);

    PostDTO getPost(PostDTO postDTO);

    PostDTO updatePost(PostDTO postDTO, MemberDTO memberDTO);

    void deletePost(PostDTO postDTO, MemberDTO memberDTO);

    List<PostDTO> listPostByViews(PostDTO postDTO);

    PostDTO getCategorySearch(PostDTO postDTO, CategoryDTO categoryDTO);

    List<PostDTO> listCategoryPost (PostDTO postDTO, CategoryDTO categoryDTO);

    List<PostDTO> listCategorySearch (PostDTO postDTO, CategoryDTO categoryDTO);

    void searchKeyword(PostDTO postDTO);

    List<PostDTO> listKeywordSearch(PostDTO postDTO);

    //조회수 증가 메서드
    int increaseViews(PostDTO postDTO);

}
