package com.fiveguys.koguma.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsDTO {
    //1. 휴대폰 번호 인증을위한 DTO

    private String to;
    private String authNumber;

}