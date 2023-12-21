package com.fiveguys.koguma.service.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiveguys.koguma.data.dto.ChatroomDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.PaymentHistoryDTO;
import com.fiveguys.koguma.data.entity.PaymentHistory;
import com.fiveguys.koguma.data.entity.PaymentHistoryType;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    void addPayment(MemberDTO memberDTO, String account, String bank, String password);
    void deletePayment(MemberDTO memberDTO);
    boolean existPayment(MemberDTO memberDTO);
    PaymentHistoryDTO addPaymentHistory(MemberDTO memberDTO, UUID id, PaymentHistoryType type, Integer point, String info);
    PaymentHistoryDTO addPaymentHistory(MemberDTO memberDTO, PaymentHistoryType type, Integer point, String info);
    void transferPoint(MemberDTO senderDTO, MemberDTO receiverDTO, ChatroomDTO chatRoomDTO, Integer point) throws JsonProcessingException;
    PaymentHistoryDTO chargePoint(MemberDTO memberDTO, UUID id, Integer point);
    PaymentHistoryDTO requestRefundPoint(MemberDTO memberDTO, Integer point);
    void successRefundPoint(PaymentHistoryDTO paymentHistoryDTO);
    boolean validatePaymentPw(MemberDTO memberDTO, String password);
    void deletePaymentHistory(PaymentHistoryDTO paymentHistoryDTO);
    void updatePaymentHistory(PaymentHistoryDTO paymentHistoryDTO, PaymentHistoryType type);
    PaymentHistoryDTO getPaymentHistory(UUID id);
    List<PaymentHistoryDTO> listPaymentHistory(MemberDTO memberDTO, Pageable pageable, String type);
    List<PaymentHistoryDTO> listPaymentHistory(MemberDTO memberDTO, Pageable pageable);
    String getPortOneToken() throws JsonProcessingException;
    String getPortOneAccountName(String accessToken, String account, String code) throws JsonProcessingException;
    boolean checkPortOneAccountName(String name, String account, String code) throws JsonProcessingException;
    boolean checkPortOneChargeSuccess(MemberDTO memberDTO, String impUid, String merchantUid) throws Exception;
}
