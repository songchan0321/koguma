package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.ReportDTO;
import com.fiveguys.koguma.service.common.ReportService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReportRestController {
    private final ReportService reportService;

    @PostMapping("/report/add")
    public void addReport(@RequestBody ReportDTO reportDTO){
        reportService.addReport(reportDTO);
    }
    @DeleteMapping("/report/delete")
    public void deleteReport(@RequestBody Long id){
        reportService.deleteReport(id);
    }
    @GetMapping("/report/get/{id}")
    public void getReport(@PathVariable Long id){
        reportService.getReport(id);
    }
    @GetMapping("/report/List/{reporterId}")
    public List<ReportDTO> ListReport(@PathVariable Long reporterId){
        return reportService.listReport(reporterId);
    }

    @GetMapping("/report/ListAll")
    public List<ReportDTO> ListAllReport() {
        return null;
    }

}
