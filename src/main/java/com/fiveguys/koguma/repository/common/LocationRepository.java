package com.fiveguys.koguma.repository.common;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByRepAuthLocationFlagIs(boolean repAuthLocationFlag);

    List<Location> findAllById(Long id);
}
