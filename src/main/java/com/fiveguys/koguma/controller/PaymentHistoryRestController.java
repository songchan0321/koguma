package com.fiveguys.koguma.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiveguys.koguma.data.dto.ChatroomDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PaymentHistoryDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.PaymentHistoryType;
import com.fiveguys.koguma.service.chat.ChatService;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.payment.PaymentService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/payment")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaymentHistoryRestController {
    private final PaymentService paymentService;
    private final MemberService memberService;
    private final ChatService chatService;

    // login 필요
    @RequestMapping(value = "/exist", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> existPayment(
            @CurrentMember MemberDTO memberDTO
    ) {
        System.out.println(memberDTO);
        boolean paymentExists = paymentService.existPayment(memberDTO);
        Map<String, Object> response = Map.of("result", paymentExists);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, Object>> deletePayment(
            @CurrentMember MemberDTO memberDTO,
            @RequestBody Map<String, String> json
    ) {
        String paymentPw = json.get("paymentPw");
        if(paymentService.validatePaymentPw(memberDTO, paymentPw)) {
            paymentService.deletePayment(memberDTO);
            return ResponseEntity.ok().body(Map.of("result", true));
        } else {
            return ResponseEntity.ok().body(Map.of("result", false));
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addPayment(
            @CurrentMember MemberDTO memberDTO,
            @RequestBody MemberDTO inputMemberDTO
    ) throws Exception {
        if(inputMemberDTO.getPaymentAccount() == null) throw new Exception("계좌번호가 없습니다.");
        if(inputMemberDTO.getPaymentBank() == null) throw new Exception("은행이 없습니다.");
        if(inputMemberDTO.getPaymentPw() == null) throw new Exception("패스워드가 없습니다.");
        paymentService.addPayment(
                memberDTO,
                inputMemberDTO.getPaymentAccount(),
                inputMemberDTO.getPaymentBank(),
                inputMemberDTO.getPaymentPw()
        );
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/history/list", method = RequestMethod.GET)
    public ResponseEntity<List<PaymentHistoryDTO>> listPaymentHistory(
            @CurrentMember MemberDTO memberDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String type
    ) {
        List<PaymentHistoryDTO> paymentHistoryDTOList = (type != null)
                ? paymentService.listPaymentHistory(memberDTO, PageRequest.of(page, 10), type)
                : paymentService.listPaymentHistory(memberDTO, PageRequest.of(page, 10));
        return ResponseEntity.ok().body(paymentHistoryDTOList);
    }

//    @RequestMapping(value = "/charge", method = RequestMethod.POST)
//    public ResponseEntity chargePoint(
//            @RequestParam Long memberId,
//            @RequestBody Map<String, String> json
//    ) throws Exception {
//        MemberDTO memberDTO = memberService.getMember(memberId);
//        int point = Integer.parseInt(json.get("point"));
//        if(point < 0)
//            throw new Exception("충전은 0원 초과만 가능합니다.");
//        paymentService.chargePoint(memberDTO, point);
//        return ResponseEntity.ok().build();
//    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public ResponseEntity transferPoint(
            @CurrentMember MemberDTO senderMemberDTO,
            @RequestBody Map<String, String> json
    ) throws Exception {
        MemberDTO receiverMemberDTO = memberService.getMember(Long.parseLong(json.get("receiverMemberId")));
        ChatroomDTO chatroomDTO = chatService.getChatroom(Long.parseLong(json.get("chatroomId")));
        int point = Integer.parseInt(json.get("point"));
        if(point < 0)
            throw new Exception("이체는 0원 초과만 가능합니다.");
        paymentService.transferPoint(senderMemberDTO, receiverMemberDTO, chatroomDTO, point);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/checkPw", method = RequestMethod.GET)
    public ResponseEntity checkPaymentPw(
            @CurrentMember MemberDTO memberDTO,
            @RequestBody Map<String, String> json
    ) {
        String paymentPw = json.get("paymentPw");
        boolean result = memberService.validationCheckPw(paymentPw, memberDTO.getPaymentPw());
        return ResponseEntity.ok().body(Map.of("result", result));
    }

    @RequestMapping(value = "/request/refund", method = RequestMethod.POST)
    public ResponseEntity requestRefund(
            @CurrentMember MemberDTO memberDTO,
            @RequestBody Map<String, String> json
    ) throws Exception {
        int point = Integer.parseInt(json.get("point"));
        if(point < 0)
            throw new Exception("환급 요청은 0원 초과만 가능합니다.");
        paymentService.requestRefundPoint(memberDTO, point);
        return ResponseEntity.ok().build();
    }

    // admin
    @RequestMapping(value = "/success/refund", method = RequestMethod.POST)
    public ResponseEntity successRefund(
            @CurrentMember MemberDTO memberDTO,
            @RequestBody Map<String, String> json
    ) throws Exception {
        String paymentHistoryId = json.get("paymentHistoryId");
        PaymentHistoryDTO paymentHistoryDTO = paymentService.getPaymentHistory(UUID.fromString(paymentHistoryId));
        if(!paymentHistoryDTO.getMemberDTO().getId().equals(memberDTO.getId())) {
            throw new Exception("권한이 없습니다.");
        }
        if(!paymentHistoryDTO.getType().equals(PaymentHistoryType.REFUND_REQUEST)) {
            throw new Exception("허용되지 않은 접근입니다.");
        }
        paymentService.successRefundPoint(paymentHistoryDTO);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/cancel/refund", method = RequestMethod.POST)
    public ResponseEntity cancelRefund(
            @CurrentMember MemberDTO memberDTO,
            @RequestBody Map<String, String> json
    ) throws Exception {
        String paymentHistoryId = json.get("paymentHistoryId");
        PaymentHistoryDTO paymentHistoryDTO = paymentService.getPaymentHistory(UUID.fromString(paymentHistoryId));
        // admin check 필요
        if(!paymentHistoryDTO.getType().equals(PaymentHistoryType.REFUND_REQUEST)) {
            throw new Exception("허용되지 않은 접근입니다.");
        }
        paymentService.deletePaymentHistory(paymentHistoryDTO);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/check/name", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> checkName(
            @RequestBody Map<String, String> json
    ) throws JsonProcessingException {
        String name = json.get("name");
        String account = json.get("account");
        String bankCode = json.get("code");
        Map<String, Object> response = Map.of("result", paymentService.checkPortOneAccountName(name, account, bankCode));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/check/charge", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> checkCharge(
            @RequestParam Long memberId,
            @RequestBody Map<String, String> json
    ) {
        String impUid = json.get("imp_uid");
        String merchantUid = json.get("merchant_uid");
        MemberDTO memberDTO = memberService.getMember(memberId);
        try {
            Map<String, Object> response = Map.of("result", paymentService.checkPortOneChargeSuccess(memberDTO, impUid, merchantUid));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("result", false));
        }
    }
}
