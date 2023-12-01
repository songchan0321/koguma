package com.fiveguys.koguma.data.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@Table(name = "customer_service")
@NoArgsConstructor()


public class Report extends BaseTime {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id",nullable = false)
    private Member reporterId;

    @Column(name = "category_id",nullable = false)
    private Long categoryId;

    @Column(name = "report_title", nullable = false)
    private String reportTitle;

    @Column(name = "report_content", nullable = false)
    private String reportContent;

    @Column(name = "report_number", nullable = false)
    private Integer reportNumber;

    @Column(name = "answer_title")
    private String answerTitle;

    @Column(name = "answer_content")
    private String answerContent;

    @Column(name = "category_name",nullable = false,length=15)
    private String categoryName;

    @Builder
    public Report(Long id, Member reporterId, String categoryName, Long categoryId, String reportTitle, String reportContent, Integer reportNumber, String answerTitle, String answerContent) {
        this.id = id;
        this.reporterId = reporterId;
        this.reportTitle = reportTitle;
        this.reportContent = reportContent;
        this.reportNumber = reportNumber;
        this.answerTitle = answerTitle;
        this.answerContent = answerContent;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }



}
