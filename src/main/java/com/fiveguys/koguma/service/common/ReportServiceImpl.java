package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.ReportDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.Report;
import com.fiveguys.koguma.repository.common.ReportRepository;
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
    public void deleteReport(Long id) {

        Optional<Report> reportOptional = reportRepository.findById(id);

        if (reportOptional.isPresent()) {
            Report report = reportOptional.get();
            reportRepository.delete(report);
        }
    }

    @Override
    public ReportDTO getReport(Long id){
        Report report = reportRepository.findById(id).orElseThrow(()-> new NoResultException("신고 정보 없음"));
        return ReportDTO.fromEntity(report);
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
