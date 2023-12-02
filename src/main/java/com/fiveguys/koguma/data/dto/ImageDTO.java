package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Club;
import com.fiveguys.koguma.data.entity.Image;
import com.fiveguys.koguma.data.entity.ImageType;
import com.fiveguys.koguma.data.entity.Product;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
public class ImageDTO {
    private Long id;
    private String URL;
    private ImageType imageType;
    private Boolean repImageFlag;
    private Boolean activeFlag;
    private ProductDTO productDTO;
    private ClubDTO clubDTO;
    private PostDTO postDTO;
    private Long messageId;
    private LocalDateTime regDate;

    @Builder
    public ImageDTO(Long id, String URL, ImageType imageType, Boolean repImageFlag, Boolean activeFlag, ProductDTO productDTO, ClubDTO clubDTO, PostDTO postDTO, Long messageId, LocalDateTime regDate) {
        this.id = id;
        this.URL = URL;
        this.imageType = imageType;
        this.repImageFlag = repImageFlag;
        this.activeFlag = activeFlag;
        this.productDTO = productDTO;
        this.clubDTO = clubDTO;
        this.postDTO = postDTO;
        this.messageId = messageId;
        this.regDate = regDate;
    }




    public static ImageDTO fromEntity(Image image){
        return ImageDTO.builder()
                .id(image.getId())
                .URL(image.getURL())
                .clubDTO(ClubDTO.fromEntity(image.getClub()))
                .productDTO(ProductDTO.fromEntity(image.getProduct()))
                .activeFlag(image.getActiveFlag())
                .imageType(image.getImageType())
                .postDTO(PostDTO.fromEntity(image.getPost()))
                .repImageFlag(image.getRepImageFlag())
                .regDate(image.getRegDate())
                .build();
    }

    public Image toEntity(){
        return Image.builder()
                .id(id)
                .URL(URL)
                .imageType(imageType)
                .club(clubDTO.toEntity())
                .activeFlag(activeFlag)
                .repImageFlag(repImageFlag)
                .post(postDTO.toEntity())
                .product(productDTO.toEntity())
                .build();
    }

}
