package com.fiveguys.koguma.service.payment;

import com.fiveguys.koguma.data.dto.ChatroomDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PaymentHistoryDTO;
import com.fiveguys.koguma.data.entity.PaymentHistory;
import com.fiveguys.koguma.data.entity.PaymentHistoryType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {
    void addPayment(MemberDTO memberDTO, String account, String bank, String password);
    void deletePayment(MemberDTO memberDTO);
    PaymentHistoryDTO addPaymentHistory(MemberDTO memberDTO, PaymentHistoryType type, Integer point, String info);
    void transferPoint(MemberDTO senderDTO, MemberDTO receiverDTO, ChatroomDTO chatRoomDTO, Integer point);
    PaymentHistoryDTO chargePoint(MemberDTO memberDTO, Integer point);
    PaymentHistoryDTO requestRefundPoint(MemberDTO memberDTO, Integer point);
    void successRefundPoint(PaymentHistoryDTO paymentHistoryDTO);
    boolean validatePaymentPw(MemberDTO memberDTO, String password);
    void deletePaymentHistory(PaymentHistoryDTO paymentHistoryDTO);
    void updatePaymentHistory(PaymentHistoryDTO paymentHistoryDTO, PaymentHistoryType type);
    PaymentHistoryDTO getPaymentHistory(Long id);
    List<PaymentHistory> listPaymentHistory(MemberDTO memberDTO, Pageable pageable, PaymentHistoryType type);
    List<PaymentHistory> listPaymentHistory(MemberDTO memberDTO, Pageable pageable);
}
