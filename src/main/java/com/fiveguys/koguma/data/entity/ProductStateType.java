package com.fiveguys.koguma.data.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStateType {

    SALE("판매 중"),
    SALED("거래 완료"),
    RESERVATION("예약 중"),
    HIDE("숨김");


    private final String name;
}
