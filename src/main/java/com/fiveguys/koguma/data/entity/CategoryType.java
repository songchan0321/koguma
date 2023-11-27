package com.fiveguys.koguma.data.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryType {

    PRODUCT("상품"),
    POST("동네생활"),
    CLUB("모임");

    private final String name;

}
