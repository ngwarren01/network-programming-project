// This Time class declare the getCurrentTime() that return the latest time

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    public String getCurrentTime(){

        Date currentDate = new Date();
        SimpleDateFormat timeformat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

        return timeformat.format(currentDate);
    }

}
