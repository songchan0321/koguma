package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.MemberProductSuggest;
import com.fiveguys.koguma.data.entity.MemberProductSuggestId;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
public class MemberProductSuggestDTO {

    private MemberDTO memberDTO;
    private ProductDTO productDTO;
    private int price;
    private LocalDateTime regDate;


    @Builder
    public MemberProductSuggestDTO(MemberDTO memberDTO, ProductDTO productDTO, int price, LocalDateTime regDate) {
        this.memberDTO = memberDTO;
        this.productDTO = productDTO;
        this.price = price;
        this.regDate = regDate;
    }



    public static MemberProductSuggestDTO fromEntity(MemberProductSuggest memberProductSuggest){
        return MemberProductSuggestDTO.builder()
                .memberDTO(MemberDTO.fromEntity(memberProductSuggest.getId().getMember()))
                .productDTO(ProductDTO.fromEntity(memberProductSuggest.getId().getProduct()))
                .price(memberProductSuggest.getPrice())
                .regDate(memberProductSuggest.getRegDate())
                .build();
    }

    public MemberProductSuggest toEntity(){
        return MemberProductSuggest.builder()
                .id(MemberProductSuggestId.builder()
                        .member(memberDTO.toEntity())
                        .product(productDTO.toEntity())
                        .build())
                .price(price)
                .build();
    }
}
