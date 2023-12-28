package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private PostDTO postDTO;
    private MemberDTO memberDTO;
    private CommentDTO parentDTO;
    private String content;
    private Boolean activeFlag;
    private LocalDateTime regDate;


    @Builder
    public CommentDTO(Long id, PostDTO postDTO, MemberDTO memberDTO,
                      CommentDTO parentDTO, String content, LocalDateTime regDate, Boolean activeFlag){
        this.id = id;
        this.postDTO = postDTO;
        this.memberDTO = memberDTO;
        this.parentDTO = parentDTO;
        this.content = content;
        this.regDate = regDate;
        this.activeFlag = activeFlag;
    }

    public CommentDTO() {

    }

    public Comment toEntity(){
        Comment comment = Comment.builder()
                .id(this.id)
                .post(postDTO.toEntity())
                .member(memberDTO.toEntity())
                .content(this.content)

                .activeFlag(this.activeFlag)
                .build();

        return comment;
    }


    public static CommentDTO fromEntity(Comment comment) {
        CommentDTO commentDTO = CommentDTO.builder()
                .id(comment.getId())
                .postDTO(PostDTO.fromEntity(comment.getPost()))
                .memberDTO(MemberDTO.fromEntity(comment.getMember()))
                .content(comment.getContent())
                .regDate(comment.getRegDate())
                .activeFlag(comment.getActiveFlag())
                .build();

        //부모 댓글 참조 설정
        if (comment.getParent() != null && comment.getId().equals(comment.getParent().getId())) {
            commentDTO.setParentDTO(CommentDTO.fromEntity(comment.getParent()));
        }
        return commentDTO;
    }

    //부모가 있을 경우 자식 댓글에서 부모를 지정
    public void updateParent(Comment parent) {
        this.parentDTO = CommentDTO.fromEntity(parent);
        parent.getChildren().add(this.toEntity());
    }
}
