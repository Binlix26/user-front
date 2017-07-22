package com.binlix26.userFront.repository;

import com.binlix26.userFront.model.SavingAccount;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by binlix26 on 17/07/17.
 */
public interface SavingAccountRepository extends CrudRepository<SavingAccount, Long> {
    SavingAccount findByAccountNumber(int accountNumber);
}
