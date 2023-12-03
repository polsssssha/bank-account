package com.example.bankaccount.model.request;

import jakarta.validation.constraints.Min;

public class AccountCreateDTO {
    @Min(value = 0,message = "Initial payment can't be less than 0")
    private Long initialPayment;

    public Long getInitialPayment() {
        return this.initialPayment;
    }
}
