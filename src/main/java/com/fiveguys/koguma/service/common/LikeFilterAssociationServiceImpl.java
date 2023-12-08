package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.LikeFilterAssociationDTO;
import com.fiveguys.koguma.data.entity.LikeFilterAssociation;
import com.fiveguys.koguma.data.entity.Product;
import com.fiveguys.koguma.repository.common.LikeFilterAssociationRepository;
import com.fiveguys.koguma.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeFilterAssociationServiceImpl implements LikeFilterAssociationService{


    private final LikeFilterAssociationRepository likeFilterAssociationRepository;

    public Page<Product> likeProductList(Long memberId,int page) {
        Pageable pageable = PageRequest.of(page,10);
        return likeFilterAssociationRepository.findAllByMemberId(memberId,pageable);
    }

    public void addLikeProduct(LikeFilterAssociationDTO likeFilterAssociationDTO) {
        likeFilterAssociationRepository.save(likeFilterAssociationDTO.toEntity());
    }

    public void deleteLikeProduct(Long id) {
        likeFilterAssociationRepository.deleteById(id);
    }
}
