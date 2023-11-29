package com.fiveguys.koguma.data.entity;


import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chatrooms")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chatroom extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Member buyer;
    @Column(name = "price")
    private Integer price;
    @Column(name = "seller_enter_date")
    private LocalDateTime sellerEnterDate;
    @Column(name = "buyer_enter_date")
    private LocalDateTime buyerEnterDate;
    @Column(name = "active_flag")
    private Boolean activeFlag;
}
