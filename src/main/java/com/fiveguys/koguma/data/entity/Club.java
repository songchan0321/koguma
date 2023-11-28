package com.fiveguys.koguma.data.entity;

import com.fiveguys.koguma.data.dto.ClubDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "clubs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseTime{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 60)
    private String title;

    @Column(nullable = false, length = 900)
    private String content;

    @Column(nullable = false)
    private Integer maxCapacity;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false,length=20)
    private String dong;

    @Column(nullable = false)
    private boolean joinActiveFlag;

    @Column(nullable = false)
    private boolean activeFlag;

    @Builder
    public Club(Long id,String title, String content, Integer maxCapacity){
        this.title = title;
        this.content = content;
        this.maxCapacity = maxCapacity;
    }

        /*
         * 모임 업데이트 로직
         * */
        public void updateClub(ClubDTO clubDTO){
            this.title = clubDTO.getTitle();
            this.content = clubDTO.getContent();
            this.maxCapacity = clubDTO.getMaxCapacity();
    }


}
