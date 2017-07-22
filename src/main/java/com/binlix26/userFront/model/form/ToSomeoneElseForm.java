package com.binlix26.userFront.model.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by binlix26 on 21/07/17.
 */
public class ToSomeoneElseForm {

    private String accountType;

    @NotNull
    @Min(value = 0, message = "Invalid Amount")
    private double amount;


    private Long recipientId;

    public ToSomeoneElseForm() {
    }

    public ToSomeoneElseForm(String accountType, double amount, Long recipientId) {
        this.accountType = accountType;
        this.amount = amount;
        this.recipientId = recipientId;
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

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }
}
