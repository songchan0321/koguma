package com.fiveguys.koguma.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private Long sellerId;
    private Long buyerId;
    private Long categoryId;
    private String title;
    private String content;
    private int price;
    private char tradeStatus;
    private String dong;
    private Double latitude;
    private Double longitude;
    private int views;
    private String categoryName;
    private Boolean activeFlag;
    private LocalDateTime buy_date;

}
