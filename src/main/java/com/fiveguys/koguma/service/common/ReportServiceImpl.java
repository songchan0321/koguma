package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.ReportDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Report;
import com.fiveguys.koguma.repository.common.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional

public class ReportServiceImpl implements ReportService {



    private final ReportRepository reportRepository;

    @Override
    public void addReport(ReportDTO reportDTO, Member reporterNickname, String reportId, String reportContent){

    }
    @Override
    public void deleteReport(ReportDTO reportDTO){

    }

    @Override
    public ReportDTO getReport(Long id){

        return null;
    }

    @Override
    public List<ReportDTO> listReport(){

        return null;
    }

    @Override
    public List<ReportDTO> listAllReport(){
        /*  List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(ReportDTO::fromEntity)
                .collect(Collectors.toList());*/
        return null;
    }

    public void addAnswer(ReportDTO reportDTO, String answerTitle, String answerContent){
        reportDTO.setReportTitle(answerTitle);
        reportDTO.setReportContent(answerContent);

        reportRepository.save(reportDTO.toEntity());
    }



}
