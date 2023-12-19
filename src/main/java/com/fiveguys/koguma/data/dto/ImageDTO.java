package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.*;
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
    private MemberDTO memberDTO;
    private Long messageId;
    private LocalDateTime regDate;

    @Builder
    public ImageDTO(Long id, String URL, ImageType imageType, Boolean repImageFlag, Boolean activeFlag, ProductDTO productDTO, ClubDTO clubDTO, PostDTO postDTO, MemberDTO memberDTO, Long messageId, LocalDateTime regDate) {
        this.id = id;
        this.URL = URL;
        this.imageType = imageType;
        this.repImageFlag = repImageFlag;
        this.activeFlag = activeFlag;
        this.productDTO = productDTO;
        this.clubDTO = clubDTO;
        this.postDTO = postDTO;
        this.memberDTO = memberDTO;
        this.messageId = messageId;
        this.regDate = regDate;
    }






    public static ImageDTO fromEntity(Image image){
        ImageDTO.ImageDTOBuilder builder = ImageDTO.builder()
                .id(image.getId())
                .URL(image.getURL())
                .repImageFlag(image.getRepImageFlag())
                .imageType(image.getImageType());
        if (image.getClub() != null) {
            builder.clubDTO(ClubDTO.fromEntity(image.getClub()));
        }
        if (image.getPost() != null) {
            builder.postDTO(PostDTO.fromEntity(image.getPost()));
        }
        if (image.getProduct() != null) {
            builder.productDTO(ProductDTO.fromEntity(image.getProduct()));
        }
        if(image.getActiveFlag()!= null){
            builder.activeFlag(image.getActiveFlag());
        }
        if(image.getMember()!=null){
            builder.memberDTO(MemberDTO.fromEntity(image.getMember()));
        }

        return builder.build();


    }

    public Image toEntity(){

        Image.ImageBuilder builder = Image.builder()
                .id(id)
                .URL(URL)
                .repImageFlag(repImageFlag)
                .activeFlag(true)
                .imageType(imageType);
        if (clubDTO != null) {
            builder.club(clubDTO.toEntity());
        }
        if (postDTO != null) {
            builder.post(postDTO.toEntity());
        }
        if (productDTO != null) {
            builder.product(productDTO.toEntity());
        }
        if (activeFlag != null){
            builder.activeFlag(activeFlag);
        }
        if(memberDTO!= null){
            builder.member(memberDTO.toEntity());
        }


        return builder.build();
    }

}
