package com.fiveguys.koguma.repository.club;

import com.fiveguys.koguma.data.entity.ClubPostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubPostCategoryRepository extends JpaRepository<ClubPostCategory, Long> {

    List<ClubPostCategory> findByClubId(Long clubId);

}
