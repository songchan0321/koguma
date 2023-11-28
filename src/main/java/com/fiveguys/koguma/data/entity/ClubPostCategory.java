package com.fiveguys.koguma.data.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "club_post_categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubPostCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Column(name = "category_name", nullable = false, length = 15)
    private String name;

    @Builder
    public ClubPostCategory(Long id, Club club, String name){
        this.id = id;
        this.club = club;
        this.name = name;
    }
}
