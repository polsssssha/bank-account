package com.example.bankaccount.repository;

import com.example.bankaccount.model.Account;
import com.example.bankaccount.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Iterable<Transaction> findAllByDateBetween(ZonedDateTime startDate, ZonedDateTime endDate);
}
