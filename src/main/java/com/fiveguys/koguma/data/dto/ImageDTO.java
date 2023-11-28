package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Club;
import com.fiveguys.koguma.data.entity.Image;
import com.fiveguys.koguma.data.entity.ImageType;
import com.fiveguys.koguma.data.entity.Product;
import lombok.*;

import javax.persistence.*;


@Data
public class ImageDTO {
    private Long id;
    private String URL;
    private ImageType imageType;
    private Boolean regImageFlag;
    private Boolean activeFlag;
    private ProductDTO productDTO;
    private ClubDTO clubDTO;

    @Builder
    public ImageDTO(Long id, String URL, ImageType imageType, Boolean regImageFlag, Boolean activeFlag, ProductDTO productDTO, ClubDTO clubDTO) {
        this.id = id;
        this.URL = URL;
        this.imageType = imageType;
        this.regImageFlag = regImageFlag;
        this.activeFlag = activeFlag;
        this.productDTO = productDTO;
        this.clubDTO = clubDTO;
    }
    public static ImageDTO fromEntity(Image image){
        return ImageDTO.builder()
                .id(image.getId())
                .URL(image.getURL())
                .clubDTO(ClubDTO.fromEntity(image.getClub()))
                .productDTO(ProductDTO.fromEntity(image.getProduct()))
                .activeFlag(image.getActiveFlag())
                .imageType(image.getImageType())
                .regImageFlag(image.getRegImageFlag())
                .build();
    }

    public Image toEntity(){
        return Image.builder()
                .id(id)
                .URL(URL)
                .imageType(imageType)
                .club(clubDTO.toEntity())
                .activeFlag(activeFlag)
                .regImageFlag(regImageFlag)
                .product(productDTO.toEntity())
                .build();
    }

}
