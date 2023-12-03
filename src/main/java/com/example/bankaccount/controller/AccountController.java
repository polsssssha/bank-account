package com.example.bankaccount.controller;

import com.example.bankaccount.model.Account;
import com.example.bankaccount.model.Transaction;
import com.example.bankaccount.model.User;
import com.example.bankaccount.model.request.AccountCreateDTO;
import com.example.bankaccount.model.request.TransactionCreateDTO;
import com.example.bankaccount.service.AccountService;
import com.example.bankaccount.service.TransactionService;
import com.example.bankaccount.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bankAccount/v1/accounts")
public class AccountController {
    private final AccountService service;
    private final UserService userService;
    private final TransactionService transactionService;

    public AccountController(AccountService service, UserService userService, TransactionService transactionService) {
        this.service = service;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Account>> getAccounts() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var username = auth.getName();
        try {
            var user = userService.getUserByUsername(username);
            return ResponseEntity.ok(service.getAccounts(user));
        } catch (Exception ex) {
            return ResponseEntity.status(401).build();
        }

    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(service.getAccount(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createAccount(@Valid @RequestBody AccountCreateDTO request) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var username = auth.getName();
        try {
            var user = userService.getUserByUsername(username);
            service.createAccount(request, user);
            return ResponseEntity.status(201).build();
        } catch (Exception ex) {
            return ResponseEntity.status(401).build();
        }

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountCreateDTO request) {
        try {
            return ResponseEntity.ok().body(service.updateAccount(id, request));
        } catch (ValidationException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        try {
            var account = service.getAccount(id);
            var transactions = account.getTransactions();
            service.deleteAccount(id);
            if(transactions != null){
                transactions.forEach(x -> transactionService.deleteTransaction(x.getId()));
            }
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}/transactions")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(service.getAccount(id).getTransactions());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("{id}/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createTransaction(@PathVariable Long id, @Valid @RequestBody TransactionCreateDTO request) {
        try {
            var transaction = transactionService.createTransaction(request);
            service.addTransaction(id, transaction);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}/transactions/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> changeTransaction(@PathVariable Long id, @PathVariable Long transactionId, @Valid @RequestBody TransactionCreateDTO request) {
        try {
            service.updateTransaction(id, transactionId, request.getPayment());
            var newTransaction = transactionService.updateTransaction(transactionId, request);
            return ResponseEntity.ok().body(newTransaction);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}/transactions/{transactionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id, @PathVariable Long transactionId) {
        try {
            var transaction = transactionService.getTransaction(transactionId);
            service.deleteTransaction(id, transaction);
            transactionService.deleteTransaction(transactionId);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
