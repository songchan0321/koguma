package com.fiveguys.koguma.repository.common;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.entity.Location;
import com.querydsl.core.types.dsl.EntityPathBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByRepAuthLocationFlagIs(boolean repAuthLocationFlag);

    List<Location> findAllById(Long id);

    Page<Location> findAllByMemberId(Long id, Pageable pageable);
    List<Location> findAllByMemberId(Long id);


    Location findByMemberIdAndRepAuthLocationFlag(Long id, boolean flag);


    boolean existsByMemberIdAndRepAuthLocationFlag(Long id, boolean b);
}
