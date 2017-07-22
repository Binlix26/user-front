package com.binlix26.userFront.service;

import com.binlix26.userFront.model.PrimaryTransaction;
import com.binlix26.userFront.model.Recipient;
import com.binlix26.userFront.model.SavingTransaction;
import com.binlix26.userFront.model.form.BetweenAccountsForm;
import com.binlix26.userFront.model.form.ToSomeoneElseForm;

import java.util.List;

/**
 * Created by binlix26 on 18/07/17.
 */
public interface TransactionService {
    List<PrimaryTransaction> findPrimaryTransactionList(String username);

    List<SavingTransaction> findSavingsTransactionList(String username);

    void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction);

    void saveSavingsDepositTransaction(SavingTransaction savingsTransaction);

    void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction);

    void saveSavingsWithdrawTransaction(SavingTransaction savingsTransaction);

    void betweenAccountsTransfer(BetweenAccountsForm form, String username);

    List<Recipient> findRecipientList(String username);

    Recipient saveRecipient(Recipient recipient);

    Recipient findRecipient(Long id);

    void deleteRecipient(Long id);

    void transferToSomeone(ToSomeoneElseForm form, String username);
}
