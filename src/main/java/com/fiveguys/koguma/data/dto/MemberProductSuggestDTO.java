package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.MemberProductSuggest;
import com.fiveguys.koguma.data.entity.MemberProductSuggestId;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
public class MemberProductSuggestDTO {

    private MemberProductSuggestId id;
    private int price;
    private LocalDateTime regDate;

    @Builder
    public MemberProductSuggestDTO(MemberProductSuggestId id, int price, LocalDateTime regDate) {
        this.id = id;
        this.price = price;
        this.regDate = regDate;
    }

    public static MemberProductSuggestDTO fromEntity(MemberProductSuggest memberProductSuggest){
        return MemberProductSuggestDTO.builder()
                .id(memberProductSuggest.getId())
                .price(memberProductSuggest.getPrice())
                .regDate(memberProductSuggest.getRegDate())
                .build();
    }

    public MemberProductSuggest toEntity(){
        return MemberProductSuggest.builder()
                .id(MemberProductSuggestId.builder()
                        .member_id(id.getMember_id())
                        .product_id(id.getProduct_id())
                        .build())
                .price(price)
                .build();
    }
}
