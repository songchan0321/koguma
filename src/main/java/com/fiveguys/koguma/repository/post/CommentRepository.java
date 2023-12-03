package com.fiveguys.koguma.repository.post;

import com.fiveguys.koguma.data.entity.Comment;
import com.fiveguys.koguma.data.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findAllByPostId(Long id);

    List<Comment> findAllByMemberId(Long id);
}
