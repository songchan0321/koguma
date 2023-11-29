package com.fiveguys.koguma.data.dto;

import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
public class ReportDTO {
    private Long id;
    private Member reporterNickname;
    private String reportTitle;
    private String reportContent;
    private Integer reportNumber;
    private String answerTitle;
    private String answerContent;


    public ReportDTO() {
    }

    public static ReportDTO fromEntity(Report report) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(report.getId());
        reportDTO.setReporterNickname(report.getReporterNickname());
        reportDTO.setReportTitle(report.getReportTitle());
        reportDTO.setReportContent(report.getReportContent());
        reportDTO.setReportNumber(report.getReportNumber());
        reportDTO.setAnswerTitle(report.getAnswerTitle());
        reportDTO.setAnswerContent(report.getAnswerContent());

        return reportDTO;
    }

    public Report toEntity() {
        Report report = new Report();
        report.setId(id);
        report.setReporterNickname(reporterNickname);
        report.setReportTitle(reportTitle);
        report.setReportContent(reportContent);
        report.setReportNumber(reportNumber);
        report.setAnswerTitle(answerTitle);
        report.setReportContent(answerContent);
        return report;
    }
}

