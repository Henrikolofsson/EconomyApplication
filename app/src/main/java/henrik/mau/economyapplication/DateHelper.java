package henrik.mau.economyapplication;

import java.text.SimpleDateFormat;

public class DateHelper {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");

    public static String convertDate(long ms){
        return sdf.format(ms);
    }
}
