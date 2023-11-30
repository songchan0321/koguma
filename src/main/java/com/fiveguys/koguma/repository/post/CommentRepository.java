package com.fiveguys.koguma.repository.post;

import com.fiveguys.koguma.data.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Post, Long> {
}
