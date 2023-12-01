package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.LikeFilterAssociationDTO;

public interface LikeFilterAssociationService {


    void addLikeProduct(LikeFilterAssociationDTO likeFilterAssociationDTO); // 시큐리티 적용 대상

    void deleteLikeProduct(Long associationId); // 시큐리티 적용 대상
}
