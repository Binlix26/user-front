package com.binlix26.userFront.repository;

import com.binlix26.userFront.model.Recipient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by binlix26 on 21/07/17.
 */
public interface RecipientRepository extends CrudRepository<Recipient, Long> {
    List<Recipient> findAll();

    Recipient findByName(String name);

    void deleteByName(String name);
}
