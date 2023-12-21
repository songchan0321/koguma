package com.fiveguys.koguma.repository.club;

import com.fiveguys.koguma.data.entity.Club;
import com.fiveguys.koguma.data.entity.ClubPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubPostRepository extends JpaRepository<ClubPost, Long> {
    List<ClubPost> findByClubIn(List<Club> clubs);

    List<ClubPost> findByClubId(Long clubId);

    List<ClubPost> findByClubPostCategoryId(Long categoryId);
}
