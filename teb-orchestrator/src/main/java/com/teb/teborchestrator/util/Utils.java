package com.teb.teborchestrator.util;

import com.teb.teborchestrator.model.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Utils {

    public static User getCurrentUser() { // add error
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static long getNumberOfNights(LocalDate dateFrom, LocalDate dateTo) {
        return ChronoUnit.DAYS.between(dateFrom, dateTo);
    }

}
