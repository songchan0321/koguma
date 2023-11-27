package com.fiveguys.koguma.data.entity;

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
    private boolean joinActiveFlag;

    @Column(nullable = false)
    private boolean activeFlag;

    @Builder
    public Club(String title, String content, Integer maxCapacity){
        this.title = title;
        this.content = content;
        this.maxCapacity = maxCapacity;
    }
}
