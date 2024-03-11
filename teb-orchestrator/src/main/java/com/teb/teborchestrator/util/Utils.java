package com.teb.teborchestrator.util;

import com.teb.teborchestrator.model.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {

    public static User getCurrentUser() { // add error
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
