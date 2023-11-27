package com.fiveguys.koguma.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "payment_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHistory extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_type", nullable = false)
    private String paymentType;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "info", nullable = false, length = 90)
    private String info;
    @ManyToOne
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    // Constructors, getters, and setters
}
