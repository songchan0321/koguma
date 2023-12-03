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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private  final MemberRepository memberRepository;



    @Override
    public void addComment(CommentDTO commentDTO) {

        Long parentId = commentDTO.getParentDTO().getId();

        //부모가 있는 답글
        if(parentId != null) {
            //부모 유효성 체크
            Comment parent = findVerifiedComment(parentId);
            //부모 댓글 설정
            commentDTO.updateParent(parent);
        }
        commentRepository.save(commentDTO.toEntity());
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

    private List<Comment> buildCommentTree(List<Comment> comments){

        Map<Long, Comment> commentMap = new HashMap<>();

        //댓글, id를 키로 삼는 맵으로 변환
        for (Comment comment : comments){
            commentMap.put(comment.getId(), comment);
        }

        //최종적으로 변환할 결과 리스트
        List<Comment> result = new ArrayList<>();

        //댓글을 부모-자식 관계에 맞게 구성
        for (Comment comment : comments){
            if (comment.getParent() != null){
                Comment parent = commentMap.get(comment.getParent().getId());
                if(parent != null){
                    parent.addChild(comment);
                }
            }else{
                result.add(comment);
            }
        }
        return result;
    }

    @Override
    public List<Comment> listComment(PostDTO postDTO) {

        List<Comment> allComments = commentRepository.findAllByPostId(postDTO.getId());
        return buildCommentTree(allComments);

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
