package com.example.demo.controller;

import com.example.demo.dto.bill.BillDto;
import com.example.demo.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService service;

    @PostMapping("")
    public ResponseEntity<?> saveBill(@RequestBody BillDto billDto) {
        return service.saveBill(billDto);
    }
    @GetMapping("")
    public ResponseEntity<?> getAllBill(@RequestParam(name="page",required = false,defaultValue = "1") Integer page,
                                        @RequestParam(name = "size", required = false, defaultValue = "5")  Integer size){
        return service.getAllBill(size,page);
    }
}
