package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.MemberRoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String nickname;
    private String pw;
    private Long imageId;
    private String phone;
    private Float score;
    private Boolean roleFlag;
    private Boolean socialFlag;
    private String email;
    private String paymentAccount;
    private String paymentBank;
    private Integer paymentBalance;
    private String paymentPw;
    private Boolean activeFlag;
    private LocalDateTime regDate;
    private MemberRoleType memberRoleType;

    public MemberDTO() {

    }


    public static MemberDTO fromEntity(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setPw(member.getPw());
        memberDTO.setNickname(member.getNickname());
        memberDTO.setImageId(member.getImageId());
        memberDTO.setPhone(member.getPhone());
        memberDTO.setRoleFlag(member.getRoleFlag());
        memberDTO.setSocialFlag(member.getSocialFlag());
        memberDTO.setPaymentAccount(member.getPaymentAccount());
        memberDTO.setPaymentBank(member.getPaymentBank());
        memberDTO.setPaymentBalance(member.getPaymentBalance());
        memberDTO.setPaymentPw(member.getPaymentPw());
        memberDTO.setActiveFlag(member.getActiveFlag());
        memberDTO.setScore(member.getScore());
        memberDTO.setMemberRoleType(member.getMemberRoleType());
        memberDTO.setRegDate(member.getRegDate());
        return memberDTO;
    }

    public Member toEntity() {
        Member member = new Member();
        member.setId(id);
        member.setNickname(nickname);
        member.setPw(pw);
        member.setImageId(imageId);
        member.setPhone(phone);
        member.setScore(score);
        member.setRoleFlag(roleFlag);
        member.setSocialFlag(socialFlag);
        member.setEmail(email);
        member.setPaymentAccount(paymentAccount);
        member.setPaymentBank(paymentBank);
        member.setPaymentBalance(paymentBalance);
        member.setPaymentPw(paymentPw);
        member.setActiveFlag(activeFlag);
        member.setMemberRoleType(memberRoleType);
        return member;
    }
}

