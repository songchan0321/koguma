package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ReportDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Report;
import com.fiveguys.koguma.repository.common.ReportRepository;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
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
    public void addReport(ReportDTO reportDTO){

        reportRepository.save(reportDTO.toEntity());
    }
    @Override
    public void deleteReport(@CurrentMember MemberDTO authenticatedMember) {
        Long reporterId = authenticatedMember.getId();

        Optional<Report> reportOptional = reportRepository.findByReporterId(reporterId);

        if (reportOptional.isPresent()) {
            Report report = reportOptional.get();
            reportRepository.delete(report);
        } else {
            throw new NoResultException("해당 신고를 찾을 수 없습니다.");
        }
    }

    @Override
    public ReportDTO getReport(@CurrentMember MemberDTO authenticatedMember) {
        Long reporterId = authenticatedMember.getId();

        Optional<Report> reportOptional = reportRepository.findByReporterId(reporterId);

        if (reportOptional.isPresent()) {
            Report report = reportOptional.get();
            return ReportDTO.fromEntity(report);
        } else {
            throw new NoResultException("해당 신고를 찾을 수 없습니다.");
        }
    }

    @Override
    public List<ReportDTO> listReport(Long reporterId) {
        List<Report> reports = reportRepository.findAllByReporterId(reporterId);

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
    public void addAnswer(ReportDTO reportDTO){

        reportRepository.save(reportDTO.toEntity());
    }

}
