package com.example.bankaccount.model;

import jakarta.persistence.*;
import jakarta.xml.bind.ValidationException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "balance", nullable = false)
    private Long balance;
    @OneToMany(cascade = CascadeType.REMOVE)
    @Column(name = "transactions", nullable = false)
    private List<Transaction> transactions = new ArrayList<>();
    @OneToOne(cascade = CascadeType.REFRESH)
    private User owner;

    public Account(Long balance, List<Transaction> transactions, User user) {
        this.balance = balance;
        this.transactions = transactions;
        this.owner = user;
    }

    public Account() {

    }
    public void setBalance(Long value) throws ValidationException {
        if(value > 0){
            this.balance = value;
        } else{
            throw new ValidationException("Balance can't be less than 0");
        }
    }
    public Long getId() {
        return this.id;
    }

    public Long getBalance() {
        return this.balance;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }
    public User getOwner(){
        return this.owner;
    }

    @Override
    public String toString() {
        var transactions = this.transactions.stream().map(Object::toString).collect(Collectors.joining(","));
        return String.format(
                "Account[id=%d, balance='%d', transactions='%s']",
                id, balance, transactions);
    }
}