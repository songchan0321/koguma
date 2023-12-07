package com.fiveguys.koguma.controller;


import com.fiveguys.koguma.data.dto.CategoryDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PostDTO;
import com.fiveguys.koguma.data.entity.CategoryType;
import com.fiveguys.koguma.data.entity.Post;
import com.fiveguys.koguma.service.common.CategoryService;
import com.fiveguys.koguma.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@CrossOrigin("*")
public class PostRestController {

    private final PostService postService;


    private final CategoryService categoryService;


    //게시글 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<Page<PostDTO>> listPost(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size)
    {
        try{
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<PostDTO> posts = postService.listPost(pageRequest).map(PostDTO::fromEntity);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/list/member/{memberId}")
    public ResponseEntity<Page<PostDTO>> listPostByMember(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @PathVariable Long memberId
    ){

        try{
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setId(memberId);

            PageRequest pageRequest = PageRequest.of(page,size);
            Page<PostDTO> posts = postService.listPostByMember(memberDTO, pageRequest).map(PostDTO::fromEntity);

            return new ResponseEntity<>(posts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    // todo :: 접근 회원에 따라 수정, 삭제로 연결하기위해 security varidation이 추가 된 후 코드 수정 할 것
    @GetMapping("/get/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable("postId") Long postId){

        try{
            PostDTO post = postService.getPost(postId);
            return new ResponseEntity<>(post, HttpStatus.OK);
        }catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/add")
    public ResponseEntity<Void> addPost(@RequestBody PostDTO postDTO){

        try{
            postService.addPost(postDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<Void> updatePost(@RequestBody PostDTO postDTO){
        try{
            postService.updatePost(postDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/delete")
    public ResponseEntity<Void> deletePost(@RequestBody PostDTO postDTO) {
        try {
            postService.deletePost(postDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/increaseViews/{postId}")
    public ResponseEntity<Void> increaseViews(@PathVariable("postId") Long postId) {
        try {
            postService.increaseViews(postId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list/views")
    public ResponseEntity<Page<PostDTO>> listPostByViews(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<PostDTO> posts = postService.listPostByViews(pageRequest).map(PostDTO::fromEntity);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    //게시글 카테고리 리스트
    @GetMapping("/list/categoryType/{categoryType}")
    public ResponseEntity<List<CategoryDTO>> listCategoryForSelect() {

        List<CategoryDTO> categoryDTOList = categoryService.listCategory(CategoryType.POST);

        return ResponseEntity.ok(categoryDTOList);
    }

    //카테고리 별 게시글 리스트
    @GetMapping("/list/categoryId/{categoryId}")
    public ResponseEntity<Page<PostDTO>> listCategoryBySearch(@PathVariable("categoryId") Long categoryId) {

        PageRequest pageRequest = PageRequest.of(0, 10);

        CategoryDTO categoryDTO = categoryService.getCategory(categoryId);

        categoryDTO.setId(categoryId);

        Page<PostDTO> postPage = postService.listCategoryBySearch(categoryDTO, pageRequest).map(PostDTO::fromEntity);

        return ResponseEntity.ok(postPage);
    }

    //todo :: 영문, 숫자 검색 시 작동하지만 한글 검색어 입력시 404 오류 발생  => URL 인코딩시 해결?
    @GetMapping("list/search/{keyword}")
    public ResponseEntity<Page<PostDTO>> listSearchKeyword(
            @PathVariable("keyword") String keyword
    )
    {
        try{
            PageRequest pageRequest = PageRequest.of(0,10);

            Page<PostDTO> postPage = postService.listSearchKeyword(keyword, pageRequest).map(PostDTO::fromEntity);

            return new ResponseEntity<>(postPage, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



