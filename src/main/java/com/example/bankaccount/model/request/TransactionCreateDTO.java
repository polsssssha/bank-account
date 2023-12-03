package com.example.bankaccount.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TransactionCreateDTO {
    private Long payment;
    @NotNull
    private String comment;

    public Long getPayment() {
        return this.payment;
    }

    public String getComment() {
        return this.comment;
    }
}
