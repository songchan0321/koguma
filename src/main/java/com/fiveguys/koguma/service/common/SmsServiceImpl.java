package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.SmsDTO;
import com.fiveguys.koguma.repository.common.SmsRepository;
import javax.annotation.PostConstruct;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Log4j2
@Service
public class SmsServiceImpl implements SmsService {

    @Value("${coolsms.api.key}")
    private String apiKey;
    @Value("${coolsms.api.secret}")
    private String apiSecretKey;
    @Value("${coolsms.sender.number}")
    private String senderNumber;
    @Value("${sender.text}")
    private String messageTest;


    private final SmsRepository smsCertification;
    private DefaultMessageService messageService;

    private final SmsRepository smsCertificationRepository;

    @PostConstruct
    private void init(){
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    // 단일 메시지 발송 예제
    @Override
    public SingleMessageSentResponse sendSms(SmsDTO smsDTO) {
        Message message = new Message();
        smsDTO.setAuthNumber(generateRandomCode(6));
        // 발신번호 및 수신번호는 반드시 01012345678 형태.
        message.setFrom(senderNumber);
        message.setTo(smsDTO.getTo());
        message.setText(messageTest +" "+ smsDTO.getAuthNumber());
        System.out.println(message.getFrom());System.out.println(message.getText());System.out.println(message.getTo());
        try {
            SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
            System.out.println(smsDTO);
            // DB에 발송한 인증번호 저장
            smsCertification.addAuthNum(smsDTO);

            return response;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
//            throw new Exception("뭐냐 이거");
        }

    }


    private String generateRandomCode(int length) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10)); // 0부터 9까지의 랜덤한 숫자
        }
        return code.toString();
    }

    //사용자가 입력한 인증번호가 Redis에 저장된 인증번호와 동일한지 확인
    @Override
    public boolean verifySms(SmsDTO smsDTO) throws Exception {
        if (!(smsCertificationRepository.isAuthNum(smsDTO.getTo()) &&
                smsCertificationRepository.getAuthNum(smsDTO.getTo())
                        .equals(smsDTO.getAuthNumber()))) {
            throw new Exception("인증번호가 일치하지 않습니다.");
        }

        smsCertificationRepository.deleteAuthNum(smsDTO.getTo());

        return true;
    }

}