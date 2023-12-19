package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.SmsDTO;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

public interface SmsService {

    SingleMessageSentResponse sendSms(SmsDTO smsDTO);

    boolean verifySms(SmsDTO smsDTO) throws Exception;
}
