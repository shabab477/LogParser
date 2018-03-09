package net.therap.util;

/**
 * @author shabab
 * @since 3/7/18
 */
public class TimeCalculator {
    private static final int HOUR_12 = 12;
    private static final int HOUR_24 = 24;
    private static final int ZERO_HOUR = 0;

    public static String getTimeStampFromIndex(int hour) {
        boolean is24Hours = hour >= HOUR_12;
        if (!is24Hours && hour == ZERO_HOUR) {
            return "12.00 am - 1.00 am";
        } else if (is24Hours && hour == HOUR_12) {
            return "12.00 pm - 1.00 pm";
        } else {
            String time = (is24Hours ? hour - HOUR_12 : hour) + ".00 - "
                    + (is24Hours ? "p.m" : "a.m");
            int nextHour = hour + 1;
            boolean isCriticalCase = hour < (is24Hours ? HOUR_24 : HOUR_12);
            return time + (is24Hours ? nextHour - HOUR_24 : nextHour) + ".00 "
                    + isAmOrPM(isCriticalCase, is24Hours);
        }
    }

    private static String isAmOrPM(boolean isCriticalCase, boolean is24Hours) {
        if (is24Hours) {
            return isCriticalCase ? "a.m" : "p.m";
        } else {
            return isCriticalCase ? "p.m" : "a.m";
        }
    }
}
