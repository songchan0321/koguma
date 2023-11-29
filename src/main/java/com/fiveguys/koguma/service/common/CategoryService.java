package com.fiveguys.koguma.service.common;


import com.fiveguys.koguma.data.dto.CategoryDTO;
import com.fiveguys.koguma.data.entity.CategoryType;

import java.util.List;

public interface CategoryService {

    public CategoryDTO getCategory();

    public List<CategoryDTO> listCategory(CategoryType categoryType);

}
