package com.fiveguys.koguma.repository.post;

import com.fiveguys.koguma.data.entity.Category;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository <Post, Long> {

    Page<Post> findAllByMember(Member entity, PageRequest pageRequest);

    Page<Post> findAllByCategory(PageRequest pageRequest, Category entity);

    Page<Post> findTop10ByOrderByViewsDesc(Post entity, PageRequest pageRequest);

    Page<Post> findAllByCategory(Category entity, PageRequest pageRequest);

    Page<Post> findByTitleContainingOrContentContaining(String title, String content, PageRequest pageRequest);
}
