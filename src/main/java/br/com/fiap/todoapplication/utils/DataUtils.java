package br.com.fiap.todoapplication.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@UtilityClass
public class DataUtils {

    public Calendar parseStringToCalendar(String date) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date dateParsed = sdf.parse(date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateParsed);

            System.out.println(calendar.getTime());

            return calendar;
        }
        catch (ParseException e) {
            e.printStackTrace();
            throw new Exception("Error trying parse " + date + " to Calendar entity");
        }
    }
}
