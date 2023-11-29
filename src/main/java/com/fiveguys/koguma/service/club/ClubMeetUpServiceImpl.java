package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.ClubDTO;
import com.fiveguys.koguma.data.dto.ClubMeetUpDTO;
import com.fiveguys.koguma.data.entity.ClubMeetUp;
import com.fiveguys.koguma.repository.club.ClubMeetUpRepository;
import com.fiveguys.koguma.repository.club.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubMeetUpServiceImpl implements ClubMeetUpService{

    private final ClubService clubService;

    private final ClubMeetUpRepository clubMeetUpRepository;
    private final ClubRepository clubRepository;


    @Override
    public Long addClubMeetUp(ClubMeetUpDTO clubMeetUpDTO, Long clubId) {

        //일정을 생성하는 모임 조회
        ClubDTO clubDTO = clubService.getClub(clubId);

        clubMeetUpDTO.setClubDTO(clubDTO);
        ClubMeetUp cmu = clubMeetUpDTO.toEntity();

        return  clubMeetUpRepository.save(cmu).getId();
    }

    @Override
    public ClubMeetUpDTO getClubMeetUp(Long clubMeetUpId) {

        ClubMeetUp clubMeetUp = clubMeetUpRepository.findById(clubMeetUpId)
                .orElseThrow(()-> new IllegalArgumentException("모임 일정이 없습니다."));

        return ClubMeetUpDTO.fromEntity(clubMeetUp);
    }

    @Override
    public List<ClubMeetUpDTO> listClubMeetUp(Long clubId) {

        return clubMeetUpRepository.findByClubId(clubId).stream()
                .map((c)-> ClubMeetUpDTO.fromEntity(c))
                .collect(Collectors.toList());
    }

    @Override
    public void updateClubMeetUp(ClubMeetUpDTO cmuDTO) {
        //일정 조회
        ClubMeetUp clubMeetUp = clubMeetUpRepository.findById(cmuDTO.getId())
                .orElseThrow(()-> new IllegalArgumentException("모임 일정이 없습니다."));

        //일정 수정
        clubMeetUp.updateClubMeetUp(cmuDTO.getTitle(), cmuDTO.getContent(), cmuDTO.getMaxCapacity(),
                cmuDTO.getMeetDate(), cmuDTO.getRoadAddr());

    }

    @Override
    public void deleteClubMeetUp(Long clubMeetUpId) {

    }

    @Override
    public Long joinClubMeetUp(Long clubMeetUpId, Long clubMemberId) {




        return null;
    }
}
