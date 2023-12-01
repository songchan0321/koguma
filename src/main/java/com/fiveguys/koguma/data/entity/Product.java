package com.fiveguys.koguma.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter

@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id",nullable = false)
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Member buyer;
    @Column(name = "category_id",nullable = false)
    private Long categoryId;
    @Column(nullable = false,length=90)
    private String title;
    @Column(nullable = false,length=300)
    private String content;

    @Column(nullable = false)
    private int price;

    @Column(name = "trade_status",nullable = false)
    private ProductStateType tradeStatus;
    @Column(nullable = false,length=20)
    private String dong;
    @Column(nullable = false)
    private Double latitude;
    @Column(nullable = false)
    private Double longitude;
    @Column(nullable = false)
    @ColumnDefault("0")
    private int views;
    @Column(name = "category_name",nullable = false,length=15)
    private String categoryName;
    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag;
    @Column(name = "buy_date")
    private LocalDateTime buyDate;

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;
    @PrePersist
    public void onPrePersist() {
        String customLocalDateTimeFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime parsedCreateDate = LocalDateTime.parse(customLocalDateTimeFormat, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.regDate = parsedCreateDate;
    }

    @Builder
    public Product(Long id, Member seller, Member buyer, Long categoryId, String title, String content, int price, ProductStateType tradeStatus, String dong, Double latitude, Double longitude, int views, String categoryName, Boolean activeFlag, LocalDateTime buyDate, LocalDateTime regDate) {
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
        this.regDate = regDate;
    }





    public void appendView(int views){
        this.views = views+1;
    }
    public void resetRegDate(){
        String customLocalDateTimeFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime parsedCreateDate = LocalDateTime.parse(customLocalDateTimeFormat, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.regDate = parsedCreateDate;
    }
}
