package com.fiveguys.koguma.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentHistoryType {
    CHARGE("충전"),
    TRANSFER("송금"),
    REFUND_REQUEST("환급 요청"),
    REFUND_SUCCESS("환급 성공");
    private final String type;

    public static boolean contains(String testType) {
        for (PaymentHistoryType type : PaymentHistoryType.values()) {
            if (type.name().contains(testType)) {
                return true;
            }
        }
        return false;
    }
}
