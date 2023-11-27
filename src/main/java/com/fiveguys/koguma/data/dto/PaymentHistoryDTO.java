package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.PaymentHistory;
import com.fiveguys.koguma.data.entity.PaymentHistoryType;
import lombok.Builder;

public class PaymentHistoryDTO {
    private Long id;
    private PaymentHistoryType type;
    private Integer price;
    private String info;

    @Builder
    public PaymentHistoryDTO(PaymentHistoryType type, Integer price, String info) {
        this.type = type;
        this.price = price;
        this.info = info;
    }

    public PaymentHistory toEntity() {
        PaymentHistory.builder().member()
    }
}
