package com.binlix26.userFront.service.serviceImpl;

import com.binlix26.userFront.model.*;
import com.binlix26.userFront.repository.PrimaryAccountRepository;
import com.binlix26.userFront.repository.SavingAccountRepository;
import com.binlix26.userFront.service.AccountService;
import com.binlix26.userFront.service.TransactionService;
import com.binlix26.userFront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by binlix26 on 17/07/17.
 */
@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PrimaryAccountRepository primaryAccountRepository;

    @Autowired
    private SavingAccountRepository savingAccountRepository;

    @Override
    public PrimaryAccount createPrimaryAccount() {
        PrimaryAccount account = new PrimaryAccount();
        account.setAccountNumber(accountGen());
        account.setAccountBalance(new BigDecimal(0.0));
        primaryAccountRepository.save(account);

        // the reason of returning account this way is because the 'ID' field is not defined
        return primaryAccountRepository.findByAccountNumber(account.getAccountNumber());
    }

    @Override
    public SavingAccount createSavingAccount() {
        SavingAccount account = new SavingAccount();
        account.setAccountNumber(accountGen());
        account.setAccountBalance(new BigDecimal(0.0));
        savingAccountRepository.save(account);

        return savingAccountRepository.findByAccountNumber(account.getAccountNumber());
    }

    @Override
    public void deposit(String accountType, double amount, String username) {
        User user = userService.findByUsername(username);

        if (accountType.equalsIgnoreCase("Primary")) {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            BigDecimal balance = primaryAccount.getAccountBalance().add(new BigDecimal(amount));
            primaryAccount.setAccountBalance(balance);
            primaryAccountRepository.save(primaryAccount);
            // Transaction
            Date date = new Date();
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(
                    date,
                    "Deposit to primary account",
                    "Account",
                    "Finished",
                    amount,
                    balance,
                    primaryAccount
            );
            // Save Transaction
            transactionService.savePrimaryDepositTransaction(primaryTransaction);

        } else if (accountType.equalsIgnoreCase("Saving")) {
            SavingAccount savingAccount = user.getSavingAccount();
            BigDecimal balance = savingAccount.getAccountBalance().add(new BigDecimal(amount));
            savingAccount.setAccountBalance(balance);
            savingAccountRepository.save(savingAccount);
            // Transaction
            Date date = new Date();
            SavingTransaction savingTransaction = new SavingTransaction(
                    date,
                    "Deposit to saving account",
                    "Account",
                    "Finished",
                    amount,
                    balance,
                    savingAccount
            );
            // Save Transaction
            transactionService.saveSavingsDepositTransaction(savingTransaction);
        }
    }

    @Override
    public void withdraw(String accountType, double amount, String username) {
        User user = userService.findByUsername(username);

        if (accountType.equalsIgnoreCase("Primary")) {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            BigDecimal balance = primaryAccount.getAccountBalance().subtract(new BigDecimal(amount));
            primaryAccount.setAccountBalance(balance);
            primaryAccountRepository.save(primaryAccount);
            // Transaction
            Date date = new Date();
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(
                    date,
                    "Withdraw to primary account",
                    "Account",
                    "Finished",
                    amount,
                    balance,
                    primaryAccount
            );
            // Save Transaction
            transactionService.savePrimaryWithdrawTransaction(primaryTransaction);
        } else if (accountType.equalsIgnoreCase("Saving")) {
            SavingAccount savingAccount = user.getSavingAccount();
            BigDecimal balance = savingAccount.getAccountBalance().subtract(new BigDecimal(amount));
            savingAccount.setAccountBalance(balance);
            savingAccountRepository.save(savingAccount);
            // Transaction
            Date date = new Date();
            SavingTransaction savingTransaction = new SavingTransaction(
                    date,
                    "Withdraw to saving account",
                    "Account",
                    "Finished",
                    amount,
                    balance,
                    savingAccount
            );
            // Save Transaction
            transactionService.saveSavingsWithdrawTransaction(savingTransaction);
        }
    }

    @Override
    public boolean isBalanceEnough(String accountType, double amount, String username) {
        User user = userService.findByUsername(username);
        BigDecimal balance = new BigDecimal(0.0);

        if (accountType.equalsIgnoreCase("Primary")) {
            balance = user.getPrimaryAccount().getAccountBalance();
        } else if (accountType.equalsIgnoreCase("Saving")) {
            balance = user.getSavingAccount().getAccountBalance();
        }

        return balance.compareTo(new BigDecimal(amount)) >= 0;
    }

    private int accountGen() {
        return (int) (Math.random() * 10000000);
    }
}
