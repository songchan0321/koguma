package com.fiveguys.koguma.data.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseTime{

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 2083)
    private String URL;

    @Enumerated
    @Column(name = "img_type", nullable = false)
    private ImageType imageType;

    private Boolean regImageFlag;
    private Boolean activeFlag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Builder
    public Image(Long id, String URL, ImageType imageType, Boolean regImageFlag, Boolean activeFlag, Product product, Club club) {
        this.id = id;
        this.URL = URL;
        this.imageType = imageType;
        this.regImageFlag = regImageFlag;
        this.activeFlag = activeFlag;
        this.product = product;
        this.club = club;
    }
}
