package com.fiveguys.koguma.member;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.service.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        String newNickname = "김태현";
        Long newImageId = 2L;

        System.out.println("업데이트 전");
        MemberDTO oldMember = memberService.getMember(memberId);
        System.out.println(oldMember);

        MemberDTO updatedMember = MemberDTO.builder()
                .id(oldMember.getId())
                .nickname(newNickname)
                .imageId(newImageId)
                .activeFlag(oldMember.getActiveFlag())
                .score(oldMember.getScore())
                .roleFlag(oldMember.getRoleFlag())
                .socialFlag(oldMember.getSocialFlag())
                .email(oldMember.getEmail())
                .paymentAccount(oldMember.getPaymentAccount())
                .paymentBank(oldMember.getPaymentBank())
                .paymentBalance(oldMember.getPaymentBalance())
                .paymentPw(oldMember.getPaymentPw())
                .regDate(oldMember.getRegDate())
                .build();

        memberService.updateMember(updatedMember);

        System.out.println("업데이트 후");
        MemberDTO afterUpdateMember = memberService.getMember(memberId);
        System.out.println(afterUpdateMember);

        assertAll(
                () -> assertEquals(newNickname, afterUpdateMember.getNickname()),
                () -> assertEquals(newImageId, afterUpdateMember.getImageId())
        );
    }
        /*public void updateMemberTest () throws Exception {

        Long memberId = 1L;
        String newNickname = "cexman";
        Long newImageId = 3L;
        Boolean newActiveFlag = true;

        System.out.println("업데이트 전");
        MemberDTO originalMember = memberService.getMember(memberId);
        System.out.println(originalMember);


        MemberDTO existingMember = MemberDTO.builder()
                .id(originalMember.getId())
                .nickname("man")
                .imageId(2L)
                .activeFlag(originalMember.getActiveFlag())
                .score(originalMember.getScore())
                .roleFlag(originalMember.getRoleFlag())
                .socialFlag(originalMember.getSocialFlag())
                .email(originalMember.getEmail())
                .paymentAccount(originalMember.getPaymentAccount())
                .paymentBank(originalMember.getPaymentBank())
                .paymentBalance(originalMember.getPaymentBalance())
                .paymentPw(originalMember.getPaymentPw())
                .regDate(originalMember.getRegDate())
                .build();

        memberService.updateMember(existingMember);

        MemberDTO updatedMemberDTO = new MemberDTO();
        updatedMemberDTO.setId(existingMember.getId());
        updatedMemberDTO.setNickname("cexman");
        updatedMemberDTO.setImageId(3L);

        memberService.updateMember(updatedMemberDTO);

        System.out.println("업데이트 후");
        MemberDTO updatedMember = memberService.getMember(memberId);
        System.out.println(updatedMember);


        assertAll(
                () -> assertEquals(newNickname, updatedMember.getNickname()),
                () -> assertEquals(newImageId, updatedMember.getImageId())
        );
    }*/


    /*@Test
    @DisplayName("회원 삭제 테스트")
    @Transactional
    public void deleteMemberTest() throws Exception {
        Long memberId = 1L;

        MemberDTO originalMember = memberService.getMember(memberId);


        memberService.deleteMember(originalMember);

        MemberDTO deletedMember = memberService.getMember(memberId);

        // activeFlag가 false로 변경되었는지 확인
        assertFalse(deletedMember.getActiveFlag());
    }*/
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
    @DisplayName("비밀번호 유효성 검사 테스트")
    void testValidationCheckPwMatching() {
        // Given
        String rawPassword = "myPassword";
        String encodedPassword = "myPassword";

        MemberService memberService = new MemberService() {
            @Override
            public Member addMember(MemberDTO memberDTO, String nickname, String pw, String phone, float score, String email, Boolean roleFlag, Boolean socialFlag) {

                return null;
            }

            @Override
            public void deleteMember(MemberDTO memberDTO) {

            }

            @Override
            public void updateMember(MemberDTO memberDTO, Long id, String nickname, Long imageId, Boolean activeFlag) {

            }

            @Override
            public void updateMember(MemberDTO memberDTO) {

            }

            @Override
            public MemberDTO login(String nickname, String pw) {
                return null;
            }

            @Override
            public boolean validationCheckPw(String rawPw, String encodedPw) {
                return false;
            }

            @Override
            public void logout() {

            }

            @Override
            public MemberDTO getMember(Long id) {
                return null;
            }

            @Override
            public MemberDTO getOtherMember(Long id) {
                return null;
            }

            @Override
            public List<MemberDTO> listMember() {
                return null;
            }
        };

        // When
        boolean result = memberService.validationCheckPw(rawPassword, encodedPassword);

        // Then
        assertFalse(result);
    }*/

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



