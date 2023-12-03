package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.ReportDTO;
import com.fiveguys.koguma.data.entity.Member;

import java.util.List;
public interface ReportService {

    void addReport(ReportDTO reportDTO);
    void deleteReport(Long id);
    ReportDTO getReport(Long id);
    List<ReportDTO> listReport(Long reporterId);
    List<ReportDTO> listAllReport();
    void addAnswer(ReportDTO reportDTO);



}
