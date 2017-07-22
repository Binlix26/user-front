package com.binlix26.userFront.model.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by binlix26 on 17/07/17.
 */
public class Authority implements GrantedAuthority {
    
    private final String AUTHORITY;

    public Authority(String authority) {
        this.AUTHORITY = authority;
    }

    @Override
    public String getAuthority() {
        return AUTHORITY;
    }
}
