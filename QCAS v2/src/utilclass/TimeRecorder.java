/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilclass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;

/**
 *
 * @author Yixin1
 */
public class TimeRecorder {

    public static String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    public String convertTimeToString(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/ddHHmmss");

        return sdf.format(date.getTime());
    }

    public Date convertStringToTime(String time) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formatter = time.substring(0, 10) + " " + time.substring(10, 12) + ":" + 
         time.substring(12, 14) + ":" + time.substring(14, 16);
        
        System.out.println(formatter);
        Date date = new Date();
        try {
            date = sdf.parse(formatter);

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;

    }
}
