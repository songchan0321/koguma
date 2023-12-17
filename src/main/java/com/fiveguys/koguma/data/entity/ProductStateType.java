package com.fiveguys.koguma.data.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStateType {

    SALE("SALE"),
    SALED("SALED"),
    RESERVATION("RESERVATION"),
    HIDE("HIDE");


    private final String name;
}
