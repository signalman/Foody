package com.foody.global.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FoodyDateFormatter {
    public static LocalDate toLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString.trim(), formatter);
        return localDate;
    }
}
