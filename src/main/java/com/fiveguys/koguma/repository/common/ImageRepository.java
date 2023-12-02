package com.fiveguys.koguma.repository.common;

import com.fiveguys.koguma.data.dto.ImageDTO;
import com.fiveguys.koguma.data.entity.Image;
import com.fiveguys.koguma.data.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByProductId(Long productId);


    List<Image> findAllByClubId(Long targetId);

    List<Image> findAllByPostId(Long targetId);

//    List<Image> findAllByMessageId(Long targetId);


//    Image findByMessageIdAndRepImageFlagTrue(Long targetId);


    Image findByProductIdAndRepImageFlagTrue(Long targetId);


    Image findByClubIdAndRepImageFlagTrue(Long targetId);

    Image findByPostIdAndRepImageFlagTrue(Long targetId);
}
