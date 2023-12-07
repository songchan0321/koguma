package com.fiveguys.koguma.controller;


import com.fiveguys.koguma.data.dto.CommentDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PostDTO;
import com.fiveguys.koguma.data.entity.Comment;
import com.fiveguys.koguma.service.post.CommentService;
import com.fiveguys.koguma.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@CrossOrigin("*")
public class CommentRestController {


    private final CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<Void> addComment(@RequestBody CommentDTO commentDTO){
        System.out.println("commentDto : "+commentDTO);
        try {
            commentService.addComment(commentDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateComment(@RequestBody CommentDTO commentDTO){

        try{
            commentService.updateComment(commentDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/delete")
    public ResponseEntity<Void> deleteComment(@RequestBody CommentDTO commentDTO){

        try{
            commentService.deleteComment(commentDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/get/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable("commentId") Long commentId){

        try{
            CommentDTO commentDTO = commentService.getComment(commentId);
            return new ResponseEntity<>(commentDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/list/{postId}")
    public ResponseEntity<List<CommentDTO>> listComment(
            @PathVariable (name = "postId") Long postId
    ){

        try{
            PostDTO postDTO = new PostDTO();
            postDTO.setId(postId);

            List<Comment> commentDTOS = commentService.listComment(postDTO);
            for (Comment comment : commentDTOS) {
                System.out.println("comment = " + comment.getContent());
            }

            List<CommentDTO> collect = commentDTOS.stream()
                    .map((c) -> CommentDTO.fromEntity(c))
                    .collect(Collectors.toList());


            return new ResponseEntity<>(collect,HttpStatus.OK);
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("/list/reply/{postId}")
    public ResponseEntity<List<CommentDTO>> listReply(
            @PathVariable (name = "postId") Long postId
    ){
        try{
            PostDTO postDTO = new PostDTO();
            postDTO.setId(postId);

            List<Comment> commentDTOS = commentService.listReply(postDTO);

            List<CommentDTO> collect = commentDTOS.stream()
                    .map((c) -> CommentDTO.fromEntity(c))
                    .collect(Collectors.toList());


            return new ResponseEntity<>(collect,HttpStatus.OK);
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/list/member/{memberId}")
    public ResponseEntity<List<CommentDTO>> listCommentByMember(
            @PathVariable (name = "memberId") Long memberId
    ){
        try {
            MemberDTO memberDTO =new MemberDTO();
            memberDTO.setId(memberId);

            List<Comment> comments = commentService.listCommentByMember(memberDTO);

            List<CommentDTO> collect = comments.stream()
                    .map((c) -> CommentDTO.fromEntity(c))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(collect, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
