package com.fiveguys.koguma.data.entity;

import com.fiveguys.koguma.data.dto.ClubDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "clubs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Club extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String title;

    @Column(nullable = false, length = 900)
    private String content;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false,length=20)
    private String dong;

    @Column(name = "join_active_flag", nullable = false)
    private boolean joinActiveFlag;

    @Column(name = "active_flag", nullable = false)
    private boolean activeFlag;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Club(Long id,String title, String content, Integer maxCapacity,
                Double latitude, Double longitude, String dong, Boolean joinActiveFlag, Boolean activeFlag,
                Category category, String categoryName){
        this.id = id;
        this.title = title;
        this.content = content;
        this.maxCapacity = maxCapacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dong = dong;
        this.joinActiveFlag = joinActiveFlag;
        this.activeFlag = activeFlag;
        this.categoryName = categoryName;
        this.category = category;
    }

        /*
         * 모임 업데이트 로직
         * */
        public void updateClub(ClubDTO clubDTO){
            this.title = clubDTO.getTitle();
            this.content = clubDTO.getContent();
            this.maxCapacity = clubDTO.getMaxCapacity();
            this.category = clubDTO.getCategoryDTO().toEntity();
    }

    public static Club createClub(String title, String content, Integer maxCapacity,
                                  Double latitude, Double longitude, String dong,
                                  String categoryName, Category category){

            return Club.builder()
                    .title(title)
                    .content(content)
                    .maxCapacity(maxCapacity)
                    .latitude(latitude)
                    .longitude(longitude)
                    .dong(dong)
                    .joinActiveFlag(true)
                    .activeFlag(true)
                    .categoryName(categoryName)
                    .category(category)
                    .build();

    }


}
