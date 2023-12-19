package com.fiveguys.koguma.data.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "commet")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Commet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "review_member_id", referencedColumnName = "member_id"),
            @JoinColumn(name = "review_product_id", referencedColumnName = "product_id")
    })
    private Review review;
    private String commet;

    @Builder
    public Commet(Long id, String commet,Review review) {
        this.id = id;
        this.review = review;
        this.commet = commet;
    }
}
