package com.fiveguys.koguma.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveguys.koguma.data.dto.*;
import com.fiveguys.koguma.data.entity.CategoryType;
import com.fiveguys.koguma.data.entity.ImageType;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Post;
import com.fiveguys.koguma.service.common.CategoryService;
import com.fiveguys.koguma.service.common.ImageService;
import com.fiveguys.koguma.service.common.LocationService;
import com.fiveguys.koguma.service.post.PostService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
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

    private final LocationService locationService;

    private final ImageService imageService;


    //게시글 리스트 조회
//    @GetMapping("/list")
//    public ResponseEntity<Page<PostDTO>> listPost(
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "100") int size)
//    {
//        try{
//            PageRequest pageRequest = PageRequest.of(page, size);
//            Page<PostDTO> posts = postService.listPost(pageRequest).map(PostDTO::fromEntity);
//            return new ResponseEntity<>(posts, HttpStatus.OK);
//        } catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
////        LocationDTO locationDTO = locationService.getMemberRepLocation(memberDTO.getId());
////        List<PostDTO> postDTOS = postService.listPostByLocation(locationDTO,keyword,categoryId)
//    }
    @GetMapping("/list")
    public ResponseEntity<List<PostDTO>> listProduct(@RequestParam String keyword,  @CurrentMember MemberDTO memberDTO) throws Exception
    {
        LocationDTO locationDTO = locationService.getMemberRepLocation(memberDTO.getId());
        List<PostDTO> postDTOS = postService.listPostByLocation(locationDTO,keyword,null);
        return ResponseEntity.status(HttpStatus.OK).body(postDTOS);
    }

    @GetMapping("/list/member")
    public ResponseEntity<Page<PostDTO>> listPostByMember(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "100") int size,
            @CurrentMember MemberDTO currentMember
    ){

        try{

            if (currentMember == null || currentMember.getId() == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            PageRequest pageRequest = PageRequest.of(page,size);
            Page<PostDTO> posts = postService.listPostByMember(currentMember, pageRequest).map(PostDTO::fromEntity);

            return new ResponseEntity<>(posts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("/get/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable("postId") Long postId){

        try{
            PostDTO post = postService.getPost(postId);
            System.out.println("$$$$$$$$$$$$");
            return new ResponseEntity<>(post, HttpStatus.OK);
        }catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Void> addPost(
            @RequestBody PostDTO postDTO,
            @CurrentMember MemberDTO currentMember
    ){

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("@@@@@@@@@@@@@@@@@@@@" + postDTO);
        try{
            if(currentMember == null || currentMember.getId() == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            List<String> urls = postDTO.getImages();


            LocationDTO locationDTO =locationService.getMemberRepLocation(currentMember.getId());

            postDTO.setDong(locationDTO.getDong());
            postDTO.setLatitude(locationDTO.getLatitude());
            postDTO.setLongitude(locationDTO.getLongitude());

            //null => react 에서 set해줘야함
            System.out.println("postDTOcategory = " + postDTO.getCategoryDTO());

            PostDTO postDTO1 = postService.addPost(postDTO, currentMember);

            List<ImageDTO> imageDTOList = imageService.createImageDTOList(postDTO1 ,urls, ImageType.POST);
            System.out.println("&&&&&&&&&&&&&");
            imageDTOList.forEach(System.out::println);
            imageService.addImage(imageDTOList);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{postId}/update")
    public ResponseEntity<Void> updatePost(
            @PathVariable("postId") Long postId,
            @CurrentMember MemberDTO currentMember
            ){
        try{

            PostDTO postDTO = new PostDTO();
            postDTO.setId(postId);
            postService.updatePost(postDTO, currentMember);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{postId}/delete")
    public ResponseEntity<Void> deletePost(
            @PathVariable("postId") Long postId,
            @CurrentMember MemberDTO currentMember
    ) {
        try {


            postService.deletePost(postId, currentMember);
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

    @GetMapping("/list/category/view")
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
//    @GetMapping("/list/category/{categoryId}")
//    public ResponseEntity<Page<PostDTO>> listCategoryBySearch(@PathVariable("categoryId") Long categoryId) {
//
//        PageRequest pageRequest = PageRequest.of(0, 100);
//
//        CategoryDTO categoryDTO = categoryService.getCategory(categoryId);
//
//        categoryDTO.setId(categoryId);
//
//        Page<PostDTO> postPage = postService.listCategoryBySearch(categoryDTO, pageRequest).map(PostDTO::fromEntity);
//
//        return ResponseEntity.ok(postPage);
//    }
    @GetMapping("/list/category/{categoryId}")
    public ResponseEntity<List<PostDTO>> listCategoryBySearch(@PathVariable("categoryId") Long categoryId,@CurrentMember MemberDTO memberDTO) throws Exception {

        LocationDTO locationDTO =locationService.getMemberRepLocation(memberDTO.getId());
        List<PostDTO> postPage = postService.listCategoryBySearch(locationDTO,categoryId);

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



