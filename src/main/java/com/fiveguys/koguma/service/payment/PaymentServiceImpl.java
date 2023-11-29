package com.fiveguys.koguma.service.payment;

import com.fiveguys.koguma.data.dto.ChatroomDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.dto.PaymentHistoryDTO;
import com.fiveguys.koguma.data.entity.PaymentHistory;
import com.fiveguys.koguma.data.entity.PaymentHistoryType;
import com.fiveguys.koguma.repository.payment.PaymentHistoryRepository;
import com.fiveguys.koguma.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService{
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final MemberService memberService;

    @Override
    public void addPayment(MemberDTO memberDTO, String account, String bank, String password) {
        memberDTO.setPaymentAccount(account);
        memberDTO.setPaymentBank(bank);
        memberDTO.setPaymentBalance(0);
        // password 암호화 해야함
        memberDTO.setPaymentPw(password);
        memberService.updateMember(memberDTO);
    }

    @Override
    public void deletePayment(MemberDTO memberDTO) {
        memberDTO.setPaymentAccount(null);
        memberDTO.setPaymentBank(null);
        memberDTO.setPaymentBalance(null);
        memberDTO.setPaymentPw(null);
        memberService.updateMember(memberDTO);
    }

    @Override
    public PaymentHistoryDTO addPaymentHistory(MemberDTO memberDTO, PaymentHistoryType type, Integer point, String info) {
        PaymentHistoryDTO paymentHistoryDTO = PaymentHistoryDTO.builder()
                .type(type)
                .price(point)
                .info(info)
                .memberDTO(memberDTO)
                .build();
        PaymentHistory paymentHistory = paymentHistoryDTO.toEntity();
        return PaymentHistoryDTO.fromEntity(paymentHistoryRepository.save(paymentHistory));
    }

    @Override
    public void transferPoint(MemberDTO senderDTO, MemberDTO receiverDTO, ChatroomDTO chatRoomDTO, Integer point) {
        senderDTO.setPaymentBalance(senderDTO.getPaymentBalance() - point);
        receiverDTO.setPaymentBalance(receiverDTO.getPaymentBalance() + point);
        memberService.updateMember(senderDTO);
        memberService.updateMember(receiverDTO);
        this.addPaymentHistory(senderDTO, PaymentHistoryType.TRANSFER, -1 * point, chatRoomDTO.getProductDTO().getTitle());
        this.addPaymentHistory(receiverDTO, PaymentHistoryType.TRANSFER, point, chatRoomDTO.getProductDTO().getTitle());
    }

    @Override
    public PaymentHistoryDTO chargePoint(MemberDTO memberDTO, Integer point) {
        memberDTO.setPaymentBalance(memberDTO.getPaymentBalance() + point);
        memberService.updateMember(memberDTO);
        return this.addPaymentHistory(memberDTO, PaymentHistoryType.CHARGE, point, "카카오 페이");
    }

    @Override
    public PaymentHistoryDTO requestRefundPoint(MemberDTO memberDTO, Integer point) {
        memberDTO.setPaymentBalance(memberDTO.getPaymentBalance() - point);
        memberService.updateMember(memberDTO);
        return this.addPaymentHistory(memberDTO, PaymentHistoryType.REFUND_REQUEST, -1 * point, memberDTO.getPaymentBank() + " " + memberDTO.getPaymentAccount());
    }

    @Override
    public void successRefundPoint(PaymentHistoryDTO paymentHistoryDTO) {
        this.updatePaymentHistory(paymentHistoryDTO, PaymentHistoryType.REFUND_SUCCESS);
    }

    @Override
    public boolean validatePaymentPw(MemberDTO memberDTO, String password) {
        return memberDTO.getPaymentPw().equals(password);
    }

    @Override
    public void deletePaymentHistory(PaymentHistoryDTO paymentHistoryDTO) {
        if(paymentHistoryDTO.getType().equals(PaymentHistoryType.REFUND_REQUEST)) {
            paymentHistoryDTO.getMemberDTO().setPaymentBalance(paymentHistoryDTO.getMemberDTO().getPaymentBalance() - paymentHistoryDTO.getPrice());
            memberService.updateMember(paymentHistoryDTO.getMemberDTO());
        }
        paymentHistoryRepository.deleteById(paymentHistoryDTO.getId());
    }

    @Override
    public PaymentHistoryDTO getPaymentHistory(Long id) {
        PaymentHistoryDTO paymentHistoryDTO = PaymentHistoryDTO.fromEntity(paymentHistoryRepository.findById(id).orElseThrow());
        return paymentHistoryDTO;
    }

    @Override
    public void updatePaymentHistory(PaymentHistoryDTO paymentHistoryDTO, PaymentHistoryType type) {
        paymentHistoryDTO.setType(type);
        PaymentHistory paymentHistory = paymentHistoryDTO.toEntity();
        paymentHistoryRepository.save(paymentHistory);
    }

    @Override
    public List<PaymentHistory> listPaymentHistory(MemberDTO memberDTO, Pageable pageable, PaymentHistoryType type) {
        return paymentHistoryRepository.findAll(PaymentHistorySpecifications.hasType(memberDTO, type), pageable).toList();
    }

    @Override
    public List<PaymentHistory> listPaymentHistory(MemberDTO memberDTO, Pageable pageable) {
        return this.listPaymentHistory(memberDTO, pageable, null);
    }
}
