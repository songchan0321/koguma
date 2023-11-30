package com.fiveguys.koguma.common;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ReportDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.service.common.ReportService;
import com.fiveguys.koguma.service.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ReportApplicationTests {

    @Autowired
    private ReportService reportService;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("신고 추가 테스트")
    void addReportTest() {
        // Given
        Member reporterId = addMember("reporterUser");
        String reportTitle = "Test Report Title";
        String reportContent = "Test Report Content";

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setReporterId(reporterId);
        reportDTO.setReportTitle(reportTitle);
        reportDTO.setReportContent(reportContent);

        // When
        reportService.addReport(reportDTO, reporterId, "reportId", reportTitle, reportContent);

        // Then
        ReportDTO addedReport = reportService.getReport(reportDTO.getId());
        assertAll(
                () -> assertEquals(reporterId, addedReport.getReporterId()),
                () -> assertEquals(reportTitle, addedReport.getReportTitle()),
                () -> assertEquals(reportContent, addedReport.getReportContent())
        );
    }

    @Test
    @DisplayName("신고 삭제 테스트")
    void deleteReportTest() {
        // Given
        Member reporterId = addMember("reporterUser");
        String reportTitle = "Test Report Title";
        String reportContent = "Test Report Content";

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setReporterId(reporterId);
        reportDTO.setReportTitle(reportTitle);
        reportDTO.setReportContent(reportContent);
        reportService.addReport(reportDTO, reporterId, "reportId", reportTitle, reportContent);

        // When
        reportService.deleteReport(reportDTO.getId());

        // Then
        assertThrows(RuntimeException.class, () -> reportService.getReport(reportDTO.getId()));
    }

    @Test
    @DisplayName("특정 회원의 신고 목록 조회 테스트")
    void listReportTest() {
        // Given
        Member reporterId = addMember("reporterUser");
        Member otherMember = addMember("otherUser");

        String reportTitle = "Test Report Title";
        String reportContent = "Test Report Content";

        ReportDTO reportDTO1 = new ReportDTO();
        reportDTO1.setReporterId(reporterId);
        reportDTO1.setReportTitle(reportTitle);
        reportDTO1.setReportContent(reportContent);
        reportService.addReport(reportDTO1, reporterId, "reportId", reportTitle, reportContent);

        ReportDTO reportDTO2 = new ReportDTO();
        reportDTO2.setReporterId(otherMember);
        reportDTO2.setReportTitle(reportTitle);
        reportDTO2.setReportContent(reportContent);
        reportService.addReport(reportDTO2, otherMember, "reportId", reportTitle, reportContent);

        // When
        List<ReportDTO> reportList = reportService.listReport(reporterId.getId());

        // Then
        assertEquals(1, reportList.size());
        assertEquals(reportDTO1.getId(), reportList.get(0).getId());
    }

    private Member addMember(String nickname) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setNickname(nickname);
        memberDTO.setPw("password");
        memberDTO.setPhone("010-1234-5678");
        memberDTO.setScore(36.5F);
        memberDTO.setEmail(nickname + "@example.com");
        memberDTO.setRoleFlag(false);
        memberDTO.setSocialFlag(false);
        memberService.addMember(memberDTO, nickname, "password", "010-1234-5678", 36.5F, nickname + "@example.com", false, false);

        return null;
    }
}
