package com.fiveguys.koguma.data.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum CategoryType {

    PRODUCT("PRODUCT"),
    POST("POST"),
    CLUB("CLUB");

    private final String name;


//    @JsonCreator
//    public static Category parsing(String inputValue) {
//        return Stream.of(CategoryType.values())
//                .filter(categoryType -> categoryType.toString().equals(inputValue.toUpperCase()))
//                .findFirst()
//                .orElse(null);
//    }

}
