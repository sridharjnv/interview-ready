package com.alacriti.payment.controller;

import com.alacriti.payment.dto.AmountRequest;
import com.alacriti.payment.dto.CreateAccountRequest;
import com.alacriti.payment.model.Account;
import com.alacriti.payment.model.Transaction;
import com.alacriti.payment.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // POST /api/accounts — Create a new account
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    // GET /api/accounts/{id} — Get account details
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        Account account = accountService.getAccount(id);
        return ResponseEntity.ok(account);
    }

    // POST /api/accounts/{id}/deposit — Deposit money
    @PostMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long id,
                                           @RequestBody AmountRequest request) {
        Account account = accountService.deposit(id, request.getAmount());
        return ResponseEntity.ok(account);
    }

    // POST /api/accounts/{id}/withdraw — Withdraw money
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable Long id,
                                            @RequestBody AmountRequest request) {
        Account account = accountService.withdraw(id, request.getAmount());
        return ResponseEntity.ok(account);
    }

    // GET /api/accounts/{id}/transactions — Transaction history
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long id) {
        List<Transaction> transactions = accountService.getTransactionHistory(id);
        return ResponseEntity.ok(transactions);
    }
}
