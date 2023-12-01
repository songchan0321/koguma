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
    private Member reporterId;
    private String reportTitle;
    private String reportContent;
    private Integer reportNumber;
    private String answerTitle;
    private String answerContent;
    private String categoryName;
    private Long categoryId;


    public ReportDTO() {
    }

    public static ReportDTO fromEntity(Report report) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(report.getId());
        reportDTO.setReporterId(report.getReporterId());
        reportDTO.setReportTitle(report.getReportTitle());
        reportDTO.setReportContent(report.getReportContent());
        reportDTO.setReportNumber(report.getReportNumber());
        reportDTO.setAnswerTitle(report.getAnswerTitle());
        reportDTO.setAnswerContent(report.getAnswerContent());
        reportDTO.setCategoryId(report.getCategoryId());
        reportDTO.setCategoryName(String.valueOf(report.getCategoryId()));

        return reportDTO;
    }

    public Report toEntity() {
        Report report = new Report();
        report.setId(id);
        report.setReporterId(reporterId);
        report.setReportTitle(reportTitle);
        report.setReportContent(reportContent);
        report.setReportNumber(reportNumber);
        report.setAnswerTitle(answerTitle);
        report.setReportContent(answerContent);
        report.setCategoryId(categoryId);
        report.setCategoryName(categoryName);
        return report;
    }
}