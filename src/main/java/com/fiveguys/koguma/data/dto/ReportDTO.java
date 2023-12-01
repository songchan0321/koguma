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
    private MemberDTO reporterDTO;
    private String reportTitle;
    private String reportContent;
    private String answerTitle;
    private String answerContent;
    private String categoryName;
    private Long categoryId;
    private LocalDateTime regDate;


    public ReportDTO() {
    }

    public static ReportDTO fromEntity(Report report) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(report.getId());
        reportDTO.setReporterDTO(MemberDTO.fromEntity(report.getReporter()));
        reportDTO.setReportTitle(report.getReportTitle());
        reportDTO.setReportContent(report.getReportContent());
        reportDTO.setAnswerTitle(report.getAnswerTitle());
        reportDTO.setAnswerContent(report.getAnswerContent());
        reportDTO.setCategoryId(report.getCategoryId());
        reportDTO.setCategoryName(report.getCategoryName());
        reportDTO.setRegDate(report.getRegDate());

        return reportDTO;
    }

    public Report toEntity() {
        Report report = new Report();
        report.setId(id);
        report.setReporter(reporterDTO.toEntity());
        report.setReportTitle(reportTitle);
        report.setReportContent(reportContent);
        report.setAnswerTitle(answerTitle);
        report.setAnswerContent(answerContent);
        report.setCategoryId(categoryId);
        report.setCategoryName(categoryName);
        return report;
    }

}