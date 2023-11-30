package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Comment;
import lombok.*;

@Data
public class CommentDTO {
    private Long id;
    private PostDTO postDTO;
    private MemberDTO memberDTO;
    private CommentDTO parentCommentDTO;
    private String content;
    private Boolean activeFlag;


    @Builder
    public CommentDTO(Long id, PostDTO postDTO, MemberDTO memberDTO,
                      CommentDTO parentcommentDTO, String content, Boolean activeFlag){
        this.id = id;
        this.postDTO = postDTO;
        this.memberDTO = memberDTO;
        this.parentCommentDTO = parentcommentDTO;
        this.content = content;
        this.activeFlag = activeFlag;
    }

    public Comment toEntity(){
        Comment comment = Comment.builder()
                .id(this.id)
                .post(postDTO.toEntity())
                .member(memberDTO.toEntity())
                .content(this.content)
                .activeFlag(this.activeFlag)
                .build();

        //부모 댓글 참조 설정
        if(this.parentCommentDTO != null){
            comment.setParentComment(this.parentCommentDTO.toEntity());
        }

        return comment;
    }

    public static CommentDTO fromEntity(Comment comment) {
        CommentDTO commentDTO = CommentDTO.builder()
                .id(comment.getId())
                .postDTO(PostDTO.fromEntity(comment.getPost()))
                .memberDTO(MemberDTO.fromEntity(comment.getMember()))
                .content(comment.getContent())
                .activeFlag(comment.getActiveFlag())
                .build();

        //부도 댓글 참조 설정
        if (comment.getParentcomment() != null) {
            commentDTO.setParentCommentDTO(CommentDTO.fromEntity(comment.getParentcomment()));
        }
        return commentDTO;
    }



}
