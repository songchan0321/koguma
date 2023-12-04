package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.ChatroomDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PaymentHistoryDTO;
import com.fiveguys.koguma.data.entity.PaymentHistoryType;
import com.fiveguys.koguma.service.chat.ChatService;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/payment")
@RequiredArgsConstructor
public class PaymentHistoryRestController {
    private final PaymentService paymentService;
    private final MemberService memberService;
    private final ChatService chatService;

    // login 필요
    @RequestMapping(value = "/exist", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> existPayment(
            @RequestParam Long memberId
    ) {
        MemberDTO memberDTO = memberService.getMember(memberId);

        boolean paymentExists = paymentService.existPayment(memberDTO);
        Map<String, Object> response = Map.of("result", paymentExists);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, Object>> deletePayment(
            @RequestParam Long memberId,
            @RequestBody Map<String, String> json
    ) {
        MemberDTO memberDTO = memberService.getMember(memberId);
        String paymentPw = json.get("paymentPw");
        System.out.println(memberDTO.getPaymentPw());
        System.out.println(paymentPw);
        if(paymentService.validatePaymentPw(memberDTO, paymentPw)) {
            paymentService.deletePayment(memberDTO);
            return ResponseEntity.ok().body(Map.of("result", true));
        } else {
            return ResponseEntity.ok().body(Map.of("result", false));
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addPayment(
            @RequestParam Long memberId,
            @RequestBody MemberDTO memberDTO
    ) throws Exception {
        MemberDTO currentMemberDTO = memberService.getMember(memberId);
        if(memberDTO.getPaymentAccount() == null) throw new Exception("계좌번호가 없습니다.");
        if(memberDTO.getPaymentBank() == null) throw new Exception("은행이 없습니다.");
        if(memberDTO.getPaymentPw() == null) throw new Exception("패스워드가 없습니다.");
        paymentService.addPayment(
                currentMemberDTO,
                memberDTO.getPaymentAccount(),
                memberDTO.getPaymentBank(),
                memberDTO.getPaymentPw()
        );
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/history/list", method = RequestMethod.GET)
    public ResponseEntity<List<PaymentHistoryDTO>> listPaymentHistory(
            @RequestParam Long memberId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String type
    ) {
        MemberDTO memberDTO = memberService.getMember(memberId);
        List<PaymentHistoryDTO> paymentHistoryDTOList = (type != null)
                ? paymentService.listPaymentHistory(memberDTO, PageRequest.of(page, 10), type)
                : paymentService.listPaymentHistory(memberDTO, PageRequest.of(page, 10));
        return ResponseEntity.ok().body(paymentHistoryDTOList);
    }

    @RequestMapping(value = "/charge", method = RequestMethod.POST)
    public ResponseEntity chargePoint(
            @RequestParam Long memberId,
            @RequestBody Map<String, String> json
    ) throws Exception {
        MemberDTO memberDTO = memberService.getMember(memberId);
        int point = Integer.parseInt(json.get("point"));
        if(point < 0)
            throw new Exception("충전은 0원 초과만 가능합니다.");
        paymentService.chargePoint(memberDTO, point);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public ResponseEntity transferPoint(
            @RequestParam Long memberId,
            @RequestBody Map<String, String> json
    ) throws Exception {
        MemberDTO senderMemberDTO = memberService.getMember(memberId);
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
            @RequestParam Long memberId,
            @RequestBody Map<String, String> json
    ) {
        MemberDTO memberDTO = memberService.getMember(memberId);
        String paymentPw = json.get("paymentPw");
        boolean result = memberService.validationCheckPw(paymentPw, memberDTO.getPaymentPw());
        return ResponseEntity.ok().body(Map.of("result", result));
    }

    @RequestMapping(value = "/request/refund", method = RequestMethod.POST)
    public ResponseEntity requestRefund(
            @RequestParam Long memberId,
            @RequestBody Map<String, String> json
    ) throws Exception {
        MemberDTO memberDTO = memberService.getMember(memberId);
        int point = Integer.parseInt(json.get("point"));
        if(point < 0)
            throw new Exception("환급 요청은 0원 초과만 가능합니다.");
        paymentService.requestRefundPoint(memberDTO, point);
        return ResponseEntity.ok().build();
    }

    // admin
    @RequestMapping(value = "/success/refund", method = RequestMethod.POST)
    public ResponseEntity successRefund(
            @RequestParam Long memberId,
            @RequestBody Map<String, String> json
    ) throws Exception {
        MemberDTO memberDTO = memberService.getMember(memberId);
        long paymentHistoryId = Long.parseLong(json.get("paymentHistoryId"));
        PaymentHistoryDTO paymentHistoryDTO = paymentService.getPaymentHistory(paymentHistoryId);
        if(!paymentHistoryDTO.getType().equals(PaymentHistoryType.REFUND_REQUEST)) {
            throw new Exception("허용되지 않은 접근입니다.");
        }
        paymentService.successRefundPoint(paymentHistoryDTO);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/cancel/refund", method = RequestMethod.POST)
    public ResponseEntity cancelRefund(
            @RequestParam Long memberId,
            @RequestBody Map<String, String> json
    ) throws Exception {
        MemberDTO memberDTO = memberService.getMember(memberId);
        long paymentHistoryId = Long.parseLong(json.get("paymentHistoryId"));
        PaymentHistoryDTO paymentHistoryDTO = paymentService.getPaymentHistory(paymentHistoryId);
        if(!paymentHistoryDTO.getType().equals(PaymentHistoryType.REFUND_REQUEST)) {
            throw new Exception("허용되지 않은 접근입니다.");
        }
        paymentService.deletePaymentHistory(paymentHistoryDTO);
        return ResponseEntity.ok().build();
    }
}
