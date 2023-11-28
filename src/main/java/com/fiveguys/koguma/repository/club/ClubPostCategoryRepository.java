package com.fiveguys.koguma.repository.club;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubPostCategoryRepository extends JpaRepository<ClubPostCategoryRepository, Long> {
}
