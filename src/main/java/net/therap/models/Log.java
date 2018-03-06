package net.therap.models;

import java.util.HashMap;

/**
 * @author shabab
 * @since 3/6/18
 */

public class Log {
    private HashMap<String, Boolean> map;
    private int getRequestCounter;
    private int postRequestCounter;
    private long timer;
    private String timeStamp;
    private static final int HOUR12 = 12;
    private static final int HOUR24 = 24;

    public Log(int hour) {
        this.timeStamp = getTimeStampFromIndex(hour);
        this.map = new HashMap<String, Boolean>();
        this.getRequestCounter = 0;
        this.postRequestCounter = 0;
        this.timer = 0;
    }

    public void addTimer(long time) {
        timer += time;
    }

    public void putMethod(String method) {
        if (method.equals("G")) {
            incrementGetCounter();
        } else {
            incrementPostCounter();
        }
    }

    private static String getTimeStampFromIndex(int hour) {
        if (hour < HOUR12) {
            if (hour == 0) {
                return "12.00 am - 1.00 am";
            } else {
                String time = String.valueOf(hour) + ".00 am - ";
                int temp = hour + 1;
                if (temp < HOUR12) {
                    return time.concat(String.valueOf(temp) + ".00 am");
                } else {
                    return time.concat(String.valueOf(temp) + ".00 pm");

                }
            }
        } else {
            if (hour == HOUR12) {
                return "12.00 pm - 1.00 pm";
            } else {
                int actualTime = hour - HOUR12;
                String time = String.valueOf(actualTime) + ".00 pm - ";
                int temp = hour + 1;
                if (temp < HOUR24) {
                    return time.concat(String.valueOf(temp - HOUR12) + ".00 pm");
                } else {
                    return time.concat(String.valueOf(temp - HOUR12) + ".00 am");
                }

            }
        }
    }

    public void setURI(String uri) {
        if (!map.containsKey(uri)) {
            map.put(uri, true);
        }
    }

    private void incrementGetCounter() {
        ++getRequestCounter;
    }

    private void incrementPostCounter() {
        ++postRequestCounter;
    }

    public HashMap<String, Boolean> getMap() {
        return map;
    }

    private void setMap(HashMap<String, Boolean> map) {
        this.map = map;
    }

    public int getGetRequestCounter() {
        return getRequestCounter;
    }

    private void setGetRequestCounter(int getRequestCounter) {
        this.getRequestCounter = getRequestCounter;
    }

    public int getPostRequestCounter() {
        return postRequestCounter;
    }

    private void setPostRequestCounter(int postRequestCounter) {
        this.postRequestCounter = postRequestCounter;
    }

    public long getTimer() {
        return timer;
    }

    private void setTimer(long timer) {
        this.timer = timer;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    private void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return String.format("%s %d/%d %d %d\n", this.timeStamp, this.getRequestCounter, this.postRequestCounter, this.map.size(), this.timer);
    }
}
