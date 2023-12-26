package com.fiveguys.koguma.repository.club;

import com.fiveguys.koguma.data.entity.ClubComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubCommentRepository extends JpaRepository<ClubComment, Long> {

    List<ClubComment> findByClubPostId(Long clubPostId);

    int countByClubPostId(Long clubPostId);
}
