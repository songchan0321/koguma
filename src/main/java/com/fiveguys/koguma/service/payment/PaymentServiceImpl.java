package com.fiveguys.koguma.service.payment;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.dto.PaymentHistoryDTO;
import com.fiveguys.koguma.data.entity.PaymentHistory;
import com.fiveguys.koguma.data.entity.PaymentHistoryType;
import com.fiveguys.koguma.repository.payment.PaymentHistoryRepository;
import com.fiveguys.koguma.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        // password 암호화 안함
        memberDTO.setPaymentPw(password);
//        memberService.updateMember(memberDTO);
    }

    @Override
    public void deletePayment(MemberDTO memberDTO) {
        memberDTO.setPaymentAccount(null);
        memberDTO.setPaymentBank(null);
        memberDTO.setPaymentBalance(null);
        memberDTO.setPaymentPw(null);
//        memberService.updateMember(memberDTO);
    }

    @Override
    public void addPaymentHistory(MemberDTO memberDTO, PaymentHistoryType type, Integer point, String info) {

    }

    @Override
    public void transferPoint(MemberDTO senderDTO, MemberDTO receiverDTO, Integer point) {
        senderDTO.setPaymentBalance(senderDTO.getPaymentBalance() - point);
        receiverDTO.setPaymentBalance(senderDTO.getPaymentBalance() + point);
//        memberService.updateMember(senderDTO);
//        memberService.updateMember(receiverDTO);
        this.ad
    }

    @Override
    public void chargePoint(MemberDTO memberDTO, Integer point) {

    }

    @Override
    public void requestRefundPoint(MemberDTO memberDTO, Integer point) {

    }

    @Override
    public void successRefundPoint(PaymentHistoryDTO paymentHistoryDTO) {

    }

    @Override
    public boolean validatePaymentPw(MemberDTO memberDTO, String password) {
        return false;
    }

    @Override
    public void addPaymentHistory(PaymentHistoryDTO paymentHistoryDTO) {

    }

    @Override
    public void deletePaymentHistory(PaymentHistoryDTO paymentHistoryDTO) {

    }

    @Override
    public void updatePaymentHistory(PaymentHistoryDTO paymentHistoryDTO) {

    }

    @Override
    public void listPaymentHistory(MemberDTO memberDTO) {

    }
}
