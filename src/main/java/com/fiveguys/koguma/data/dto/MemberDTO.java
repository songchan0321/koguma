package com.fiveguys.koguma.data.dto;

import lombok.Data;

import javax.management.relation.Role;

@Data
public class MemberDTO {
    private String id;

    private String nickname;
    private String pw;
    private String imageId;
    private String phone;
    private Float score;
    private Boolean roleFlag;
    private Boolean socialFlag;
    private String email;
    private String paymentAccount;
    private String paymentBank;
    private String paymentBalance;
    private String paymentPw;
    private Boolean activeFlag;



}
