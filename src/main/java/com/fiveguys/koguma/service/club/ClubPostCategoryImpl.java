package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.ClubPostCategoryDTO;
import com.fiveguys.koguma.data.entity.Club;
import com.fiveguys.koguma.data.entity.ClubMember;
import com.fiveguys.koguma.data.entity.ClubPostCategory;
import com.fiveguys.koguma.repository.club.ClubMemberRepository;
import com.fiveguys.koguma.repository.club.ClubPostCategoryRepository;
import com.fiveguys.koguma.repository.club.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubPostCategoryImpl implements ClubPostCategoryService{

    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubPostCategoryRepository clubPostCategoryRepository;

    @Override
    public Long addClubPostCategory(Long clubId, Long clubMemberId, String categoryName) {

        Club findClub = clubRepository.findById(clubId).orElseThrow();

        ClubMember clubMember = clubMemberRepository.findById(clubMemberId).orElseThrow();

        if(!clubMember.getMemberRole()){
            throw new IllegalStateException("모임장이 아닙니다 ");
        }

        if(findClub != clubMember.getClub()){
            throw new IllegalStateException("같은 모임이 아닙니다. ");
        }

        ClubPostCategory clubPostCategory = ClubPostCategory.builder()
                .club(findClub)
                .name(categoryName)
                .build();

        return clubPostCategoryRepository.save(clubPostCategory).getId();
    }

    @Override
    public List<ClubPostCategoryDTO> listClubPostCategories(Long clubId) {

        List<ClubPostCategory> clubCategories = clubPostCategoryRepository.findByClubId(clubId);

        return clubCategories.stream()
                .map((c)-> ClubPostCategoryDTO.fromEntity(c))
                .collect(Collectors.toList());
    }

    @Override
    public ClubPostCategoryDTO getClubPostCategory(Long categoryId) {

        ClubPostCategory clubPostCategory = clubPostCategoryRepository.findById(categoryId)
                .orElseThrow();

        return ClubPostCategoryDTO.fromEntity(clubPostCategory);
    }
}
