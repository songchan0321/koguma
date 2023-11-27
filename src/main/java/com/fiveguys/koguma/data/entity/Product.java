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
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTime {

    @Id
    @Column(nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @Column(nullable = false)
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Member buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @Column(nullable = false)
    private Category category;
    @Column(nullable = false,length=90)
    private String title;
    @Column(nullable = false,length=300)
    private String content;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private char trade_status;
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
    private String category_name;
    @Column(nullable = false)
    private Boolean active_flag;
    private LocalDateTime buy_date;

    public Product(Long id, Member seller, Member buyer, Category category, String title, String content, int price, char trade_status, String dong, Double latitude, Double longitude, int views, String category_name, Boolean active_flag, LocalDateTime buy_date) {
        this.id = id;
        this.seller = seller;
        this.buyer = buyer;
        this.category = category;
        this.title = title;
        this.content = content;
        this.price = price;
        this.trade_status = trade_status;
        this.dong = dong;
        this.latitude = latitude;
        this.longitude = longitude;
        this.views = views;
        this.category_name = category_name;
        this.active_flag = active_flag;
        this.buy_date = buy_date;
    }
}
