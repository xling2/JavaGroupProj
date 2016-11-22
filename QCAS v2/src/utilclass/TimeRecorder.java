/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilclass;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Yixin1
 */
public abstract class TimeRecorder {

    public static String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }
}
