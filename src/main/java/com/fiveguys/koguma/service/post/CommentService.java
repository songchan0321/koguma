package com.fiveguys.koguma.service.post;

import com.fiveguys.koguma.data.dto.CommentDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PostDTO;
import com.fiveguys.koguma.data.entity.Comment;
import com.fiveguys.koguma.data.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface CommentService {

    void addComment(CommentDTO commentDTO, MemberDTO memberDTO);

    void updateComment(CommentDTO commentDTO, MemberDTO memberDTO);

    void deleteComment(CommentDTO commentDTO) throws AccessDeniedException;

    List<Comment> listComment(PostDTO postDTO);

    List<Comment> listReply(Long commentId);

    Page<Post> listCommentedPostByMember(MemberDTO memberDTO,  PageRequest pageRequest);

    List<Comment> listCommentByMember(MemberDTO memberDTO);

    CommentDTO getComment(Long id);


}
