package com.sts_LoginAuthentication.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    
    // Load user by username (used by your service implementation)
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

