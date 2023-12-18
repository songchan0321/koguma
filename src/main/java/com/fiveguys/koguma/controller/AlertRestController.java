package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.AlertDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.service.common.AlertService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/alert")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AlertRestController {
    private final AlertService alertService;

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Integer>> getAlertCount(
            @CurrentMember MemberDTO memberDTO
    ) {
        return ResponseEntity.ok().body(Map.of("count", alertService.listAlert(memberDTO).size()));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<AlertDTO>> listAlert(
            @CurrentMember MemberDTO memberDTO
    ) {
        return ResponseEntity.ok().body(alertService.listAlert(memberDTO));
    }

    @RequestMapping(value = "/read/{alertId}", method = RequestMethod.POST)
    public ResponseEntity readAlert(
            @CurrentMember MemberDTO memberDTO,
            @PathVariable String alertId
    ) {
        alertService.readAlert(memberDTO, alertId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/readAll", method = RequestMethod.POST)
    public ResponseEntity readAlertAll(
            @CurrentMember MemberDTO memberDTO
    ) {
        alertService.readAlertAll(memberDTO);
        return ResponseEntity.ok().build();
    }
}
