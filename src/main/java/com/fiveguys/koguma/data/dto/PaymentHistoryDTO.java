package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.PaymentHistory;
import com.fiveguys.koguma.data.entity.PaymentHistoryType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PaymentHistoryDTO {
    private Long id;
    private PaymentHistoryType type;
    private Integer price;
    private String info;
    private MemberDTO memberDTO;
    private LocalDateTime regDate;
    @Builder
    public PaymentHistoryDTO(PaymentHistoryType type, Integer price, String info, MemberDTO memberDTO) {
        this.type = type;
        this.price = price;
        this.info = info;
        this.memberDTO = memberDTO;
    }
    public static PaymentHistoryDTO fromEntity(PaymentHistory paymentHistory) {
        PaymentHistoryDTO paymentHistoryDTO = new PaymentHistoryDTO();
        paymentHistoryDTO.setId(paymentHistory.getId());
        paymentHistoryDTO.setPrice(paymentHistory.getPrice());
        paymentHistoryDTO.setInfo(paymentHistory.getInfo());
        paymentHistoryDTO.setType(paymentHistory.getType());
        paymentHistoryDTO.setMemberDTO(MemberDTO.fromEntity(paymentHistory.getMember()));
        paymentHistoryDTO.setRegDate(paymentHistory.getRegDate());
        return paymentHistoryDTO;
    }
    public PaymentHistory toEntity() {
        return PaymentHistory.builder()
                .id(id)
                .price(price)
                .info(info)
                .type(type)
                .member(memberDTO.toEntity())
                .build();
    }

}
