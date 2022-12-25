package com.example.controller;

import com.example.dto.ReportDto;
import com.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    //Report .--> Controller
    //1. Create repost (USER)
    @PostMapping("/create")
    public HttpEntity<?> create(@RequestBody ReportDto reportDto) {
        return ResponseEntity.ok(reportService.create(reportDto));
    }

    //    2. ReportList Pagination ADMIN ReportInfo
    @GetMapping("/reportList")
    public HttpEntity<?> getReportList(@RequestParam("page") Integer page,
                                       @RequestParam("size") Integer size) {
        return ResponseEntity.ok(reportService.getAllByPagination(page, size));
    }


    //    3. Remove Report by id (ADMIN)
    public HttpEntity<?> remove(@PathVariable() Integer id) {
        return ResponseEntity.ok(reportService.deleteById(id));
    }

    //4. Report List By User id (ADMIN)
    //ReportInfo
    @GetMapping("/list")
    public HttpEntity<?> reportList() {
        return ResponseEntity.ok(reportService.findAllByProfile_id());
    }


//RepostInfo id,profile(id,name,surname,photo(id,url)),content,
//entity_id(channel/video)),type(channel,video)

}
