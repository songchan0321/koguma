package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Commet;
import com.fiveguys.koguma.data.entity.Review;
import com.fiveguys.koguma.data.entity.ReviewId;
import lombok.*;

import javax.persistence.*;

@Data
public class CommetDTO {

    private Long id;
    private MemberDTO memberDTO;
    private ProductDTO productDTO;
    private String commet;

    @Builder
    public CommetDTO(Long id, MemberDTO memberDTO, ProductDTO productDTO, String commet) {
        this.id = id;
        this.memberDTO = memberDTO;
        this.productDTO = productDTO;
        this.commet = commet;
    }



//
//    public static CommetDTO fromEntity(Commet commet){
//        return CommetDTO.builder()
//                .id(commet.getId())
//                .memberDTO(MemberDTO.fromEntity(commet.getReview().getId().getMember()))
//                .productDTO(ProductDTO.fromEntity(commet.getReview().getId().getProduct()))
//                .commet(commet.getCommet()).build();
//    }
//    public Commet toEntity(){
//        return Commet.builder()
//                .id(id)
//                .review(Review.builder().id(ReviewId.builder().member(memberDTO.toEntity()).product(productDTO.toEntity()).build()).build())
//                .commet(commet)
//                .build();
//
//    }
}
