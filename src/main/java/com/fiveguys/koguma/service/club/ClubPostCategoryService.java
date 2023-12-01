package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.ClubPostCategoryDTO;

import java.util.List;

public interface ClubPostCategoryService {

    public Long addClubPostCategory(Long clubId, Long clubMemberId, String categoryName);

    public List<ClubPostCategoryDTO> listClubPostCategories(Long clubId);

    public ClubPostCategoryDTO getClubPostCategory(Long categoryId);

}
