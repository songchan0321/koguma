package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.*;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
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
    private List<String> images;
    private List<ImageDTO> imageDTO;
    private int likeCount;
    private int chatroomCount;


    @Builder
    public ProductDTO(Long id, MemberDTO sellerDTO, MemberDTO buyerDTO, Long categoryId, String title, String content, int price, ProductStateType tradeStatus, String dong, Double latitude, Double longitude, int views, String categoryName, Boolean activeFlag, LocalDateTime regDate, LocalDateTime buyDate, List<String> images, List<ImageDTO> imageDTO,  int likeCount, int chatroomCount) {
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
        this.images = images;
        this.imageDTO = imageDTO;
        this.likeCount = likeCount;
        this.chatroomCount = chatroomCount;
    }









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
                .regDate(regDate)
                .categoryName(categoryName)
                .buyDate(buyDate);
        if (buyerDTO != null) {
            builder.buyer(buyerDTO.toEntity());
        }
        if (activeFlag != null){
            builder.activeFlag(activeFlag);
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
                .regDate(product.getRegDate())
                .buyDate(product.getBuyDate());

        if (product.getBuyer() != null) {
            builder.buyerDTO(MemberDTO.fromEntity(product.getBuyer()));
        }
        if (product.getActiveFlag() != null){
            builder.activeFlag(product.getActiveFlag());
        }

        return builder.build();
    }
    public static ProductDTO fromEntityContainImage(Product product) {
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
                .regDate(product.getRegDate())
                .buyDate(product.getBuyDate());

        if (product.getBuyer() != null) {
            builder.buyerDTO(MemberDTO.fromEntity(product.getBuyer()));
        }
        if (product.getActiveFlag() != null){
            builder.activeFlag(product.getActiveFlag());
        }
        if (product.getImage()!= null){
            builder.imageDTO(product.getImage().stream().map((ImageDTO::fromEntity)).collect(Collectors.toList()));
        }
        if (product.getLikeCount()!= null){
            builder.likeCount(product.getLikeCount().size());
        }

        return builder.build();
    }
}
