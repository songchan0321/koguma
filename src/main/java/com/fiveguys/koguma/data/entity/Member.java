package com.fiveguys.koguma.data.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import javax.persistence.*;

@Data
@Entity
public class Member {
    public Member() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column()
    private String imageId;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Float score;

    @Column()
    private Boolean roleFlag;

    @Column()
    private Boolean socialFlag;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String paymentAccount;

    @Column(nullable = false)
    private String paymentBank;

    @Column(nullable = false)
    private Integer paymentBalance;

    @Column(nullable = false)
    private String paymentPw;

    @Column()
    private Boolean activeFlag;


    @Builder
    public Member(String nickname, String email, String imageId, Boolean roleFlag, String pw, String phone, Float score, Boolean socialFlag, String paymentAccount, String paymentPw, String paymentBank, Integer paymentBalance, Boolean activeFlag) {
        this.nickname = nickname;
        this.email = email;
        this.imageId = imageId;
        this.roleFlag = roleFlag;
        this.activeFlag = activeFlag;
        this.pw = pw;
        this.nickname = nickname;
        this.paymentAccount = paymentAccount;
        this.paymentBalance = paymentBalance;
        this.paymentBank = paymentBank;
        this.paymentPw = paymentPw;
        this.phone = phone;
        this.score = score;
        this.socialFlag = socialFlag;
    }
    public Member update(String nickname, String imageId) {
        this.nickname = nickname;
        this.imageId = imageId;

        return this;
    }

}