package com.fiveguys.koguma.payment;

import com.fiveguys.koguma.data.dto.ChatroomDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PaymentHistoryDTO;
import com.fiveguys.koguma.data.entity.PaymentHistoryType;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;


//@RequiredArgsConstructor
@SpringBootTest
@Transactional
public class PaymentHistoryApplicationTests {
    @Autowired private PaymentService paymentService;
    @Autowired private MemberService memberService;
    @Test
    public void addPaymentTest() {
        MemberDTO memberDTO = memberService.getMember(MemberDTO.builder().id(1L).build());
        System.out.println(memberDTO);
        paymentService.addPayment(memberDTO, "3333-12-2444441", "카카오 뱅크", "123");
        MemberDTO newMemberDTO = memberService.getMember(MemberDTO.builder().id(1L).build());
        assert newMemberDTO.getPaymentAccount().equals(memberDTO.getPaymentAccount());
        assert newMemberDTO.getPaymentBank().equals(memberDTO.getPaymentBank());
        assert newMemberDTO.getPaymentBalance() == 0;
        assert newMemberDTO.getPaymentPw().equals(memberDTO.getPaymentPw());
    }

    @Test
    public void deletePaymentTest() {
        MemberDTO memberDTO = memberService.getMember(MemberDTO.builder().id(1L).build());
        paymentService.addPayment(memberDTO, "3333-12-2444441", "카카오 뱅크", "123");
        paymentService.deletePayment(memberDTO);
        MemberDTO newMemberDTO = memberService.getMember(MemberDTO.builder().id(1L).build());
        assert newMemberDTO.getPaymentAccount() == null;
        assert newMemberDTO.getPaymentBank() == null;
        assert newMemberDTO.getPaymentBalance() == null;
        assert newMemberDTO.getPaymentPw() == null;
    }

    @Test
    public void paymentUsePointTest() {
        MemberDTO senderDTO = memberService.getMember(MemberDTO.builder().id(1L).build());
        paymentService.addPayment(senderDTO, "3333-12-2444441", "카카오 뱅크", "123");
        paymentService.chargePoint(senderDTO, 10000);
        assert senderDTO.getPaymentBalance() == 10000;
        MemberDTO receiverDTO = memberService.getMember(MemberDTO.builder().id(1L).build());
        paymentService.addPayment(receiverDTO, "3333-14-6444441", "카카오 뱅크", "123");
//        paymentService.transferPoint(senderDTO, receiverDTO);

        PaymentHistoryDTO objectPaymentHistoryDTO = paymentService.requestRefundPoint(senderDTO, 5000);
        PaymentHistoryDTO dbPaymentHistoryDTO  = paymentService.getPaymentHistory(objectPaymentHistoryDTO.getId());
        assert dbPaymentHistoryDTO.getMemberDTO().equals(senderDTO);
        assert dbPaymentHistoryDTO.getPrice() == -5000;
        assert dbPaymentHistoryDTO.getType().equals(PaymentHistoryType.REFUND_REQUEST);
        assert memberService.getMember(senderDTO).getPaymentBalance() == 5000;

        paymentService.successRefundPoint(dbPaymentHistoryDTO);
        assert dbPaymentHistoryDTO.getType().equals(PaymentHistoryType.REFUND_SUCCESS);

        assert paymentService.validatePaymentPw(senderDTO, "123");
        assert !paymentService.validatePaymentPw(senderDTO, "456");

        assert paymentService.listPaymentHistory(senderDTO, PageRequest.of(0, 9), PaymentHistoryType.TRANSFER).size() == 0;
        assert paymentService.listPaymentHistory(senderDTO, PageRequest.of(0, 9), PaymentHistoryType.REFUND_SUCCESS).size() == 1;
    }
}
