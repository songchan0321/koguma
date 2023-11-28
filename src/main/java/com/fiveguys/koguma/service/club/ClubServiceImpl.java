package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.ClubDTO;
import com.fiveguys.koguma.data.dto.ClubMemberDTO;
import com.fiveguys.koguma.data.entity.Club;
import com.fiveguys.koguma.repository.club.ClubMemberRepository;
import com.fiveguys.koguma.repository.club.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @Transactional
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService{

    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;


    @Override
    public Long addClub(ClubDTO clubDTO, Long memberId) {

        Club club = clubDTO.toEntity();

        //모임 생성 등록
        Club clubId = clubRepository.save(club);

        return club.getId();
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

        // 모임 조회
        Club club =clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모임이 없습니다."));

        // DTO -> Entity return
        return ClubDTO.fromDTO(club);
    }

    @Override
    public void updateClub(ClubDTO clubDTO) {

        // 모임 정보 찾기
        Club club = clubRepository.findById(clubDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 클럽 없습니다."));

        // 모임 업데이트 저장
        club.updateClub(clubDTO);

    }

    @Override
    public Long addClubMember(ClubMemberDTO clubMemberDTO) {
        return null;
    }


}
