package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.MemberRelationship;
import com.fiveguys.koguma.data.entity.MemberRelationshipType;
import com.fiveguys.koguma.service.chat.ChatService;
import com.fiveguys.koguma.service.member.MemberRelationshipService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.xml.ws.Response;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
public class MemberRelationshipRestController {
    private final MemberRelationshipService memberRelationshipService;
    private final ChatService chatService;

    @PostMapping("/member/relationship/block/add")
    public ResponseEntity<MemberRelationshipDTO> addBlock(
            @CurrentMember MemberDTO authenticatedMember,
            @RequestBody MemberRelationshipDTO memberRelationshipDTO
    ) {
        memberRelationshipDTO.setSourceMember(authenticatedMember.toEntity()); // 현재 인증된 사용자를 sourceMember로 설정
        try {
            // 인증된 사용자 정보가 없거나 인증된 사용자의 ID와 요청의 sourceMember가 일치하지 않으면 권한 없음 응답
            if (authenticatedMember == null || !authenticatedMember.getId().equals(memberRelationshipDTO.getSourceMember().getId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            // 차단 추가 로직 수행

            memberRelationshipService.addBlock(memberRelationshipDTO);
            chatService.exitChatroomAllByBlockMember(authenticatedMember, MemberDTO.fromEntity(memberRelationshipDTO.targetMember));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(memberRelationshipDTO);
        }
    }


    /*{
  "sourceMember": {
    "id": 1,  // 현재 인증된 사용자의 ID
    "nickname": "현재인증된사용자"
  },
  "targetMember": {
    "id": 2,  // 차단 대상의 ID
    "nickname": "차단대상"
  },
  "content": "차단합니다.",
  "memberRelationshipType": "BLOCK"
}*/
//http://localhost:8080/relationship/block/add/3




    @PutMapping("/member/relationship/block/delete")
    public ResponseEntity<String> deleteBlock(
            @RequestBody Map<String, Long> requestBody,
            @CurrentMember MemberDTO authenticatedMember
    ) {
        // requestBody에서 targetMemberId를 추출합니다.
        Long sourceMemberId = authenticatedMember.getId();
        Long targetMemberId = requestBody.get("targetMemberId");
        // authenticatedMember를 사용하여 현재 인증된 사용자의 정보를 활용할 수 있습니다.

        try {
            // 예외 처리 등 필요한 로직 추가
            memberRelationshipService.deleteBlock(sourceMemberId, targetMemberId);
            return ResponseEntity.ok("차단 삭제 완료");
        } catch (NoResultException e) {
            // 차단 정보가 없을 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("차단 정보가 없습니다.");
        } catch (Exception e) {
            // 그 외 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("차단 삭제 중 오류가 발생했습니다.");
        }
    }

    //{
    //  "targetMemberId": 2
    //}

    //차단 정보 조회
    // KTH 로그인한 사람이 상대방에게 차단 당했는지 여부 따지는 함수
    @GetMapping("/member/relationship/block/get/reverse/{sourceMemberId}")
    public CompletableFuture<ResponseEntity<MemberRelationshipDTO>> getBlockReverse(
            @CurrentMember MemberDTO authenticatedMember,
            @PathVariable Long sourceMemberId
    ) {
        Long targetMemberId = authenticatedMember.getId();

        return CompletableFuture.supplyAsync(() -> {
            try {
                MemberRelationshipDTO existingMember = memberRelationshipService.getBlock(sourceMemberId, targetMemberId);
                return ResponseEntity.ok(existingMember);
            } catch (NoResultException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        });
    }

    @GetMapping("/member/relationship/block/get/{targetMemberId}")
    public CompletableFuture<ResponseEntity<MemberRelationshipDTO>> getBlock(
            @CurrentMember MemberDTO authenticatedMember,
            @PathVariable Long targetMemberId
    ) {
        Long sourceMemberId = authenticatedMember.getId();

        return CompletableFuture.supplyAsync(() -> {
            try {
                MemberRelationshipDTO existingMember = memberRelationshipService.getBlock(sourceMemberId, targetMemberId);
                return ResponseEntity.ok(existingMember);
            } catch (NoResultException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        });
    }


    @GetMapping("/member/relationship/block/list")
    public List<MemberRelationshipDTO> listBlock(@CurrentMember MemberDTO authenticatedMember) {
        // authenticatedMember를 사용하여 현재 인증된 사용자의 정보를 활용할 수 있습니다.
        Long sourceMemberId = authenticatedMember.getId();

        return memberRelationshipService.listBlock(sourceMemberId);
    }
    @PostMapping("/member/relationship/following/add")
    public ResponseEntity<MemberRelationshipDTO> addFollowing(
            @CurrentMember MemberDTO authenticatedMember,
            @RequestBody MemberRelationshipDTO memberRelationshipDTO
    ) {
        memberRelationshipDTO.setSourceMember(authenticatedMember.toEntity()); // 현재 인증된 사용자를 sourceMember로 설정
        try {
            // 인증된 사용자 정보가 없거나 인증된 사용자의 ID와 요청의 sourceMember가 일치하지 않으면 권한 없음 응답
            if (authenticatedMember == null || !authenticatedMember.getId().equals(memberRelationshipDTO.getSourceMember().getId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            // 차단 추가 로직 수행

            memberRelationshipService.addFollowing(memberRelationshipDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(memberRelationshipDTO);
        }
    }
    /*{
  "sourceMember": {
    "id": 1,  // 현재 인증된 사용자의 ID
    "nickname": "현재인증된사용자"
  },
  "targetMember": {
    "id": 2,  // 팔로잉 대상의 ID
    "nickname": "팔로잉 대상"
  },
  "content": "멋져유.",
  "memberRelationshipType": "FOLLOWING"
}*/
    @PutMapping("/member/relationship/following/delete")
    public ResponseEntity<String> deleteFollowing(
            @RequestBody Map<String, Long> requestBody,
            @CurrentMember MemberDTO authenticatedMember
    ) {
        // requestBody에서 targetMemberId를 추출합니다.
        Long sourceMemberId = authenticatedMember.getId();
        Long targetMemberId = requestBody.get("targetMemberId");
        // authenticatedMember를 사용하여 현재 인증된 사용자의 정보를 활용할 수 있습니다.

        try {
            // 예외 처리 등 필요한 로직 추가
            memberRelationshipService.deleteFollowing(sourceMemberId, targetMemberId);
            return ResponseEntity.ok("팔로잉 삭제 완료");
        } catch (NoResultException e) {
            // 차단 정보가 없을 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("팔로잉 정보가 없습니다.");
        } catch (Exception e) {
            // 그 외 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("팔로잉 삭제 중 오류가 발생했습니다.");
        }
    }
    //{
    //      "targetMemberId": 5
    //}
    @GetMapping("/member/relationship/following/get/{targetMemberId}")
    public ResponseEntity<MemberRelationshipDTO> getFollowing(
            @CurrentMember MemberDTO authenticatedMember,
            @PathVariable Long targetMemberId
    ) {
        Long sourceMemberId = authenticatedMember.getId();

        MemberRelationshipDTO existingMember = memberRelationshipService.getFollowing(sourceMemberId, targetMemberId);
        if (existingMember == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(existingMember);
        }
        return ResponseEntity.ok(existingMember);
    }
    @GetMapping("/member/relationship/following/list")
    public List<MemberRelationshipDTO> listFollowing(@CurrentMember MemberDTO authenticatedMember) {
        // authenticatedMember를 사용하여 현재 인증된 사용자의 정보를 활용할 수 있습니다.
        Long sourceMemberId = authenticatedMember.getId();

        return memberRelationshipService.listFollowing(sourceMemberId);
    }

    @GetMapping("/member/relationship/block/check/{targetMemberId}")
    public ResponseEntity<Boolean> checkBlockRelationship(
            @PathVariable Long targetMemberId,
            @CurrentMember MemberDTO authenticatedMember
    ) {
        if (authenticatedMember == null) {
            // 사용자 정보가 없으면 예외처리 또는 다른 로직을 수행
            return ResponseEntity.badRequest().build();
        }

        // 현재 접속된 사용자의 정보를 가져옴
        Long sourceMemberId = authenticatedMember.getId();

        boolean isBlocked = memberRelationshipService.isBlocked(targetMemberId, sourceMemberId);
        return ResponseEntity.ok(isBlocked);
    }
}
