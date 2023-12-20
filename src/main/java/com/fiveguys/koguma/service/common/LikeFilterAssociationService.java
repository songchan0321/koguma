package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.LikeFilterAssociationDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.data.entity.LikeFilterAssociation;
import com.fiveguys.koguma.data.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LikeFilterAssociationService {


    List<LikeFilterAssociationDTO> likeProductList(Long memberId);

    LikeFilterAssociationDTO getLikeProduct(Long productId,Long memberId);

    void addLikeProduct(LikeFilterAssociationDTO likeFilterAssociationDTO); // 시큐리티 적용 대상

    void deleteLikeProduct(Long id); // 시큐리티 적용 대상
}
