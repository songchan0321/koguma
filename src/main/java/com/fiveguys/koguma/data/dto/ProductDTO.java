package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Location;
import com.fiveguys.koguma.data.entity.Product;
import com.fiveguys.koguma.data.entity.ProductStateType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Data
public class ProductDTO {
    private Long id;
    private MemberDTO sellerDTO;
    private MemberDTO buyerDTO;
    private Long categoryId;
    private String title;
    private String content;
    private int price;
    private ProductStateType tradeStatus;
    private String dong;
    private Double latitude;
    private Double longitude;
    private int views;
    private String categoryName;
    private Boolean activeFlag;
    private LocalDateTime regDate;
    private LocalDateTime buyDate;


    public ProductDTO(Long id, MemberDTO sellerDTO, MemberDTO buyerDTO, Long categoryId, String title, String content, int price, ProductStateType tradeStatus, String dong, Double latitude, Double longitude, int views, String categoryName, Boolean activeFlag, LocalDateTime regDate, LocalDateTime buyDate) {
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
        this.regDate = regDate;
        this.buyDate = buyDate;
    }

    @Builder


    public Product toEntity(){
        Product.ProductBuilder builder = Product.builder()
                .id(id)
                .seller(sellerDTO.toEntity())
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
                .buyDate(buyDate);
        if (buyerDTO != null) {
            builder.buyer(buyerDTO.toEntity());
        }

        return builder.build();
    }
    public static ProductDTO fromEntity(Product product) {
        ProductDTO.ProductDTOBuilder builder = ProductDTO.builder()
                .id(product.getId())
                .sellerDTO(MemberDTO.fromEntity(product.getSeller()))
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
                .regDate(product.getRegDate())
                .buyDate(product.getBuyDate());

        if (product.getBuyer() != null) {
            builder = builder.buyerDTO(MemberDTO.fromEntity(product.getBuyer()));
        }

        return builder.build();
    }

}
