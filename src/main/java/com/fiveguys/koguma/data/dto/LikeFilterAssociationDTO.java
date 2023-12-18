package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.*;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class LikeFilterAssociationDTO {


    private Long id;
    private MemberDTO memberDTO;
    private ProductDTO productDTO;
    private CategoryDTO categoryDTO;
    private PostDTO postDTO;

    @Builder
    public LikeFilterAssociationDTO(Long id, MemberDTO memberDTO, ProductDTO productDTO, CategoryDTO categoryDTO, PostDTO postDTO) {
        this.id = id;
        this.memberDTO = memberDTO;
        this.productDTO = productDTO;
        this.categoryDTO = categoryDTO;
        this.postDTO = postDTO;
    }

    public static LikeFilterAssociationDTO fromEntity(LikeFilterAssociation likeFilterAssociation){
        LikeFilterAssociationDTO.LikeFilterAssociationDTOBuilder builder = LikeFilterAssociationDTO.builder()
                .id(likeFilterAssociation.getId())
                .memberDTO(MemberDTO.fromEntity(likeFilterAssociation.getMember()));

                if(likeFilterAssociation.getProduct() != null){
                    builder.productDTO(ProductDTO.fromEntityContainImage(likeFilterAssociation.getProduct()));
                }
                if(likeFilterAssociation.getPost() != null){
                    builder.postDTO(PostDTO.fromEntity(likeFilterAssociation.getPost()));
                }
                if (likeFilterAssociation.getCategory() != null)
                    builder.categoryDTO(CategoryDTO.fromDTO(likeFilterAssociation.getCategory()));

        return builder.build();


    }

    public LikeFilterAssociation toEntity() {

        LikeFilterAssociation likeFilterAssociation = null;
        if (postDTO != null) {
            likeFilterAssociation = LikeFilterAssociation.builder()
                    .id(id)
                    .member(memberDTO.toEntity())
                    .post(postDTO.toEntity())
                    .build();
        }
        if (productDTO != null) {
            likeFilterAssociation = LikeFilterAssociation.builder()
                    .id(id)
                    .member(memberDTO.toEntity())
                    .product(productDTO.toEntity())
                    .build();
        }
        if (categoryDTO != null) {
            likeFilterAssociation = LikeFilterAssociation.builder()
                    .id(id)
                    .member(memberDTO.toEntity())
                    .category(categoryDTO.toEntity())
                    .build();
        }
        return likeFilterAssociation;

    }

}
