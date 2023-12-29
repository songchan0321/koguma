package com.fiveguys.koguma.data.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "category_name", length = 15)
    private String categoryName;

    @Column(nullable = false, length = 90)
    private String title;

    @Column(nullable = false, length = 1500)
    private String content;

    @Column(name = "post_type",nullable = false)
    private Boolean postType;

    private Double latitude;

    private Double longitude;

    @Column(nullable = false, length = 20)
    private String dong;

    @Column(nullable = false)
    private int views = 0;

    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag = true;

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Image> image = new ArrayList<>();


    public void increaseViews(int views){
        this.views++;
    }

    @Builder
    public Post(Long id, Member member, Category category, String categoryName,
                String title, String content, Boolean postType,  LocalDateTime regDate, Double latitude, Double longitude, String dong,
                int views, Boolean activeFlag, List<Image> image){
        this.id = id;
        this.member = member;
        this.category = category;
        this.categoryName = categoryName;
        this.title = title;
        this.content = content;
        this.postType = postType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dong = dong;
        this.views = views;
        this.regDate = regDate;
        this.activeFlag = activeFlag;
        this.image = image;
    }

    public void setCategory(Category category) {

        this.category = category;
    }

    public void setActiveFlag(boolean b) {

        this.activeFlag = b;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
