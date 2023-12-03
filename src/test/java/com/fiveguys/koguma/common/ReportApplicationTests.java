package com.fiveguys.koguma.common;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ReportDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Report;
import com.fiveguys.koguma.service.common.ReportService;
import com.fiveguys.koguma.service.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class ReportApplicationTests {

    @Autowired
    private ReportService reportService;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("신고 추가 테스트")
    @Transactional
    public void addReportTest() throws Exception {

        Member reporter = memberService.getMember(2L).toEntity();

        Report report = Report.builder()
                .reporter(reporter)
                .reportTitle("신고요")
                .reportContent("말대꾸 하지마!!!")
                .answerTitle(null)
                .answerContent(null)
                .categoryId(51L)
                .categoryName("회원")
                .build();
        reportService.addReport(ReportDTO.fromEntity(report));
    }

    /*@Test
    @DisplayName("신고 삭제 테스트")
    @Transactional
    public void deleteReportTest() {
        // Given
        Member reporter = memberService.getMember(2L).toEntity();

        Report report = Report.builder()
                .reporter(reporter)
                .reportTitle("신고요")
                .reportContent("말대꾸 하지마!!!")
                .answerTitle(null)
                .answerContent(null)
                .categoryId(51L)
                .categoryName("회원")
                .build();
        reportService.addReport(ReportDTO.fromEntity(report));

        // When
        reportService.deleteReport(report.getId());

        // Then
        assertThrows(NoSuchElementException.class, () -> reportService.getReport(report.getId()));

        // Additional verification to check if reporterId is null
        Report deletedReport = reportService.getReport(report.getId()).toEntity();
        assertNull(deletedReport.getReporter(), "Reporter ID should be null after deletion");
    }*/
    @Test
    @DisplayName("신고 상세 조회 테스트")
    @Transactional
    public void getReportTest(){
        System.out.println((reportService.getReport(12L).toString()));
    }

    @Test
    @DisplayName("특정 회원의 신고 목록 조회 테스트")
    @Transactional
    public void listReportTest() {


        List<ReportDTO> reports = reportService.listReport(2L);

        assertEquals(2, reports.size());
    }

    @Test
    @DisplayName("모든 신고 목록 조회 테스트")
    @Transactional
    public void listAllReportTest(){
        List<ReportDTO> reports = reportService.listAllReport();

    }
    @Test
    @DisplayName("답변 등록 테스트")
    @Transactional
    public void addAnswerTest(){


    }

}
