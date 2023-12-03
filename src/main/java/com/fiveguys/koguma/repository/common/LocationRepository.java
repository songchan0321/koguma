package com.fiveguys.koguma.repository.common;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.entity.Location;
import com.querydsl.core.types.dsl.EntityPathBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByRepAuthLocationFlagIs(boolean repAuthLocationFlag);

    List<Location> findAllById(Long id);

    List<Location> findAllByMemberId(Long id);


    Location findByMemberIdAndRepAuthLocationFlag(Long id,boolean flag);

//    List<Object> findAllByDistance(EntityPathBase<Object> entity, double longitude, double latitude, int searchRange);

}
