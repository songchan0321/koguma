package com.fiveguys.koguma.service.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveguys.koguma.data.dto.ChatroomDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.MemberRelationshipDTO;
import com.fiveguys.koguma.data.dto.PaymentHistoryDTO;
import com.fiveguys.koguma.data.entity.PaymentHistory;
import com.fiveguys.koguma.data.entity.PaymentHistoryType;
import com.fiveguys.koguma.repository.payment.PaymentHistoryRepository;
import com.fiveguys.koguma.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService{
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final MemberService memberService;
    private final ObjectMapper objectMapper;
    @Value("${portone.imp-key}")
    private String IMP_KEY;
    @Value("${portone.imp-secret}")
    private String IMP_SECRET;
    private final String API_URL = "https://api.iamport.kr/";

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
    public boolean existPayment(MemberDTO memberDTO) {
        return memberDTO.getPaymentBalance() != null;
    }

    @Override
    public PaymentHistoryDTO addPaymentHistory(MemberDTO memberDTO, UUID id, PaymentHistoryType type, Integer point, String info) {
        PaymentHistoryDTO paymentHistoryDTO = PaymentHistoryDTO.builder()
                .id(id)
                .type(type)
                .price(point)
                .info(info)
                .memberDTO(memberDTO)
                .build();
        PaymentHistory paymentHistory = paymentHistoryDTO.toEntity();
        return PaymentHistoryDTO.fromEntity(paymentHistoryRepository.save(paymentHistory));
    }

    @Override
    public PaymentHistoryDTO addPaymentHistory(MemberDTO memberDTO, PaymentHistoryType type, Integer point, String info) {
        return this.addPaymentHistory(memberDTO, null, type, point, info);
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
    public PaymentHistoryDTO chargePoint(MemberDTO memberDTO, UUID id, Integer point) {
        memberDTO.setPaymentBalance(memberDTO.getPaymentBalance() + point);
        memberService.updateMember(memberDTO);
        return this.addPaymentHistory(memberDTO, id, PaymentHistoryType.CHARGE, point, "카카오 페이");
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
    public PaymentHistoryDTO getPaymentHistory(UUID id) {
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
    public List<PaymentHistoryDTO> listPaymentHistory(MemberDTO memberDTO, Pageable pageable, String type) {
        return paymentHistoryRepository.findAll(PaymentHistorySpecifications.hasType(memberDTO, type), pageable).map(PaymentHistoryDTO::fromEntity).toList();
    }

    @Override
    public List<PaymentHistoryDTO> listPaymentHistory(MemberDTO memberDTO, Pageable pageable) {
        return this.listPaymentHistory(memberDTO, pageable, null);
    }

    @Override
    public String getPortOneToken() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> json =  Map.of("imp_key", IMP_KEY, "imp_secret", IMP_SECRET);
        String requestBody = objectMapper.writeValueAsString(json);
        // HttpEntity를 사용하여 바디에 데이터를 담음
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        // POST 요청 보내기
        ResponseEntity<String> response = restTemplate.postForEntity(API_URL +"users/getToken", requestEntity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Error: " + response.getStatusCode());
        }
        String responseBody = response.getBody();
        JsonNode rootNode = objectMapper.readTree(responseBody);

        JsonNode accessTokenNode = rootNode.path("response").path("access_token");
        String accessToken = accessTokenNode.asText();
        return accessToken;
    }

    @Override
    public String getPortOneAccountName(String accessToken, String account, String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(API_URL +"vbanks/holder?bank_code=" + code + "&bank_num=" + account, HttpMethod.GET, requestEntity ,String.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Error: " + response.getStatusCode());
        }
        String responseBody = response.getBody();
        JsonNode rootNode = objectMapper.readTree(responseBody);

        JsonNode accessTokenNode = rootNode.path("response").path("bank_holder");
        String name = accessTokenNode.asText();
        return name;
    }

    @Override
    public boolean checkPortOneAccountName(String name, String account, String code) throws JsonProcessingException {
        String accessToken = this.getPortOneToken();
        return name.equals(this.getPortOneAccountName(accessToken, account, code));
    }

    @Override
    public boolean checkPortOneChargeSuccess(MemberDTO memberDTO, String impUid, String merchantUid) throws Exception {
        String accessToken = this.getPortOneToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(API_URL +"payments/" + impUid, HttpMethod.GET, requestEntity ,String.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new Exception("결제 오류");
        }
        String responseBody = response.getBody();
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode amountNode = rootNode.path("response").path("amount");
        JsonNode merchantUidNode = rootNode.path("response").path("merchant_uid");
        JsonNode buyerNameNode = rootNode.path("response").path("buyer_name");
        JsonNode statusNode = rootNode.path("response").path("status");

        if(
                paymentHistoryRepository.existsById(UUID.fromString(merchantUid))
                || !merchantUidNode.asText().equals(merchantUid)
                || !buyerNameNode.asText().equals(memberDTO.getNickname())
                || !statusNode.asText().equals("paid"))
        {
            throw new Exception("비정상적인 접근");
        }
        this.chargePoint(memberDTO, UUID.fromString(merchantUid), Integer.parseInt(amountNode.asText()));
        return true;
    }
}
