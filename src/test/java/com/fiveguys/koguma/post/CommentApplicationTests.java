package com.fiveguys.koguma.post;


import com.fiveguys.koguma.data.dto.CommentDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PostDTO;
import com.fiveguys.koguma.data.entity.Comment;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Post;
import com.fiveguys.koguma.repository.member.MemberRepository;
import com.fiveguys.koguma.repository.post.CommentRepository;
import com.fiveguys.koguma.repository.post.PostRepository;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.post.CommentService;
import com.fiveguys.koguma.service.post.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CommentApplicationTests {

    @Autowired
    CommentService commentService;

    @Autowired
    MemberService memberService;

    @Autowired
    PostService postService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;


    @Test
    @DisplayName("댓글 작성")
    public void testAddComment() throws Exception{

        Member writer = memberService.getMember(4L).toEntity();
        Post post = postService.getPost(4L).toEntity();

        CommentDTO cd = CommentDTO.builder()
                .memberDTO(MemberDTO.fromEntity(writer))
                .postDTO(PostDTO.fromEntity(post))
                .content("댓글 테스트1")
                .build();


        Comment comment = Comment.createComment(cd.getPostDTO().toEntity(),cd.getMemberDTO().toEntity(),
                cd.getContent());

         Comment savedComment = commentRepository.save(comment);


        Assertions.assertThat(savedComment.getPost().getId()).isEqualTo(post.getId());
    }

    @Test
    @DisplayName("답글 작성")
    public void testAddReply() throws Exception {

        Member writer = memberService.getMember(4L).toEntity();
        Post post = postService.getPost(4L).toEntity();

        CommentDTO cd = CommentDTO.builder()
                .memberDTO(MemberDTO.fromEntity(writer))
                .postDTO(PostDTO.fromEntity(post))
                .content("답글 테스트1")
                .build();


        Comment reply = Comment.createComment(cd.getPostDTO().toEntity(),cd.getMemberDTO().toEntity(),
                cd.getContent());

        Comment savedReply = commentRepository.save(reply);


        Assertions.assertThat(savedReply.getPost().getId()).isEqualTo(post.getId());

    }

    @Test
    @DisplayName("댓글,답글 수정")
    public void testUpdateComment(){

        Member writer = memberService.getMember(4L).toEntity();
        Post post = postService.getPost(4L).toEntity();

        // Given
        Comment existingComment = Comment.builder()
                .member(writer)
                .post(post)
                .content("기존 댓글 내용")
                .activeFlag(true)
                .build();


        commentRepository.save(existingComment);

        CommentDTO updatedCommentDTO = new CommentDTO();
        updatedCommentDTO.setId(existingComment.getId());
        updatedCommentDTO.setContent("새로운 댓글 내용");

        // When
        commentService.updateComment(updatedCommentDTO);

        // Then
        Comment updatedComment = commentRepository.findById(existingComment.getId()).orElse(null);
        assertEquals(updatedCommentDTO.getContent(), updatedComment.getContent());
    }

    @Test
    @DisplayName("댓글/답글 삭제")
    public void deleteComment_ValidCommentAndOwner_ShouldDeleteComment() throws Exception{


        Long commentId = 4L;

        CommentDTO existComment = commentService.getComment(commentId);

        commentService.deleteComment(existComment);

        CommentDTO deletedComment = commentService.getComment(commentId);

        assertFalse(deletedComment.getActiveFlag());
    }


    @Test
    @DisplayName("댓글 리스트 조회")
    public  void testListComment() throws Exception{

        //given
        Post postId = postRepository.findById(4L).get();

        //when
        List<Comment> commentList = commentService.listComment(PostDTO.fromEntity(postId));

        //then
        assertNotNull(commentList);
    }

    @Test
    @DisplayName("회원이 작성한 댓글 리스트 조회")
    public void testListCommentByMember() throws Exception{

        //given
        Member memberId = memberRepository.findById(4L).get();
        MemberDTO memberDTO = memberService.getMember(4L);

        //when
        List<Comment> commentList = commentService.listCommentByMember(MemberDTO.fromEntity(memberId));

        //then
        assertNotNull(commentList);
    }

}
