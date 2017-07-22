package com.binlix26.userFront.repository;

import com.binlix26.userFront.model.PrimaryAccount;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by binlix26 on 17/07/17.
 */
public interface PrimaryAccountRepository extends CrudRepository<PrimaryAccount, Long> {
    PrimaryAccount findByAccountNumber (int accountNumber);
}
