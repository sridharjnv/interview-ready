package com.alacriti.payment.controller;

import com.alacriti.payment.dto.TransferRequest;
import com.alacriti.payment.dto.TransferResponse;
import com.alacriti.payment.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final AccountService accountService;

    public TransferController(AccountService accountService) {
        this.accountService = accountService;
    }

    // POST /api/transfers — Transfer money between accounts
    @PostMapping
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest request) {
        TransferResponse response = accountService.transfer(request);
        return ResponseEntity.ok(response);
    }
}
