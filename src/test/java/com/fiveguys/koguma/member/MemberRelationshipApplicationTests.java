package com.fiveguys.koguma.member;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.MemberRelationship;
import com.fiveguys.koguma.data.entity.MemberRelationshipType;
import com.fiveguys.koguma.repository.member.MemberRelationshipRepository;
import com.fiveguys.koguma.service.member.MemberRelationshipService;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.member.MemberServiceImpl;
import com.fiveguys.koguma.service.member.MemberRelationshipServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class MemberRelationshipApplicationTests {

    @Autowired
    private MemberRelationshipService memberRelationshipService;

    @Autowired
    private MemberRelationshipRepository memberRelationshipRepository;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void setUp() {
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }

    @Test
    @DisplayName("차단 추가 테스트")
    @Transactional
    public void addBlockTest() throws Exception {
        Member sourceMember = memberService.getMember(1L).toEntity();
        Member targetMember = memberService.getMember(2L).toEntity();

        MemberRelationship memberRelationship = MemberRelationship.builder()
                .sourceMember(sourceMember)
                .targetMember(targetMember)
                .content("사기 쳤어요")
                .memberRelationshipType(MemberRelationshipType.BLOCK)
                .build();
        memberRelationshipService.addBlock(MemberRelationshipDTO.fromEntity(memberRelationship));
    }

    @Test
    @DisplayName("차단 삭제 테스트")
    @Transactional
    public void deleteBlockTest() {
        Member sourceMember = memberService.getMember(1L).toEntity();
        Member targetMember = memberService.getMember(2L).toEntity();

        MemberRelationship memberRelationship = MemberRelationship.builder()
                .sourceMember(sourceMember)
                .targetMember(targetMember)
                .content("사기 쳤어요")
                .memberRelationshipType(MemberRelationshipType.BLOCK)
                .build();
        memberRelationshipService.addBlock(MemberRelationshipDTO.fromEntity(memberRelationship));

    }

    @Test
    @DisplayName("차단 목록 조회")
    @Transactional
    public void listBlockTest() {
        List<MemberRelationshipDTO> memberRelationships = memberRelationshipService.listBlock(5L);
        assertEquals(1, memberRelationships.size());
    }

    /*@Test
    @DisplayName("차단 상세 조회")
    @Transactional
    public void getBlock() {
        System.out.println((memberRelationshipService.getBlock(5L).toString()));
    }*/

    @Test
    @DisplayName("팔로잉 추가 테스트")
    @Transactional
    public void addFollowingTest() throws Exception {
        Member sourceMember = memberService.getMember(3L).toEntity();
        Member targetMember = memberService.getMember(4L).toEntity();

        MemberRelationship memberRelationship = MemberRelationship.builder()
                .sourceMember(sourceMember)
                .targetMember(targetMember)
                .content("좋은 물건을 많이 판다")
                .memberRelationshipType(MemberRelationshipType.FOLLOWING)
                .build();
        memberRelationshipService.addBlock(MemberRelationshipDTO.fromEntity(memberRelationship));
    }

    @Test
    @DisplayName("팔로잉 목록 조회")
    @Transactional
    public void listFollowingTest() {
        List<MemberRelationshipDTO> memberRelationships = memberRelationshipService.listFollowing(6L);
        assertEquals(1, memberRelationships.size());
    }
}

    /*@Test
    @DisplayName("팔로잉 상세 조회")
    @Transactional
    public void getFollowing(){
        System.out.println((memberRelationshipService.getFollowing(6L).toString()));
    }
}*/