package com.avtor31;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadLocalDateFormatService {

    private static final ThreadLocal<DateFormat> tl = ThreadLocal.withInitial(() -> {
        System.out.println("It was created new SimpleDateFormat object");
        return new SimpleDateFormat("yyyyMMdd");
    });

    public Date convertToDate(String dateAsString) {
        Date date = null;
        try {
            date = tl.get().parse(dateAsString);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return date;
    }

}
