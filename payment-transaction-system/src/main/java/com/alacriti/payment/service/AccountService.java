package com.alacriti.payment.service;

import com.alacriti.payment.dto.*;
import com.alacriti.payment.exception.InsufficientBalanceException;
import com.alacriti.payment.exception.ResourceNotFoundException;
import com.alacriti.payment.model.*;
import com.alacriti.payment.repository.AccountRepository;
import com.alacriti.payment.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    // Constructor injection (no @Autowired needed with single constructor)
    public AccountService(AccountRepository accountRepository,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    // ==================== CREATE ACCOUNT ====================

    public Account createAccount(CreateAccountRequest request) {
        Account account = new Account(request.getAccountHolderName(), request.getEmail());
        return accountRepository.save(account);
    }

    // ==================== GET ACCOUNT ====================

    public Account getAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
    }

    // ==================== DEPOSIT ====================

    @Transactional
    public Account deposit(Long accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        Account account = getAccount(accountId);
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        // Create CREDIT transaction record
        Transaction txn = Transaction.create(
                null,           // no sender for deposit
                account,        // receiver
                amount,
                TransactionType.CREDIT,
                TransactionStatus.SUCCESS
        );
        transactionRepository.save(txn);

        return account;
    }

    // ==================== WITHDRAW ====================

    @Transactional
    public Account withdraw(Long accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        Account account = getAccount(accountId);

        // Business rule: check sufficient balance
        if (account.getBalance().compareTo(amount) < 0) {
            // Save a FAILED transaction record
            Transaction failedTxn = Transaction.create(
                    account, null, amount,
                    TransactionType.DEBIT,
                    TransactionStatus.FAILED
            );
            transactionRepository.save(failedTxn);

            throw new InsufficientBalanceException(
                    "Insufficient balance. Available: " + account.getBalance());
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        // Create DEBIT transaction record
        Transaction txn = Transaction.create(
                account,        // sender
                null,           // no receiver for withdrawal
                amount,
                TransactionType.DEBIT,
                TransactionStatus.SUCCESS
        );
        transactionRepository.save(txn);

        return account;
    }

    // ==================== TRANSFER ====================

    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        Long fromId = request.getFromAccountId();
        Long toId = request.getToAccountId();
        BigDecimal amount = request.getAmount();

        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        Account fromAccount = getAccount(fromId);
        Account toAccount = getAccount(toId);

        // Business rule: check sufficient balance
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            // Save a FAILED transaction record
            Transaction failedTxn = Transaction.create(
                    fromAccount, toAccount, amount,
                    TransactionType.TRANSFER,
                    TransactionStatus.FAILED
            );
            transactionRepository.save(failedTxn);

            throw new InsufficientBalanceException(
                    "Insufficient balance. Available: " + fromAccount.getBalance());
        }

        // Perform the transfer atomically (both within @Transactional)
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Save TRANSFER transaction record
        Transaction txn = Transaction.create(
                fromAccount, toAccount, amount,
                TransactionType.TRANSFER,
                TransactionStatus.SUCCESS
        );
        transactionRepository.save(txn);

        return new TransferResponse(
                txn.getId(),
                "SUCCESS",
                fromAccount.getBalance(),
                toAccount.getBalance()
        );
    }

    // ==================== TRANSACTION HISTORY ====================

    public List<Transaction> getTransactionHistory(Long accountId) {
        // Verify account exists first
        getAccount(accountId);
        return transactionRepository.findByAccountId(accountId);
    }
}
