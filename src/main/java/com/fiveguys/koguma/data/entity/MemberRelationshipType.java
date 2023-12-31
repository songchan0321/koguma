package com.fiveguys.koguma.data.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRelationshipType {

    BLOCK("차단"),
    FOLLOWING("팔로잉");

    private final String name;
}
