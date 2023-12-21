package com.fiveguys.koguma.repository.club;

import com.fiveguys.koguma.data.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    @Query("SELECT c FROM Club c WHERE c.latitude = :latitude AND c.longitude = :longitude")
    List<Club> findClubsByLocation(@Param("latitude") Double latitude, @Param("longitude") Double longitude);

//    List<Club> findClubsByCategoryId(Long categoryId);

//    @Query("SELECT c, COUNT(cm) FROM Club c LEFT JOIN c.Club cm GROUP BY c.id")
//    List<Object[]> findClubWithMemberCount();

    @Query("SELECT c, COUNT(cm) FROM Club c LEFT JOIN c.clubMembers cm WHERE c.category.id = :categoryId GROUP BY c.id")
    List<Club> findClubsByCategoryId(@Param("categoryId") Long categoryId);}
