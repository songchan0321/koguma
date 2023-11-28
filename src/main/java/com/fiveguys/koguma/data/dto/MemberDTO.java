package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Member;
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
    private Long pw;
    private Long imageId;
    private Number phone;
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

    public MemberDTO() {

    }


    public static MemberDTO toMemberDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setPw(member.getPw());
        memberDTO.setNickname(member.getNickname());
        memberDTO.setImageId(member.getImageId());
        memberDTO.setPhone(member.getPhone());
        memberDTO.setPaymentAccount(member.getPaymentAccount());
        memberDTO.setPaymentBank(member.getPaymentBank());
        memberDTO.setPaymentBalance(member.getPaymentBalance());
        memberDTO.setPaymentPw(member.getPaymentPw());
        memberDTO.setActiveFlag(member.getActiveFlag());
        return memberDTO;
    }
    public static Member fromEntity(MemberDTO memberDTO) {
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setNickname(memberDTO.getNickname());
        member.setPw(memberDTO.getPw());
        member.setImageId(memberDTO.getImageId());
        member.setPhone(memberDTO.getPhone());
        member.setScore(memberDTO.getScore());
        member.setRoleFlag(memberDTO.getRoleFlag());
        member.setSocialFlag(memberDTO.getSocialFlag());
        member.setEmail(memberDTO.getEmail());
        member.setPaymentAccount(memberDTO.getPaymentAccount());
        member.setPaymentBank(memberDTO.getPaymentBank());
        member.setPaymentBalance(memberDTO.getPaymentBalance());
        member.setPaymentPw(memberDTO.getPaymentPw());
        member.setActiveFlag(memberDTO.getActiveFlag());
        return member;
    }
}

