package com.fiveguys.koguma.data.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "categoriese")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType categoryType;
}
