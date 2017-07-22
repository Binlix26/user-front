package com.binlix26.userFront.service;

import com.binlix26.userFront.model.PrimaryAccount;
import com.binlix26.userFront.model.SavingAccount;

import java.security.Principal;

/**
 * Created by binlix26 on 17/07/17.
 */
public interface AccountService {
    PrimaryAccount createPrimaryAccount();
    SavingAccount createSavingAccount();

    void deposit(String accountType, double amount, String username);

    void withdraw(String accountType, double amount, String username);

    boolean isBalanceEnough(String accountType, double amount, String username);
}
