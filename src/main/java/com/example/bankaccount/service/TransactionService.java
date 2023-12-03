package com.example.bankaccount.service;

import com.example.bankaccount.model.Account;
import com.example.bankaccount.model.Transaction;
import com.example.bankaccount.model.User;
import com.example.bankaccount.model.request.AccountCreateDTO;
import com.example.bankaccount.model.request.TransactionCreateDTO;
import com.example.bankaccount.repository.TransactionRepository;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getTransactionsByDate(ZonedDateTime startDate, ZonedDateTime endDate) {
        var list = new ArrayList<Transaction>();
        repository.findAllByDateBetween(startDate, endDate).forEach(list::add);
        return list;
    }

    public Transaction getTransaction(Long id) {
        var transaction = repository.findById(id);
        if (transaction.isPresent()) {
            return transaction.get();
        } else {
            throw new RuntimeException("Transaction nor found");
        }
    }

    public Transaction createTransaction(TransactionCreateDTO dto) {
        return repository.save(new Transaction(dto.getPayment(), dto.getComment(), ZonedDateTime.now()));
    }

    public Transaction updateTransaction(Long id, TransactionCreateDTO dto) {
        var entity = repository.findById(id);

        if (entity.isPresent()) {
            var transaction = entity.get();
            transaction.setAmount(dto.getPayment());
            transaction.setComment(dto.getComment());
            repository.save(transaction);
            return transaction;
        } else {
            throw new RuntimeException("Transaction not found");
        }
    }

    public void deleteTransaction(Long id) {
        var entity = repository.findById(id);
        if (entity.isPresent()) {
            repository.delete(entity.get());
        } else {
            throw new RuntimeException("Transaction not found");
        }

    }
}
