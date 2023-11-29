package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.ClubPostCategoryDTO;
import com.fiveguys.koguma.repository.club.ClubPostCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubPostCategoryImpl implements ClubPostCategoryService{

    private final ClubPostCategoryRepository clubPostCategoryRepository;

    @Override
    public Long addClubPostCategory(ClubPostCategoryDTO clubPostCategoryDTO) {
        return null;
    }

    @Override
    public List<ClubPostCategoryDTO> listClubPostCategories(Long clubId) {
        return null;
    }

    @Override
    public ClubPostCategoryDTO getClubPostCategory(Long categoryId) {
        return null;
    }
}
