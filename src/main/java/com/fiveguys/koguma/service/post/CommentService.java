package com.fiveguys.koguma.service.post;

import com.fiveguys.koguma.data.dto.CommentDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {

    void addComment(CommentDTO commentDTO);

    void updateComment(CommentDTO commentDTO);

    void deleteComment(CommentDTO commentDTO, MemberDTO memberDTO);

    Page<Comment> listComment();

    Page<Comment> listCommentByMember(MemberDTO memberDTO);


}
