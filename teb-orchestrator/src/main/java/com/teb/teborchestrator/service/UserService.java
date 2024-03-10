package com.teb.teborchestrator.service;

import com.teb.teborchestrator.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    User create(User user);

    User getByUsername(String username);

    UserDetailsService userDetailsService();

}
