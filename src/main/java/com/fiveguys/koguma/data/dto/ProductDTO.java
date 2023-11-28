package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Location;
import com.fiveguys.koguma.data.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private MemberDTO sellerDTO;
    private MemberDTO buyerDTO;
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
    private LocalDateTime buyDate;

    @Builder
    public ProductDTO(Long id, MemberDTO sellerDTO, MemberDTO buyerDTO, Long categoryId, String title, String content, int price, char tradeStatus, String dong, Double latitude, Double longitude, int views, String categoryName, Boolean activeFlag, LocalDateTime buyDate) {
        this.id = id;
        this.sellerDTO = sellerDTO;
        this.buyerDTO = buyerDTO;
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

    public Product toEntity(){
        return Product.builder()
                .id(id)
//                .seller(sellerDTO.toEntity())
//                .buyer(buyerDTO.toEntity())
                .categoryId(categoryId)
                .title(title)
                .content(content)
                .price(price)
                .tradeStatus(tradeStatus)
                .dong(dong)
                .latitude(latitude)
                .longitude(longitude)
                .views(views)
                .categoryName(categoryName)
                .activeFlag(activeFlag)
                .buyDate(buyDate)
                .build();
    }
    public static ProductDTO fromEntity(Product product){
        return ProductDTO.builder()
                .id(product.getId())
//                .buyerDTO(product.getBuyer().toDTO())
//                .sellerDTO(product.getSeller().toDTO())
                .categoryId(product.getCategoryId())
                .title(product.getTitle())
                .content(product.getContent())
                .price(product.getPrice())
                .tradeStatus(product.getTradeStatus())
                .dong(product.getDong())
                .latitude(product.getLatitude())
                .longitude(product.getLongitude())
                .views(product.getViews())
                .categoryName(product.getCategoryName())
                .activeFlag(product.getActiveFlag())
                .buyDate(product.getBuyDate())
                .build();
    }
}
