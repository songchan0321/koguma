package com.fiveguys.koguma.data.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter

@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTime {

    @Id
    @Column(nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id",nullable = false)
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Member buyer;
    @Column(nullable = false)
    private Long categoryId;
    @Column(nullable = false,length=90)
    private String title;
    @Column(nullable = false,length=300)
    private String content;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private char tradeStatus;
    @Column(nullable = false,length=20)
    private String dong;
    @Column(nullable = false)
    private Double latitude;
    @Column(nullable = false)
    private Double longitude;
    @Column(nullable = false)
    @ColumnDefault("0")
    private int views;
    @Column(nullable = false,length=15)
    private String categoryName;
    @Column(nullable = false)
    private Boolean activeFlag;
    private LocalDateTime buyDate;

    @Builder
    public Product(Long id, Member seller, Member buyer, Long categoryId, String title, String content, int price, char tradeStatus, String dong, Double latitude, Double longitude, int views, String categoryName, Boolean activeFlag, LocalDateTime buyDate) {
        this.id = id;
        this.seller = seller;
        this.buyer = buyer;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.price = price;
        this.tradeStatus = tradeStatus;
        this.dong = dong;
        this.latitude = latitude;
        this.longitude = longitude;
        this.views = views;
        this.categoryName = categoryName;
        this.activeFlag = activeFlag;
        this.buyDate = buyDate;
    }
}
