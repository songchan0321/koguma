package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.ReportDTO;
import com.fiveguys.koguma.service.common.ReportService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
public class ReportRestController {
    private final ReportService reportService;

    /*@PostMapping("/report/add")
    public void addReport(@RequestBody ReportDTO reportDTO){
        reportService.addReport(reportDTO);
    }
    @DeleteMapping("/report/delete")
    public void deleteReport(@RequestBody Long id){
        reportService.deleteReport(id);
    }*/
    @GetMapping("/report/get/{id}")
    public ResponseEntity<ReportDTO> getReport(@PathVariable Long id){
        ReportDTO existingReport = reportService.getReport(id);
        if (existingReport == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(existingReport);
    }
    @GetMapping("/report/list/{reporterId}")
    public List<ReportDTO> ListReport(@PathVariable Long reporterId){
        return reportService.listReport(reporterId);
    }

    @GetMapping("/report/listAll")
    public List<ReportDTO> ListAllReport() {
        return reportService.listAllReport();
    }

}
