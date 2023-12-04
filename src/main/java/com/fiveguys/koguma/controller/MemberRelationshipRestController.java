package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.entity.MemberRelationship;
import com.fiveguys.koguma.service.member.MemberRelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberRelationshipRestController {
    private final MemberRelationshipService memberRelationshipService;

    @PostMapping("/memberRelationship/block/add")
    public void addBlock(@RequestBody MemberRelationshipDTO memberRelationshipDTO) {
        memberRelationshipService.addBlock(memberRelationshipDTO);
    }

    @PutMapping("/memberRelationship/block/delete/{id}")
    public void deleteBlock(@RequestBody MemberRelationshipDTO memberRelationshipDTO) {
        memberRelationshipService.deleteBlock(memberRelationshipDTO);
    }

    @GetMapping("/memberRelationship/block/get/{sourceMember}")
    public MemberRelationship getBlock(@PathVariable Long sourceMember) {
        memberRelationshipService.getBlock(sourceMember);
        return memberRelationshipService.getBlock(sourceMember).toEntity();
    }

    @GetMapping("/memberRelationship/block/list/{sourceMemberId}")
    public List<MemberRelationshipDTO> ListBlock(@PathVariable Long sourceMemberId) {
        return memberRelationshipService.listBlock(sourceMemberId);
        //return memberRelationshipService.listBlock(sourceMemberId).toEntity();
    }

    @PostMapping("/memberRelationship/following/add")
    public void add(@RequestBody MemberRelationshipDTO memberRelationshipDTO) {
        memberRelationshipService.addFollowing(memberRelationshipDTO);
    }
    @PutMapping("/memberRelationship/following/delete/{id}")
    public void delete(@RequestBody MemberRelationshipDTO memberRelationshipDTO){
        memberRelationshipService.deleteFollowing(memberRelationshipDTO.getId());
    }
    @GetMapping("/memberRelationship/following/get/{sourceMember}")
    public MemberRelationship get(@PathVariable Long sourceMember){
        memberRelationshipService.getFollowing(sourceMember);
        return memberRelationshipService.getFollowing(sourceMember).toEntity();
    }
    @GetMapping("/memberRelationship/following/list/{sourceMemberId}")
    public List<MemberRelationshipDTO> ListFollowing(@PathVariable Long sourceMemberId){
        return memberRelationshipService.listFollowing(sourceMemberId);
        //return memberRelationshipService.listFollowing(sourceMemberId).toEntity();
    }
}
