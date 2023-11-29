package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Comment;
import lombok.*;

@Data
public class CommentDTO {
    private Long id;
    private PostDTO postDTO;
    private MemberDTO memberDTO;
    private CommentDTO commentDTO;
    private String content;
    private Boolean activeFlag;


    @Builder
    public CommentDTO(Long id, PostDTO postDTO, MemberDTO memberDTO,
                      CommentDTO commentDTO, String content, Boolean activeFlag){
        this.id = id;
        this.postDTO = postDTO;
        this.memberDTO = memberDTO;
        this.commentDTO = commentDTO;
        this.content = content;
        this.activeFlag = activeFlag;
    }

    public Comment toEntity(){
        return Comment.builder()
                .id(this.id)
                .post(postDTO.toEntity())
                .member(memberDTO.toEntity())
                .comment(commentDTO.toEntity())
                .content(this.content)
                .activeFlag(this.activeFlag)
                .build();
    }

    public static CommentDTO fromEntity(Comment comment){
        return CommentDTO.builder()
                .id(comment.getId())
                .postDTO(PostDTO.fromEntity(comment.getPost()))
                .memberDTO(MemberDTO.fromEntity(comment.getMember()))
                .commentDTO(CommentDTO.fromEntity(comment.getComment()))
                .content(comment.getContent())
                .activeFlag(comment.getActiveFlag())
                .build();

    }



}
