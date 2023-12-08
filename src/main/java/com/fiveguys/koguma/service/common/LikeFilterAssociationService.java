package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.LikeFilterAssociationDTO;
import com.fiveguys.koguma.data.entity.Product;
import org.springframework.data.domain.Page;

public interface LikeFilterAssociationService {


    Page<Product> likeProductList(Long memberId,int page);

    void addLikeProduct(LikeFilterAssociationDTO likeFilterAssociationDTO); // 시큐리티 적용 대상

    void deleteLikeProduct(Long associationId); // 시큐리티 적용 대상
}
