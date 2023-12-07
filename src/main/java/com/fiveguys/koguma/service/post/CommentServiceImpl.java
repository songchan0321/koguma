package com.fiveguys.koguma.service.post;

import com.fiveguys.koguma.data.dto.CommentDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PostDTO;
import com.fiveguys.koguma.data.entity.Comment;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.repository.post.CommentRepository;
import com.fiveguys.koguma.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;



    @Override
    public void addComment(CommentDTO commentDTO) {


        //부모가 있는 답글
        if(commentDTO.getParentDTO() != null && commentDTO.getParentDTO().getId() != null) {
            //부모 유효성 체크
            Comment parent = findVerifiedComment(commentDTO.getParentDTO().getId());

            Comment saved = commentRepository.save(commentDTO.toEntity());
            saved.updateParent(parent);

        }else {
            commentRepository.save(commentDTO.toEntity());
        }

    }

    private Comment findVerifiedComment(Long parentId) {

        Comment parent = commentRepository.findById(parentId)
                .orElseThrow();
        if(!parent.getActiveFlag()){
            throw new EntityNotFoundException("댓글 비활성화 상태");
        }
        return parent;
    }

    @Override
    public void updateComment(CommentDTO commentDTO) {

        Long commentId = commentDTO.getId();

        //기존 댓글 조회
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(()-> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

        //새로운 내용으로 업데이트
        existingComment.setContent(commentDTO.getContent());

        //수정 내용 저장
        commentRepository.save(existingComment);
    }



    @Override
    public void deleteComment(CommentDTO commentDTO) throws AccessDeniedException {

        Long commentId = commentDTO.getId();

        //댓글 조회
        Comment existingComment =commentRepository.findById(commentId)
                .orElseThrow(()-> new EntityNotFoundException("댓글을 찾을 수 없습니다."));


        //댓글 비활성화 상태 변경
        existingComment.setActiveFlag(false);

        //삭제문구로 변경
        existingComment.setContent("삭제된 댓글입니다.");

        //변경 내용 저장
        commentRepository.save(existingComment);
    }


    @Override
    public List<Comment> listComment(PostDTO postDTO) {

        List<Comment> allComments = commentRepository.findAllByPostId(postDTO.getId());

        List<Comment> comments = allComments.stream()
                .filter(comment -> comment.getParent() == null)
                .collect(Collectors.toList());

        return comments;

    }

    @Override
    public List<Comment> listReply(PostDTO postDTO) {

        List<Comment> allComments = commentRepository.findAllByPostId(postDTO.getId());

        List<Comment> reply = allComments.stream()
                .filter(comment -> comment.getParent() != null)
                .collect(Collectors.toList());


        return reply;
    }

    @Override
    public List<Comment> listCommentByMember(MemberDTO memberDTO) {

        return commentRepository.findAllByMemberId(memberDTO.getId());
    }

    @Override
    public CommentDTO getComment(Long id) {

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NoResultException("해당 댓글/답글 정보가 존재하지 않습니다."));

        return CommentDTO.fromEntity(comment);
    }
}
