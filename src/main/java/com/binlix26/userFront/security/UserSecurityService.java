package com.binlix26.userFront.security;

import com.binlix26.userFront.model.User;
import com.binlix26.userFront.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by binlix26 on 17/07/17.
 */
@Service
public class UserSecurityService implements UserDetailsService {

    // The application logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserSecurityService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            LOGGER.warn("Username {} not found", username);
            throw new UsernameNotFoundException("Username: " + username + " not found.");
        }
        return user;
    }
}
