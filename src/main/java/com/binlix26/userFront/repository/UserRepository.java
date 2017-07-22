package com.binlix26.userFront.repository;

import com.binlix26.userFront.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by binlix26 on 17/07/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findAll();
}
