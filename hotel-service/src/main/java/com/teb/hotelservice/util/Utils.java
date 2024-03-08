package com.teb.hotelservice.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<LocalDate> generateDatesBetween(LocalDate from, LocalDate to) {
        List<LocalDate> dates = new ArrayList<>();

        while (!from.isAfter(to)) {
            dates.add(from);
            from = from.plusDays(1);
        }

        return dates;
    }
}
