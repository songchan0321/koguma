package com.fiveguys.koguma.data.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRoleType {

    MEMBER("멤버"),
    AUTH_MEMBER("인증된 멤버"),
    ADMIN("관리자");

    private final String name;
}