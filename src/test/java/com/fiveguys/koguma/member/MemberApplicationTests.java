package com.fiveguys.koguma.member;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.service.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberApplicationTests {

    @Autowired
    private MemberService memberService;


    @Test
    @DisplayName("회원 조회 테스트")
    @Transactional
    public  void getMember() throws Exception{
        Long memberId = 1L;
        MemberDTO getMember = memberService.getMember(memberId);
    }

    @Test
    @DisplayName("다른 회원 조회 테스트")
    @Transactional
    public void getOtherMember() throws Exception{
        Long otherMemberId = 2L;
        MemberDTO getotherMember = memberService.getOtherMember(otherMemberId);
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    @Transactional
    public void updateMemberTest() throws Exception {

        Long memberId = 1L;
        String newNickname = "newNickname";
        Long newImageId = 2L;
        Boolean newActiveFlag = true;

        System.out.println("업데이트 전");
        MemberDTO originalMember = memberService.getMember(memberId);
        System.out.println(originalMember);


        MemberDTO updatedMemberDTO = MemberDTO.builder()
                .id(memberId)
                .nickname(newNickname)
                .imageId(newImageId)
                .activeFlag(newActiveFlag)
                .build();

        memberService.updateMember(updatedMemberDTO);
        System.out.println("업데이트 후");
        MemberDTO updatedMember = memberService.getMember(memberId);
        System.out.println(updatedMember);


        assertAll(
                () -> assertEquals(newNickname, updatedMember.getNickname()),
                () -> assertEquals(newImageId, updatedMember.getImageId()),
                () -> assertEquals(newActiveFlag, updatedMember.getActiveFlag())
        );
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    @Transactional
    public void deleteMemberTest() throws Exception {
        Long memberId = 1L;

        MemberDTO originalMember = memberService.getMember(memberId);


        memberService.deleteMember(originalMember);

        MemberDTO deletedMember = memberService.getMember(memberId);

        // activeFlag가 false로 변경되었는지 확인
        assertFalse(deletedMember.getActiveFlag());
    }
    @Test
    @DisplayName("회원 목록 추가 테스트")
    @Transactional

    public void addMemberTest() {
        String[] nicknames = {"user1", "user2", "user3"};
        String[] passwords = {"password1", "password2", "password3"};
        String[] phones = {"010-1111-1111", "010-2222-2222", "010-3333-3333"};
        float[] scores = {36.5F, 37.0F, 38.5F};
        String[] emails = {"user1@example.com", "user2@example.com", "user3@example.com"};
        Boolean[] roleFlags = {false, true, false};
        Boolean[] socialFlags = {true, false, true};

        for (int i = 0; i < nicknames.length; i++) {
            MemberDTO memberDTO = MemberDTO.builder()
                    .nickname(nicknames[i])
                    .pw(passwords[i])
                    .phone(phones[i])
                    .score(scores[i])
                    .email(emails[i])
                    .roleFlag(roleFlags[i])
                    .socialFlag(socialFlags[i])
                    .build();
            memberService.addMember(memberDTO, nicknames[i], passwords[i], phones[i], scores[i], emails[i], roleFlags[i], socialFlags[i]);
        }
    }
    @Test
    @DisplayName("회원 목록 조회 테스트")
    @Transactional
    public void listMemberTest() throws Exception {

        addMemberTest();

        List<MemberDTO> memberList = memberService.listMember();

        assertFalse(memberList.isEmpty());

        memberList.forEach(memberDTO -> {
            MemberDTO retrievedMember = memberService.getMember(memberDTO.getId());
            assertAll(
                    () -> assertEquals(retrievedMember.getId(), memberDTO.getId()),
                    () -> assertEquals(retrievedMember.getNickname(), memberDTO.getNickname())
            );
        });
    }




    /*@Test
    @DisplayName("로그인 테스트")
    @Transactional
    public void loginTest() throws Exception {
        String nickname = "testUser";
        String password = "testPassword";
        Boolean activeFlag = true;

        // 회원 등록
        memberService.addMember(MemberDTO.builder()
                .nickname(nickname)
                .pw(password)
                .activeFlag(activeFlag)
                .build());

        // 로그인 시도
        MemberDTO loggedInMember = memberService.login(nickname, password, activeFlag);

        // 세션에 로그인한 회원 정보가 저장되었는지 확인
        assertNotNull(httpSession.getAttribute("loggedInMember"));

        // 반환된 MemberDTO와 등록한 회원 정보가 일치하는지 확인
        assertAll(
                () -> assertEquals(nickname, loggedInMember.getNickname()),
                () -> assertEquals(activeFlag, loggedInMember.getActiveFlag())
        );
    }*/
}


