package com.fiveguys.koguma.repository.post;

import com.fiveguys.koguma.data.entity.Comment;
import com.fiveguys.koguma.data.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findAllByPostId(Long id);

    List<Comment> findAllByMemberId(Long id);

    @Query("SELECT DISTINCT c.post FROM Comment c WHERE c.member.id = :memberId")
    Page<Post> findCommentedPostByMemberId(Long memberId, PageRequest pageRequest);

    List<Comment> findAllByPostIdAndParentIdNotNull(Long id);

    Arrays findAllByParentIdNotNull();

    List<Comment> findAllByPostIdAndParentId(Long id, Long id1);

    List<Comment> findAllByParentId(Long id);
}
