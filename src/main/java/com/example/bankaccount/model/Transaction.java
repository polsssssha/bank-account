package com.example.bankaccount.model;

import jakarta.persistence.*;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.ZonedDateTime;

@Entity
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "amount", nullable = false)
    private Long amount;
    @Column(name = "remark", nullable = true)
    private String comment;
    @Column(name = "payment_date", nullable = false)
    private ZonedDateTime date;

    public Transaction(Long amount, String comment, ZonedDateTime date) {
        this.amount = amount;
        this.comment = comment;
        this.date = date;
    }

    public Transaction() {

    }

    public Long getId() {
        return this.id;
    }

    public Long getAmount() {
        return this.amount;
    }

    public String getComment() {
        return this.comment;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public void setAmount(Long value) {
        this.amount = value;
    }

    public void setComment(String value) {
        this.comment = value;
    }

    public void setDate(ZonedDateTime value) {
        this.date = value;
    }

    @Override
    public String toString() {
        var dateTimeFormatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss VV");
        return String.format(
                "Transaction[id=%d, amount='%d', comment='%s' date='%s']",
                id, amount, comment, date.format(dateTimeFormatter));
    }
}
