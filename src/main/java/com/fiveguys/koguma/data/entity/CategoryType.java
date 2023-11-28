package com.fiveguys.koguma.data.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum CategoryType {

    PRODUCT("상품"),
    POST("동네생활"),
    CLUB("모임");

    private final String name;

//    @JsonCreator
//    public static Category parsing(String inputValue) {
//        return Stream.of(CategoryType.values())
//                .filter(categoryType -> categoryType.toString().equals(inputValue.toUpperCase()))
//                .findFirst()
//                .orElse(null);
//    }

}
