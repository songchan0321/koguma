package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.MemberRelationship;
import com.fiveguys.koguma.data.entity.MemberRelationshipType;
import com.fiveguys.koguma.service.member.MemberRelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
public class MemberRelationshipRestController {
    private final MemberRelationshipService memberRelationshipService;

    @PostMapping("/relationship/block/add/{sourceMember}")
    public ResponseEntity<MemberRelationshipDTO> addBlock(@RequestBody MemberRelationshipDTO memberRelationshipDTO) {
        try {
            memberRelationshipService.addBlock(memberRelationshipDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(memberRelationshipDTO);
        }
    }
    /*{
        "sourceMember" : {
        "id" : 1
    },
        "targetMember" : {
        "id" : 2,
                "nickname" : "사기꾼"
    },
        "content" : "짜증나게 함",
            "memberRelationshipType" : "BLOCK"
    }*/
//http://localhost:8080/relationship/block/add/3




    @PutMapping("/relationship/block/delete/{sourceMemberId}/{targetMemberId}")
    public ResponseEntity<String> deleteBlock(@RequestBody Map<String, Long> requestBody) {
        Long sourceMember = requestBody.get("sourceMember");
        Long targetMember = requestBody.get("targetMember");

        // 예외 처리 등 필요한 로직 추가
        memberRelationshipService.deleteBlock(sourceMember, targetMember);

        return ResponseEntity.ok("차단 삭제 완료");
    }

    //{
    //  "sourceMember": 1,
    //  "targetMember": 3
    //}

    //차단 정보 조회
    @GetMapping("/relationship/block/get/{sourceMember}")
    public ResponseEntity<MemberRelationshipDTO> getBlock(@PathVariable Long sourceMember) {
        MemberRelationshipDTO existingMember = memberRelationshipService.getBlock(sourceMember);
        if (existingMember == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(existingMember);
        }
        return ResponseEntity.ok(existingMember);
    }

    @GetMapping("/relationship/block/list/{sourceMemberId}")
    public List<MemberRelationshipDTO> ListBlock(@PathVariable Long sourceMemberId) {
        return memberRelationshipService.listBlock(sourceMemberId);
    }

    @PostMapping("/relationship/following/add")
    public void add(@RequestBody MemberRelationshipDTO memberRelationshipDTO) {
        memberRelationshipService.addFollowing(memberRelationshipDTO);
    }
    @PutMapping("/relationship/following/delete/{id}")
    public void delete(@RequestBody MemberRelationshipDTO memberRelationshipDTO){
        memberRelationshipService.deleteFollowing(memberRelationshipDTO.getId());
    }
    @GetMapping("/relationship/following/get/{sourceMember}")
    public ResponseEntity<MemberRelationshipDTO> getFollowing(@PathVariable Long sourceMember) {
        MemberRelationshipDTO existingMember = memberRelationshipService.getFollowing(sourceMember);
        if (existingMember == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(existingMember);
        }
        return ResponseEntity.ok(existingMember);
    }
    @GetMapping("/relationship/following/list/{sourceMemberId}")
    public List<MemberRelationshipDTO> ListFollowing(@PathVariable Long sourceMemberId) {
        return memberRelationshipService.listFollowing(sourceMemberId);
    }
}
