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
    public Long id;
    public String reportTitle;
    public String reportContent;
    public String answerTitle;
    public String answerContent;
    public String categoryName;
    public Long categoryId;
    public LocalDateTime regDate;
    public Member reporter;


    public ReportDTO() {
    }

    public static ReportDTO fromEntity(Report report) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(report.getId());
        reportDTO.setReportTitle(report.getReportTitle());
        reportDTO.setReportContent(report.getReportContent());
        reportDTO.setAnswerTitle(report.getAnswerTitle());
        reportDTO.setAnswerContent(report.getAnswerContent());
        reportDTO.setCategoryId(report.getCategoryId());
        reportDTO.setCategoryName(report.getCategoryName());
        reportDTO.setRegDate(report.getRegDate());
        reportDTO.setReporter(report.getReporter());

        return reportDTO;
    }

    public Report toEntity() {
        Report report = new Report();
        report.setId(id);
        report.setReportTitle(reportTitle);
        report.setReportContent(reportContent);
        report.setAnswerTitle(answerTitle);
        report.setAnswerContent(answerContent);
        report.setCategoryId(categoryId);
        report.setCategoryName(categoryName);
        report.setReporter(reporter);
        return report;
    }

}