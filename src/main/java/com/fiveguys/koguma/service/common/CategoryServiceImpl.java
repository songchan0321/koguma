package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.CategoryDTO;
import com.fiveguys.koguma.data.entity.Category;
import com.fiveguys.koguma.data.entity.CategoryType;
import com.fiveguys.koguma.repository.common.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service @Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDTO getCategory(Long id) {
        return CategoryDTO.fromDTO(categoryRepository.findById(id).get());
    }

    @Override
    public List<CategoryDTO> listCategory(CategoryType categoryType) {
        List<Category> categories = categoryRepository.findAll(CategorySpecifications.hasType(categoryType));

        return  categories.stream()
                .map((c) -> new CategoryDTO(c))
                .collect(Collectors.toList());
    }
}
