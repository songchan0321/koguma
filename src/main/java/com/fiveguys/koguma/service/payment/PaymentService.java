package com.fiveguys.koguma.service.payment;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PaymentHistoryDTO;
import com.fiveguys.koguma.data.entity.PaymentHistory;
import com.fiveguys.koguma.data.entity.PaymentHistoryType;

public interface PaymentService {
    void addPayment(MemberDTO memberDTO, String account, String bank, String password);
    void deletePayment(MemberDTO memberDTO);
    void addPaymentHistory(MemberDTO memberDTO, PaymentHistoryType type, Integer point, String info);
    void transferPoint(MemberDTO senderDTO, MemberDTO receiverDTO, Integer point);
    void chargePoint(MemberDTO memberDTO, Integer point);
    void requestRefundPoint(MemberDTO memberDTO, Integer point);
    void successRefundPoint(PaymentHistoryDTO paymentHistoryDTO);
    boolean validatePaymentPw(MemberDTO memberDTO, String password);
    void addPaymentHistory(PaymentHistoryDTO paymentHistoryDTO);
    void deletePaymentHistory(PaymentHistoryDTO paymentHistoryDTO);
    void updatePaymentHistory(PaymentHistoryDTO paymentHistoryDTO);
    void listPaymentHistory(MemberDTO memberDTO);
}
