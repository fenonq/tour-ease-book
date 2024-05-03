package com.teb.orderservice.util;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Utils {

    public static long getNumberOfNights(LocalDate dateFrom, LocalDate dateTo) {
        return ChronoUnit.DAYS.between(dateFrom, dateTo);
    }

}
