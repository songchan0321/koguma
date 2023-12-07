package com.fiveguys.koguma.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "payment_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHistory extends BaseTime {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "payment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentHistoryType type;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "info", nullable = false, length = 90)
    private String info;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // Constructors, getters, and setters
}
