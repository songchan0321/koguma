package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.LikeFilterAssociationDTO;
import com.fiveguys.koguma.data.entity.LikeFilterAssociation;
import com.fiveguys.koguma.repository.common.LikeFilterAssociationRepository;
import com.fiveguys.koguma.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeFilterAssociationServiceImpl implements LikeFilterAssociationService{


    private final LikeFilterAssociationRepository likeFilterAssociationRepository;

    public void addLikeProduct(LikeFilterAssociationDTO likeFilterAssociationDTO) {


        likeFilterAssociationRepository.save(likeFilterAssociationDTO.toEntity());
    }

    public void deleteLikeProduct(Long id) {
        likeFilterAssociationRepository.deleteById(id);
    }
}
