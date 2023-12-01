package com.fiveguys.koguma.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiveguys.koguma.data.dto.AlertDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.service.common.AlertService;
import com.fiveguys.koguma.service.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AlertApplicationTests {
    @Autowired private AlertService alertService;
    @Autowired private MemberService memberService;
    @Test
    public void addAlertTest() throws JsonProcessingException {
        MemberDTO memberDTO = memberService.getMember(1L);
        alertService.addAlert(memberDTO, "회원", "회원 가입", "member/" + memberDTO.getId());
        alertService.addAlert(memberDTO, "회원", "회원 가입", "member/" + memberDTO.getId());
        alertService.addAlert(memberDTO, "회원", "회원 가입", "member/" + memberDTO.getId());
        alertService.addAlert(memberDTO, "회원", "회원 가입", "member/" + memberDTO.getId());
        alertService.addAlert(memberDTO, "회원", "회원 가입", "member/" + memberDTO.getId());
        alertService.addAlert(memberDTO, "회원", "회원 가입", "member/" + memberDTO.getId());
    }

    @Test
    public void listAlertTest() {
        MemberDTO memberDTO = memberService.getMember(1L);
        List<AlertDTO> alertDTOList = alertService.listAlert(memberDTO);
        System.out.println(alertDTOList);
    }

    @Test
    public void readAlertTest() {
        MemberDTO memberDTO = memberService.getMember(1L);

        alertService.readAlert(memberDTO, "ef6c39f5-08ea-4aeb-9209-d349d64e7674");
    }

    @Test
    public void readAlertAllTest() throws JsonProcessingException {
        MemberDTO memberDTO = memberService.getMember(1L);
        alertService.addAlert(memberDTO, "회원", "회원 가입", "member/" + memberDTO.getId());
        alertService.addAlert(memberDTO, "회원", "회원 가입", "member/" + memberDTO.getId());
        alertService.addAlert(memberDTO, "회원", "회원 가입", "member/" + memberDTO.getId());
        alertService.addAlert(memberDTO, "회원", "회원 가입", "member/" + memberDTO.getId());
        alertService.addAlert(memberDTO, "회원", "회원 가입", "member/" + memberDTO.getId());
        alertService.readAlertAll(memberDTO);
    }
}
