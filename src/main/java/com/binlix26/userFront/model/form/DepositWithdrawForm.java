package com.binlix26.userFront.model.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by binlix26 on 18/07/17.
 */
public class DepositWithdrawForm {
    @NotNull(message = "Select your Account")
    private String accountType;

    @NotNull
    @Min(value = 0, message = "Invalid Amount")
    private double amount;

    public DepositWithdrawForm() {
    }

    public DepositWithdrawForm(String accountType, double amount) {
        this.accountType = accountType;
        this.amount = amount;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
