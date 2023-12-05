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

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
public class MemberRelationshipRestController {
    private final MemberRelationshipService memberRelationshipService;

    @PostMapping("/memberRelationship/block/add/{sourceMember}")
    public ResponseEntity<MemberRelationshipDTO> addBlock(@RequestBody MemberRelationshipDTO memberRelationshipDTO) {
        try {
            memberRelationshipService.addBlock(memberRelationshipDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(memberRelationshipDTO);
        }
    }




    @PutMapping("/relationship/block/delete/{sourceMemberId}/{targetMemberId}")
    public ResponseEntity<MemberRelationshipDTO> deleteBlock(
            @PathVariable Long sourceMemberId,
            @RequestBody Long targetMemberId) {
        memberRelationshipService.deleteBlock(sourceMemberId, targetMemberId);
        return ResponseEntity.ok().build();
    }

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
