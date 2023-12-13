package com.fiveguys.koguma.controller;


import com.fiveguys.koguma.data.dto.CommentDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PostDTO;
import com.fiveguys.koguma.data.entity.Comment;
import com.fiveguys.koguma.service.post.CommentService;
import com.fiveguys.koguma.service.post.PostService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
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
    public ResponseEntity<Void> addComment(
            @RequestBody CommentDTO commentDTO,
            @CurrentMember MemberDTO currentMember
    ){
        System.out.println("commentDto : "+commentDTO);
        try {
            if(currentMember == null || currentMember.getId() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            commentService.addComment(commentDTO, new MemberDTO());

            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateComment(
            @RequestBody CommentDTO commentDTO,
            @CurrentMember MemberDTO currentMember
    ){

        try{

            if(!commentDTO.getMemberDTO().getId().equals(currentMember.getId())){
                throw new Exception("권한이 없습니다.");
            }

            commentService.updateComment(commentDTO, currentMember);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/delete")
    public ResponseEntity<Void> deleteComment(
            @RequestBody CommentDTO commentDTO,
            @CurrentMember MemberDTO currentMember

    ){

        try{
            if(!commentDTO.getMemberDTO().getId().equals(currentMember.getId())){
                throw new Exception("권한이 없습니다.");
            }
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


    @GetMapping("/list/member")
    public ResponseEntity<List<CommentDTO>> listCommentByMember(
            @CurrentMember MemberDTO currentMember
    ){
        try {

            if (currentMember == null || currentMember.getId() == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            List<Comment> comments = commentService.listCommentByMember(currentMember);

            List<CommentDTO> collect = comments.stream()
                    .map((c) -> CommentDTO.fromEntity(c))
                    .collect(Collectors.toList());

            System.out.println("collect = " + collect);

            return new ResponseEntity<>(collect, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
