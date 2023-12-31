package com.fiveguys.koguma.repository.common;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.entity.*;
import com.querydsl.core.types.EntityPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryRepository {

    List<?> findAllByDistance(CategoryType target, LocationDTO locationDTO,
                              Pageable pageable, String keyword) throws Exception;
    List<Product> findAllByDistanceProduct(LocationDTO locationDTO, String keyword,Long categoryId) throws Exception;
    List<Post> findAllByDistancePost(LocationDTO locationDTO, String keyword,Long categoryId) throws Exception;
    List<Club> findAllByDistanceClub(LocationDTO locationDTO, String keyword,Long categoryId) throws Exception;
    List<Member> findAllByDistanceMember(LocationDTO locationDTO, String keyword) throws Exception;
}
