package com.binlix26.userFront.service.serviceImpl;

import com.binlix26.userFront.model.*;
import com.binlix26.userFront.model.form.BetweenAccountsForm;
import com.binlix26.userFront.model.form.ToSomeoneElseForm;
import com.binlix26.userFront.repository.*;
import com.binlix26.userFront.service.TransactionService;
import com.binlix26.userFront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by binlix26 on 18/07/17.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private PrimaryAccountRepository primaryAccountRepository;

    @Autowired
    private SavingAccountRepository savingAccountRepository;

    @Autowired
    private PrimaryTransactionRepository primaryTransactionRepository;

    @Autowired
    private SavingTransactionRepository savingTransactionRepository;

    @Autowired
    private RecipientRepository recipientRepository;

    @Override
    public List<PrimaryTransaction> findPrimaryTransactionList(String username) {
        User user = userService.findByUsername(username);
        List<PrimaryTransaction> transactionList = user.getPrimaryAccount().getPrimaryTransactionList();
        return transactionList;
    }

    @Override
    public List<SavingTransaction> findSavingsTransactionList(String username) {
        User user = userService.findByUsername(username);
        List<SavingTransaction> transactionList = user.getSavingAccount().getSavingTransactionList();
        return transactionList;
    }

    @Override
    public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction) {
        primaryTransactionRepository.save(primaryTransaction);
    }

    @Override
    public void saveSavingsDepositTransaction(SavingTransaction savingsTransaction) {
        savingTransactionRepository.save(savingsTransaction);
    }

    @Override
    public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction) {
        primaryTransactionRepository.save(primaryTransaction);
    }

    @Override
    public void saveSavingsWithdrawTransaction(SavingTransaction savingsTransaction) {
        savingTransactionRepository.save(savingsTransaction);
    }

    @Override
    public void betweenAccountsTransfer(BetweenAccountsForm form, String username) {
        // Get all the information needed
        User user = userService.findByUsername(username);
        String transferFrom = form.getTransferFrom();
        String transferTo = form.getTransferTo();
        BigDecimal amount = new BigDecimal(form.getAmount());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingAccount savingAccount = user.getSavingAccount();
        BigDecimal primaryBalance = primaryAccount.getAccountBalance();
        BigDecimal savingBalance = savingAccount.getAccountBalance();

        // from Primary to Saving
        if (transferFrom.equalsIgnoreCase("Primary")
                && transferTo.equalsIgnoreCase("Saving")) {
            primaryBalance = primaryBalance.subtract(amount);
            savingBalance = savingBalance.add(amount);

            primaryAccount.setAccountBalance(primaryBalance);
            savingAccount.setAccountBalance(savingBalance);

            primaryAccountRepository.save(primaryAccount);
            savingAccountRepository.save(savingAccount);

            Date date = new Date();
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(
                    date,
                    "Transfer to " + transferTo,
                    "Transfer",
                    "Finished",
                    amount.doubleValue(),
                    primaryBalance,
                    primaryAccount
            );

            SavingTransaction savingTransaction = new SavingTransaction(
                    date,
                    "From " + transferFrom,
                    "Transfer",
                    "Finished",
                    amount.doubleValue(),
                    savingBalance,
                    savingAccount
            );

            savePrimaryWithdrawTransaction(primaryTransaction);
            saveSavingsDepositTransaction(savingTransaction);

            // from saving to primary
        } else if (transferFrom.equalsIgnoreCase("Saving")
                && transferTo.equalsIgnoreCase("Primary")) {
            primaryBalance = primaryBalance.add(amount);
            savingBalance = savingBalance.subtract(amount);

            primaryAccount.setAccountBalance(primaryBalance);
            savingAccount.setAccountBalance(savingBalance);

            primaryAccountRepository.save(primaryAccount);
            savingAccountRepository.save(savingAccount);

            Date date = new Date();
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(
                    date,
                    "from " + transferFrom,
                    "Transfer",
                    "Finished",
                    amount.doubleValue(),
                    primaryBalance,
                    primaryAccount
            );

            SavingTransaction savingTransaction = new SavingTransaction(
                    date,
                    "transfer to " + transferTo,
                    "Transfer",
                    "Finished",
                    amount.doubleValue(),
                    savingBalance,
                    savingAccount
            );

            saveSavingsWithdrawTransaction(savingTransaction);
            savePrimaryDepositTransaction(primaryTransaction);
        }
    }

    @Override
    public List<Recipient> findRecipientList(String username) {
        List<Recipient> recipientList = recipientRepository.findAll().stream()
                .filter( recipient -> username.equals(recipient.getUser().getUsername()))
                .collect(Collectors.toList());

        return recipientList;
    }

    @Override
    public Recipient saveRecipient(Recipient recipient) {
        return recipientRepository.save(recipient);
    }

    @Override
    public Recipient findRecipient(Long id) {
        return recipientRepository.findOne(id);
    }

    @Override
    public void deleteRecipient(Long id) {
        recipientRepository.delete(id);
    }

    @Override
    public void transferToSomeone(ToSomeoneElseForm form, String username) {
        User user = userService.findByUsername(username);
        String accountType = form.getAccountType();
        double amount = form.getAmount();
        long recipientId = form.getRecipientId();
        String recipientName = recipientRepository.findOne(recipientId).getName();

        if (accountType.equalsIgnoreCase("Primary")) {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            BigDecimal balance = primaryAccount.getAccountBalance().subtract(new BigDecimal(amount));
            primaryAccount.setAccountBalance(balance);
            primaryAccountRepository.save(primaryAccount);

            Date date = new Date();
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(
                    date,
                    "Transfer to " + recipientName,
                    "Transfer",
                    "Finished",
                    amount,
                    balance,
                    primaryAccount
            );
            primaryTransactionRepository.save(primaryTransaction);
        } else if (accountType.equalsIgnoreCase("Saving")) {
            SavingAccount savingAccount = user.getSavingAccount();
            BigDecimal balance = savingAccount.getAccountBalance().subtract(new BigDecimal(amount));
            savingAccount.setAccountBalance(balance);
            savingAccountRepository.save(savingAccount);

            Date date = new Date();
            SavingTransaction savingTransaction = new SavingTransaction(
                    date,
                    "Transfer to " + recipientName,
                    "Transfer",
                    "Finished",
                    amount,
                    balance,
                    savingAccount
            );
            savingTransactionRepository.save(savingTransaction);
        }
    }
}
