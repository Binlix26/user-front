package com.binlix26.userFront.service.serviceImpl;

import com.binlix26.userFront.model.User;
import com.binlix26.userFront.model.security.Role;
import com.binlix26.userFront.model.security.UserRole;
import com.binlix26.userFront.repository.RoleRepository;
import com.binlix26.userFront.repository.UserRepository;
import com.binlix26.userFront.service.AccountService;
import com.binlix26.userFront.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by binlix26 on 17/07/17.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;

    @Override
    public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userRepository.findByUsername(user.getUsername());

        if (localUser != null)
            LOG.info("User with username {} already exists.", user.getUsername());
        else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

             // do not understand why I need this
//            for (UserRole userRole : userRoles) {
//                Role role = userRole.getRole();
//                role.getUserRoles().add(userRole);
//                roleRepository.save(role);
//            }

            // do not understand why I need this
//            for (UserRole ur : userRoles) {
//                roleRepository.save(ur.getRole());
//            }

            user.getUserRoles().addAll(userRoles);
            // initialise accounts
            user.setPrimaryAccount(accountService.createPrimaryAccount());
            user.setSavingAccount(accountService.createSavingAccount());

            localUser = userRepository.save(user);
        }

        return localUser;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean checkUserExists(String username, String email) {
        return checkUsernameExists(username) || checkEmailExists(email);
    }

    @Override
    public boolean checkUsernameExists(String username) {
        return findByUsername(username) != null;
    }

    @Override
    public boolean checkEmailExists(String email) {
        return findByEmail(email) != null;
    }

    @Override
    public List<User> findUserList() {
        return userRepository.findAll();
    }

    @Override
    public void enableUser(String username) {
        User user = findByUsername(username);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void disableUser(String username) {
        User user = findByUsername(username);
        user.setEnabled(false);
        System.out.println(user.isEnabled());
        userRepository.save(user);
        System.out.println(username + " is disabled.");
    }
}
