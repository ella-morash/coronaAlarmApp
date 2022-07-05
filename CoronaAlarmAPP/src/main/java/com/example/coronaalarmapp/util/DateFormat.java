package com.example.coronaalarmapp.util;


import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Component
public class DateFormat {

    public long difference(String date, LocalDate dateNow) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date firstDate = sdf.parse(date);
        Date secondDate = sdf.parse(String.valueOf(dateNow));

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());

        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
