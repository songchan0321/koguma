package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Category;
import com.fiveguys.koguma.data.entity.CategoryType;
import lombok.Builder;
import lombok.Data;

@Data
public class CategoryDTO {

    private Long id;
    private String name;
    private CategoryType categoryType;

    @Builder
    public CategoryDTO(Long id, String name, CategoryType categoryType){
        this.id = id;
        this.name = name;
        this.categoryType = categoryType;
    }

    public Category toEntity(){
        return Category.builder()
                .id(this.id)
                .categoryName(name)
                .categoryType(categoryType)
                .build();
    }

    public static CategoryDTO fromDTO(Category entity){
        return CategoryDTO.builder()
                .id(entity.getId())
                .name(entity.getCategoryName())
                .categoryType(entity.getCategoryType())
                .build();
    }

}
