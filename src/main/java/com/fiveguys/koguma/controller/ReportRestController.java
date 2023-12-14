package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ReportDTO;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.service.common.ReportService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/member/report")
public class ReportRestController {
    private final ReportService reportService;


    @PostMapping("/add")
    public ResponseEntity<ReportDTO> addReport(
            @CurrentMember MemberDTO authenticatedMember,
            @RequestBody ReportDTO reportDTO
    ){
        reportDTO.setReporter(authenticatedMember.toEntity());
        try{
            if( authenticatedMember == null || !authenticatedMember.getId().equals(reportDTO.getReporter())){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
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
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteReport(@CurrentMember MemberDTO authenticatedMember) {
        try {
            reportService.deleteReport(authenticatedMember);
            return ResponseEntity.noContent().build();
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/get")
    public ResponseEntity<ReportDTO> getReport(@CurrentMember MemberDTO authenticatedMember) {
        Long reporterId = authenticatedMember.getId();

        ReportDTO existingReport = reportService.getReport(authenticatedMember);

        if (existingReport == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(existingReport);
    }
    @GetMapping("/list")
    public List<ReportDTO> listReport(@CurrentMember MemberDTO authenticatedMember) {
        Long reporterId = authenticatedMember.getId();

        return reportService.listReport(reporterId);
    }

    @GetMapping("/listAll")
    public List<ReportDTO> ListAllReport() {
        return reportService.listAllReport();
    }

}
