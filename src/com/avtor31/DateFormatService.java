package com.avtor31;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatService {
    private final SimpleDateFormat dateFormat;

    public DateFormatService() {
        System.out.println("It was created new SimpleDateFormat object in DateFormatService");
        this.dateFormat = new SimpleDateFormat("yyyyMMdd");
    }

    public Date convertToDate(String dateAsString) {
        Date date = null;
        try {
            date = dateFormat.parse(dateAsString);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return date;
    }
}
