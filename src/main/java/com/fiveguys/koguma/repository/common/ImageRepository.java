package com.fiveguys.koguma.repository.common;

import com.fiveguys.koguma.data.entity.Image;
import com.fiveguys.koguma.data.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
