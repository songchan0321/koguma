package com.fiveguys.koguma.repository.post;

import com.fiveguys.koguma.data.entity.Category;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository <Post, Long> {


    Page<Post> findTop10ByOrderByViewsDesc(Pageable pageable);


    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    Page<Post> findByTitleOrContentContaining(@Param("keyword") String keyword, Pageable pageable);


    @Query("select p from Post p left join fetch p.image where p.id = :postId")
    Post findByPostIdWithImages(@Param("postId") Long postId);
    Page<Post> findAllByOrderByIdDesc(PageRequest pageRequest);

    Page<Post> findAllByMemberOrderByIdDesc(Member entity, PageRequest pageRequest);

    Page<Post> findAllByCategoryOrderByIdDesc(Category entity, PageRequest pageRequest);



}
