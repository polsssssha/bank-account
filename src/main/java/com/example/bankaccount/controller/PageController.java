package com.example.bankaccount.controller;

import com.example.bankaccount.service.AccountService;
import com.example.bankaccount.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    private final AccountService service;
    private final TransactionService transactionService;

    public PageController(AccountService service, TransactionService transactionService) {
        this.service = service;
        this.transactionService = transactionService;
    }

    @RequestMapping("/transactions/month")
    public String monthTransactions() {
        return "monthTransactions";
    }

    @RequestMapping("/login")
    public String login() {
        return "auth";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/")
    public String accounts() {
        return "accounts";
    }

    @RequestMapping("/add")
    public String addAccount() {
        return "addAccount";
    }

    @RequestMapping("/{id}/change")
    public String changeAccount(@PathVariable Long id) {
        try {
            service.getAccount(id);
            return "changeAccount";
        } catch (Exception ex) {
            return "redirect:/";
        }
    }

    @RequestMapping("/{id}/transactions")
    public String transactions(@PathVariable String id) {
        return "transactions";
    }

    @RequestMapping("/{id}/transactions/add")
    public String addTransaction(@PathVariable String id) {
        return "addTransaction";
    }

    @RequestMapping("/{id}/transactions/{transactionId}/change")
    public String changeTransaction(@PathVariable Long id, @PathVariable Long transactionId) {
        try {
            service.getAccount(id);
            try {
                transactionService.getTransaction(transactionId);
                return "changeTransaction";
            } catch (Exception ex) {
                return String.format("redirect:/%s/transactions", id.toString());
            }
        } catch (Exception ex) {
            return "redirect:/";
        }
    }
}
