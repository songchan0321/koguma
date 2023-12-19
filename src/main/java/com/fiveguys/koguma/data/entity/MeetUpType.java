package com.fiveguys.koguma.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MeetUpType {

    SCHEDULE("SCHEDULE"),
    COMPLETE("COMPLETE");

    private final String name;

}
