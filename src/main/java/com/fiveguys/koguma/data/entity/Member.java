package com.fiveguys.koguma.data.entity;

import com.fiveguys.koguma.data.dto.MemberDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "members")
@NoArgsConstructor()

public class Member extends BaseTime{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name="profile_url")
    private String profileURL;
    @Column(name = "pw")
    private String pw;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "score", nullable = false)
    private Float score;

    @Column(name = "role_flag")
    private Boolean roleFlag;

    @Column(name = "social_flag")
    private Boolean socialFlag;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "payment_account")
    private String paymentAccount;

    @Column(name = "payment_bank")
    private String paymentBank;

    @Column(name = "payment_balance")
    private Integer paymentBalance;

    @Column(name = "payment_pw")
    private String paymentPw;

    @Column(name = "active_flag")
    private Boolean activeFlag;

    @Enumerated(EnumType.STRING)
    @Column(name= "member_role_type")
    private MemberRoleType memberRoleType;


    @Builder
    public Member(Long id, String nickname, String profileURL, String pw, String phone, Float score, Boolean roleFlag, Boolean socialFlag, String email, String paymentAccount, String paymentBank, Integer paymentBalance, String paymentPw, Boolean activeFlag, MemberRoleType memberRoleType) {
        this.id = id;
        this.nickname = nickname;
        this.profileURL = profileURL;
        this.pw = pw;
        this.phone = phone;
        this.score = score;
        this.roleFlag = roleFlag;
        this.socialFlag = socialFlag;
        this.email = email;
        this.paymentAccount = paymentAccount;
        this.paymentBank = paymentBank;
        this.paymentBalance = paymentBalance;
        this.paymentPw = paymentPw;
        this.activeFlag = activeFlag;
        this.memberRoleType = memberRoleType;
    }




    public void updateEmail(String email){
        this.email=email;
    }
    public void updateSocialFlag(boolean socialFlag){
        this.socialFlag = socialFlag;
    }
    public void updateMemberRole(MemberRoleType memberRoleType){
        this.memberRoleType = memberRoleType;
    }
}