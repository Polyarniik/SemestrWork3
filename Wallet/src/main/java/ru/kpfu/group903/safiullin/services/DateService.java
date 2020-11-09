package ru.kpfu.group903.safiullin.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateService {
    Long weekMSec = 604800000L;
    Long yearMSec = 31536000000L;

    public Long toUnix(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime();
    }

    public String toString(Long unix) {
        return new SimpleDateFormat("dd.MM.yyyy").format(unix);
    }

    public Long lastWeek() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.parse(format.format(new Date().getTime() - weekMSec)).getTime();
    }

    public Long lastYear() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.parse(format.format(new Date().getTime() - yearMSec)).getTime();
    }
}
