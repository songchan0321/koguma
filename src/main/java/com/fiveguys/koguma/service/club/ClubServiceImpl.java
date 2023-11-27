package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.ClubDTO;
import com.fiveguys.koguma.data.dto.ClubMemberDTO;
import com.fiveguys.koguma.repository.club.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @Transactional
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService{

    private final ClubRepository clubRepository;
    @Override
    public Long addClub(ClubDTO clubDTO, Long memberId) {
        return null;
    }

    @Override
    public List<ClubDTO> listClub() {
        return null;
    }

    @Override
    public List<ClubDTO> listMyClub(Long memberId) {
        return null;
    }

    @Override
    public ClubDTO getClub(Long clubId) {
        return null;
    }

    @Override
    public void updateClub(ClubDTO clubDTO) {

    }

    @Override
    public Long addClubMember(ClubMemberDTO clubMemberDTO) {
        return null;
    }


}
