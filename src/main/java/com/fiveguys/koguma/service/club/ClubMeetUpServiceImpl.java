package com.fiveguys.koguma.service.club;

import com.fiveguys.koguma.data.dto.ClubDTO;
import com.fiveguys.koguma.data.dto.ClubMeetUpDTO;
import com.fiveguys.koguma.data.dto.ClubMemberMeetUpJoinDTO;
import com.fiveguys.koguma.data.entity.ClubMeetUp;
import com.fiveguys.koguma.data.entity.ClubMember;
import com.fiveguys.koguma.data.entity.ClubMemberMeetUpJoin;
import com.fiveguys.koguma.repository.club.ClubMeetUpRepository;
import com.fiveguys.koguma.repository.club.ClubMemberMeetUpJoinRepository;
import com.fiveguys.koguma.repository.club.ClubMemberRepository;
import com.fiveguys.koguma.repository.club.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubMeetUpServiceImpl implements ClubMeetUpService{

    private final ClubService clubService;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubMeetUpRepository clubMeetUpRepository;
    private final ClubMemberMeetUpJoinRepository clubMemberMeetUpJoinRepository;



    @Override
    public Long addClubMeetUp(ClubMeetUpDTO clubMeetUpDTO, Long clubId) {


        Long meetUpCounts = clubMeetUpRepository.countActiveMeetUpsByClubId(clubId);

        // todo : 3  나중에 변경되는 값, 이라면 properties 별도 관리 필요
        if (meetUpCounts >= 3){
            throw new IllegalStateException("종료되지 않은 일정이 3개 이상");
        }

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
    public void updateClubMeetUp(Long meetUpId,ClubMeetUpDTO cmuDTO) {
        //일정 조회
        ClubMeetUp clubMeetUp = clubMeetUpRepository.findById(meetUpId)
                .orElseThrow(()-> new IllegalArgumentException("모임 일정이 없습니다."));

        //일정 수정
        clubMeetUp.updateClubMeetUp(cmuDTO.getTitle(), cmuDTO.getContent(), cmuDTO.getMaxCapacity(),
                cmuDTO.getMeetDate(), cmuDTO.getRoadAddr());

    }

    @Override
    public void deleteClubMeetUp(Long leaderId,Long clubMeetUpId) {
        clubMeetUpRepository.deleteById(clubMeetUpId);
    }

    @Override
    public Long joinClubMeetUp(Long clubMeetUpId, Long clubMemberId) {

        ClubMember clubMember = clubMemberRepository.findById(clubMemberId)
                .orElseThrow(() -> new IllegalArgumentException("모임원이 없습니다."));

        ClubMeetUp clubMeetUp = clubMeetUpRepository.findById(clubMeetUpId)
                .orElseThrow(() -> new IllegalArgumentException("모임 일정이 없습니다."));

        ClubMemberMeetUpJoin clubMemberMeetUpJoin = ClubMemberMeetUpJoin.createClubMemberMeetUpJoin(clubMeetUp, clubMember);

        clubMemberMeetUpJoinRepository.save(clubMemberMeetUpJoin);

        return clubMemberMeetUpJoin.getId();
    }

    @Override
    public void cancel(Long meetUpId, Long clubMemberId) {

        ClubMemberMeetUpJoin joinMember = clubMemberMeetUpJoinRepository.findByMeetUpJoinMember(meetUpId, clubMemberId);

        joinMember.joinCancel();
    }

    @Override
    public List<ClubMemberMeetUpJoinDTO> listClubMeetUpMember(Long meetUpId) {

        List<ClubMemberMeetUpJoin> joinMembers = clubMemberMeetUpJoinRepository.findAllByClubMeetUpId(meetUpId);

        return joinMembers.stream()
                .map((j)-> ClubMemberMeetUpJoinDTO.fromEntity(j))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMeetupJoinMember(Long meetUpId, Long joinMemberId, Long leaderId) {

    }

    @Override
    @Scheduled(cron = "0 20 20 * * * ")
    public void changeMeetUpState() {

        // 메서드 실행 날짜
        LocalDateTime endDate = LocalDateTime.now();

        //종료가 필요한 일정 조회
        Boolean status = true;
        List<ClubMeetUp> endMeetUps = clubMeetUpRepository.findByChangeState(endDate, status);

        //상태 종료 처리
        for (ClubMeetUp endMeetUp : endMeetUps) {
            System.out.println("endMeetUp = " + endMeetUp.getTitle());
            endMeetUp.changeActiveFlag();
        }

    }
}
