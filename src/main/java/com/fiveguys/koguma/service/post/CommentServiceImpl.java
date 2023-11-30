package com.fiveguys.koguma.service.post;

import com.fiveguys.koguma.data.dto.CommentDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Comment;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Post;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.repository.post.CommentRepository;
import com.fiveguys.koguma.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private  final MemberRepository memberRepository;



    @Override
    public void addComment(CommentDTO commentDTO) {


    }

    @Override
    public void updateComment(CommentDTO commentDTO) {

    }

    @Override
    public void deleteComment(CommentDTO commentDTO, MemberDTO memberDTO) {

    }

    @Override
    public Page<Comment> listComment() {
        return null;
    }

    @Override
    public Page<Comment> listCommentByMember(MemberDTO memberDTO) {
        return null;
    }
}
