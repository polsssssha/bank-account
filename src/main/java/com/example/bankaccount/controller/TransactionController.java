package com.example.bankaccount.controller;

import com.example.bankaccount.model.Transaction;
import com.example.bankaccount.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bankAccount/v1/transactions")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("lastMonth")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getTransactions() {
        var endDate = ZonedDateTime.now();
        var startDate = endDate.minusMonths(1);
        return service.getTransactionsByDate(startDate, endDate);
    }
}
