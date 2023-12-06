package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.ReportDTO;
import com.fiveguys.koguma.data.entity.Member;
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
@RequestMapping("/report")
public class ReportRestController {
    private final ReportService reportService;

    @PostMapping("/add/{reporter}")
    public ResponseEntity<ReportDTO> addReport(@RequestBody ReportDTO reportDTO){
        try{
            reportService.addReport(reportDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reportDTO);
        }
    }

    //{
    //    "reporter" : {
    //        "id" : "1"
    //    },
    //    "reportTitle" : "사기를 당했어요.",
    //    "reportContent" : "중고 거래를 하다 사기를 당했어요. 가해자를 처벌해 주세요.",
    //    "categoryId" : "51",
    //    "categoryName" : "회원"
    //}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ReportDTO> getReport(@PathVariable Long id){
        ReportDTO existingReport = reportService.getReport(id);
        if (existingReport == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(existingReport);
    }
    @GetMapping("/list/{reporterId}")
    public List<ReportDTO> ListReport(@PathVariable Long reporterId){
        return reportService.listReport(reporterId);
    }

    @GetMapping("/listAll")
    public List<ReportDTO> ListAllReport() {
        return reportService.listAllReport();
    }

}
