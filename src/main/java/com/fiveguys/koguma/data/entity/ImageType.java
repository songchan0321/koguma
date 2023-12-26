package com.fiveguys.koguma.data.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageType {

    CHATROOM("채팅"),
    CLUB_CHATROOM("모임채팅"),
    MESSAGE("메시지"),
    POST("동네생활"),
    CLUB("모임"),
    PRODUCT("상품"),
    MEMBER("회원"),
    CLUB_POST("모임겍시글");

    private final String name;
}