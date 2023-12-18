package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.SmsDTO;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

public interface SmsService {

    public SingleMessageSentResponse sendSms(SmsDTO smsDTO);
//  public void sendOne(String cellphone);


    public boolean verifySms(SmsDTO smsDTO) throws Exception;
}
