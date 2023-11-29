package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.BaseTime;
import com.fiveguys.koguma.data.entity.Chatroom;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatroomDTO{
    private Long id;
    private ProductDTO productDTO;
    private MemberDTO buyerDTO;
    private Integer price;
    private LocalDateTime sellerEnterDate;
    private LocalDateTime buyerEnterDate;
    private Boolean activeFlag;
    private LocalDateTime regDate;
    public static ChatroomDTO formEntity(Chatroom chatroom) {
        return ChatroomDTO.builder()
                .id(chatroom.getId())
                .buyerDTO(MemberDTO.fromEntity(chatroom.getBuyer()))
                .productDTO(ProductDTO.fromEntity(chatroom.getProduct()))
                .sellerEnterDate(chatroom.getSellerEnterDate())
                .buyerEnterDate(chatroom.getBuyerEnterDate())
                .price(chatroom.getPrice())
                .activeFlag(chatroom.getActiveFlag())
                .regDate(chatroom.getRegDate())
                .build();
    }

    public Chatroom toEntity() {
        return Chatroom.builder()
                .id(id)
                .product(productDTO.toEntity())
                .buyer(buyerDTO.toEntity())
                .price(price)
                .sellerEnterDate(sellerEnterDate)
                .buyerEnterDate(buyerEnterDate)
                .activeFlag(activeFlag)
                .build();
    }
}
