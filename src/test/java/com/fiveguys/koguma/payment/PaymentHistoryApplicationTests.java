package com.fiveguys.koguma.payment;

import com.fiveguys.koguma.data.dto.ChatroomDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PaymentHistoryDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.data.entity.PaymentHistoryType;
import com.fiveguys.koguma.service.chat.ChatService;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.payment.PaymentService;
import com.fiveguys.koguma.service.product.ProductService;
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
    @Autowired private ProductService productService;
    @Autowired private ChatService chatService;
    @Test
    public void addPaymentTest() {
        MemberDTO memberDTO = memberService.getMember(1L);
        System.out.println(memberDTO);
        paymentService.addPayment(memberDTO, "3333-12-2444441", "카카오 뱅크", "123");
        MemberDTO newMemberDTO = memberService.getMember(1L);
        assert newMemberDTO.getPaymentAccount().equals(memberDTO.getPaymentAccount());
        assert newMemberDTO.getPaymentBank().equals(memberDTO.getPaymentBank());
        assert newMemberDTO.getPaymentBalance() == 0;
        assert newMemberDTO.getPaymentPw().equals(memberDTO.getPaymentPw());
    }

    @Test
    public void deletePaymentTest() {
        MemberDTO memberDTO = memberService.getMember(1L);
        paymentService.addPayment(memberDTO, "3333-12-2444441", "카카오 뱅크", "123");
        paymentService.deletePayment(memberDTO);
        MemberDTO newMemberDTO = memberService.getMember(1L);
        assert newMemberDTO.getPaymentAccount() == null;
        assert newMemberDTO.getPaymentBank() == null;
        assert newMemberDTO.getPaymentBalance() == null;
        assert newMemberDTO.getPaymentPw() == null;
    }

    @Test
    public void paymentUsePointTest() {
//        MemberDTO senderDTO = memberService.getMember(1L);
//        paymentService.addPayment(senderDTO, "3333-12-2444441", "카카오 뱅크", "123");
//        paymentService.chargePoint(senderDTO, 10000);
//        assert senderDTO.getPaymentBalance() == 10000;
//
//
//        ProductDTO productDTO = productService.getProduct(1L);
//        ChatroomDTO chatroomDTO = chatService.addChatroom(senderDTO, productDTO);
//        MemberDTO receiverDTO = memberService.getMember(productDTO.getSellerDTO().getId());
//        paymentService.addPayment(receiverDTO, "3333-14-6444441", "카카오 뱅크", "123");
//
//        paymentService.transferPoint(senderDTO, receiverDTO, chatroomDTO, 2000);
//        assert memberService.getMember(senderDTO.getId()).getPaymentBalance() == 8000;
//        System.out.println(receiverDTO.getId());
//        System.out.println(memberService.getMember(receiverDTO.getId()).getPaymentBalance());
//        assert memberService.getMember(receiverDTO.getId()).getPaymentBalance() == 2000;
//
//        PaymentHistoryDTO objectPaymentHistoryDTO = paymentService.requestRefundPoint(senderDTO, 5000);
//        PaymentHistoryDTO dbPaymentHistoryDTO  = paymentService.getPaymentHistory(objectPaymentHistoryDTO.getId());
//        assert dbPaymentHistoryDTO.getMemberDTO().equals(senderDTO);
//        assert dbPaymentHistoryDTO.getPrice() == -5000;
//        assert dbPaymentHistoryDTO.getType().equals(PaymentHistoryType.REFUND_REQUEST);
//        assert memberService.getMember(senderDTO.getId()).getPaymentBalance() == 3000;
//
//        paymentService.successRefundPoint(dbPaymentHistoryDTO);
//        assert dbPaymentHistoryDTO.getType().equals(PaymentHistoryType.REFUND_SUCCESS);
//
//        assert paymentService.validatePaymentPw(senderDTO, "123");
//        assert !paymentService.validatePaymentPw(senderDTO, "456");
//        paymentService.chargePoint(senderDTO, 10000);
//        paymentService.chargePoint(senderDTO, 10000);
//        paymentService.chargePoint(senderDTO, 10000);
//        paymentService.chargePoint(senderDTO, 10000);
//        assert paymentService.listPaymentHistory(senderDTO, PageRequest.of(0, 9), PaymentHistoryType.TRANSFER.name()).size() == 1;
//        assert paymentService.listPaymentHistory(senderDTO, PageRequest.of(0, 9), PaymentHistoryType.CHARGE.name()).size() == 5;
//        assert paymentService.listPaymentHistory(senderDTO, PageRequest.of(0, 9), PaymentHistoryType.REFUND_REQUEST.name()).size() == 0;
//        assert paymentService.listPaymentHistory(senderDTO, PageRequest.of(0, 9), PaymentHistoryType.REFUND_SUCCESS.name()).size() == 1;
//        assert paymentService.listPaymentHistory(senderDTO, PageRequest.of(0, 9)).size() == 7;
    }
}
