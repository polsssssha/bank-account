package com.example.bankaccount.service;

import com.example.bankaccount.model.Account;
import com.example.bankaccount.model.Transaction;
import com.example.bankaccount.model.User;
import com.example.bankaccount.model.request.AccountCreateDTO;
import com.example.bankaccount.repository.AccountRepository;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public List<Account> getAccounts(User user) {
        var list = new ArrayList<Account>();
        repository.findAll().forEach(list::add);
        return list.stream().filter(x -> x.getOwner() != null && x.getOwner().getId().equals(user.getId())).toList();
    }

    public Account getAccount(Long id) {
        var account = repository.findById(id);
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new RuntimeException("account nor found");
        }
    }

    public void createAccount(AccountCreateDTO dto, User user) {
        repository.save(new Account(dto.getInitialPayment(), new ArrayList<>(), user));
    }

    public Account updateAccount(Long id, AccountCreateDTO dto) throws ValidationException {
        var entity = repository.findById(id);

        if (entity.isPresent()) {
            var account = entity.get();
            account.setBalance(dto.getInitialPayment());
            repository.save(account);
            return account;
        } else {
            throw new RuntimeException("Account not found");
        }
    }

    public void deleteAccount(Long id) {
        var entity = repository.findById(id);
        if (entity.isPresent()) {
            repository.delete(entity.get());
        } else {
            throw new RuntimeException("Account not found");
        }
    }

    public void addTransaction(Long id, Transaction transaction) throws ValidationException {
        var account = getAccount(id);
        account.setBalance(account.getBalance() + transaction.getAmount());
        account.getTransactions().add(transaction);
        repository.save(account);
    }

    public void updateTransaction(Long id, Long transactionId,Long amount) throws ValidationException {
        var account = getAccount(id);
        var myTransaction = account.getTransactions().stream().filter(x -> x != null && Objects.equals(x.getId(), transactionId)).toList();
        System.out.println(myTransaction);
        if(!myTransaction.isEmpty()){
            var newAmount = myTransaction.get(0).getAmount() - amount;
            account.setBalance(account.getBalance() - newAmount);
            repository.save(account);
        }
    }

    public void deleteTransaction(Long id, Transaction transaction) throws ValidationException {
        var account = getAccount(id);
        account.getTransactions().remove(transaction);
        account.setBalance(account.getBalance() - transaction.getAmount());
        repository.save(account);
    }
}
