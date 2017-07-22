package com.binlix26.userFront.service;

import com.binlix26.userFront.model.User;
import com.binlix26.userFront.model.security.UserRole;

import java.util.List;
import java.util.Set;

/**
 * Created by binlix26 on 17/07/17.
 */
public interface UserService {
    void save(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    boolean checkUsernameExists(String name);

    boolean checkEmailExists(String email);

    boolean checkUserExists(String username, String email);

    User createUser(User user, Set<UserRole> userRoles);

    List<User> findUserList();

    void enableUser (String username);

    void disableUser (String username);
}
