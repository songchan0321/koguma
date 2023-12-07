package com.fiveguys.koguma.service.post;

import com.fiveguys.koguma.data.dto.CommentDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PostDTO;
import com.fiveguys.koguma.data.entity.Comment;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface CommentService {

    void addComment(CommentDTO commentDTO);

    void updateComment(CommentDTO commentDTO);

    void deleteComment(CommentDTO commentDTO) throws AccessDeniedException;

    List<Comment> listComment(PostDTO postDTO);

    List<Comment> listReply(PostDTO postDTO);

    List<Comment> listCommentByMember(MemberDTO memberDTO);

    CommentDTO getComment(Long id);


}
