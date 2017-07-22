package com.binlix26.userFront.model.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by binlix26 on 18/07/17.
 */
public class BetweenAccountsForm {

    @NotNull
    private String transferFrom;

    @NotNull
    private String transferTo;

    @Min(value = 0, message = "Invalid Amount")
    private double amount;

    public BetweenAccountsForm() {
    }

    public BetweenAccountsForm(String transferFrom, String transferTo, double amount) {
        this.transferFrom = transferFrom;
        this.transferTo = transferTo;
        this.amount = amount;
    }

    public String getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(String transferFrom) {
        this.transferFrom = transferFrom;
    }

    public String getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(String transferTo) {
        this.transferTo = transferTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
