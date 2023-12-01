package com.fiveguys.koguma.service.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiveguys.koguma.data.dto.AlertDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;

import java.util.List;

public interface AlertService {
    public Long addAlert(MemberDTO memberDTO, String title, String content, String url) throws JsonProcessingException;
    public List<AlertDTO> listAlert(MemberDTO memberDTO);
    public void readAlert(MemberDTO memberDTO, String id);
    public void readAlertAll(MemberDTO memberDTO);
}
