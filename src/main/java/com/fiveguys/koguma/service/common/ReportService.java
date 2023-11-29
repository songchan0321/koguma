package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.ReportDTO;
import com.fiveguys.koguma.data.entity.Member;

import java.util.List;
public interface ReportService {

    void addReport(ReportDTO reportDTO, Member reporterNickname, String reportId, String reportContent);
    void deleteReport(ReportDTO reportDTO);
    ReportDTO getReport(Long id);
    List<ReportDTO> listReport();
    List<ReportDTO> listAllReport();
    void addAnswer(ReportDTO reportDTO, String answerTitle, String answerContent);



}
