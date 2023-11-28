package com.fiveguys.koguma.data.entity;

import com.fiveguys.koguma.data.dto.MemberDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "members")
@NoArgsConstructor()

public class Member {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "pw")
    private Long pw;

    @Column(name = "phone", nullable = false)
    private Number phone;

    @Column(name = "score", nullable = false)
    private Float score;

    @Column(name = "role_flag")
    private Boolean roleFlag;

    @Column(name = "social_flag")
    private Boolean socialFlag;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "payment_account", nullable = false)
    private String paymentAccount;

    @Column(name = "payment_bank", nullable = false)
    private String paymentBank;

    @Column(name = "payment_balance", nullable = false)
    private Integer paymentBalance;

    @Column(name = "payment_pw", nullable = false)
    private String paymentPw;

    @Column(name = "active_flag")
    private Boolean activeFlag;


    @Builder
    public Member (String nickname, String email, Long imageId, Boolean roleFlag, Long pw, Number phone, Float score, Boolean socialFlag, String paymentAccount, String paymentPw, String paymentBank, Integer paymentBalance, Boolean activeFlag) {
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


}