package com.fiveguys.koguma.repository.common;

import com.fiveguys.koguma.data.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    //List<Report> findByReporterId(Long reporterId);

    List<Report> findAllByReporterId(Long reporterId);

    Optional<Report> findByReporterId(Long reporterId);
}