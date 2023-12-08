package com.fiveguys.koguma.repository.common;

import com.fiveguys.koguma.data.dto.LocationDTO;
import com.fiveguys.koguma.data.entity.CategoryType;
import com.fiveguys.koguma.data.entity.Product;
import com.querydsl.core.types.EntityPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryRepository {

    Page<?> findAllByDistance(CategoryType target, LocationDTO locationDTO,
                              Pageable pageable, String keyword) throws Exception;
}
