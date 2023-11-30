package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.ReportDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Report;
import com.fiveguys.koguma.repository.common.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional

public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    public void addReport(ReportDTO reportDTO, Member reporterId, String reportId, String reportTitle, String reportContent){
        reportDTO.setReporterId(reporterId);
        reportDTO.setReportTitle(reportTitle);
        reportDTO.setReportContent(reportContent);

        reportRepository.save(reportDTO.toEntity());

    }
    @Override
    public void deleteReport(Long id) {

        Optional<Report> reportOptional = reportRepository.findById(id);

        if (reportOptional.isPresent()) {
            Report report = reportOptional.get();
            reportRepository.delete(report);
        }
    }

    @Override
    public ReportDTO getReport(Long id){
        return ReportDTO.fromEntity(reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 신고가 존재하지 않습니다.")));
    }

    @Override
    public List<ReportDTO> listReport(Long id) {
        List<Report> reports = reportRepository.findByReporterId(id);
        return reports.stream()
                .map(ReportDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> listAllReport(){
          List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(ReportDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void addAnswer(ReportDTO reportDTO, String answerTitle, String answerContent){
        reportDTO.setReportTitle(answerTitle);
        reportDTO.setReportContent(answerContent);

        reportRepository.save(reportDTO.toEntity());
    }



}
