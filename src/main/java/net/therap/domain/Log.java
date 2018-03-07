package net.therap.domain;

import net.therap.util.TimeCalculator;

import java.util.HashMap;

/**
 * @author shabab
 * @since 3/6/18
 */

public class Log {
    private HashMap<String, Boolean> uriMap;
    private int getRequestCounter;
    private int postRequestCounter;
    private long timer;
    private String timeStamp;

    public Log(int hour) {
        this.timeStamp = TimeCalculator.getTimeStampFromIndex(hour);
        this.uriMap = new HashMap<>();
    }

    public void addTimer(long time) {
        timer += time;
    }

    public void putMethod(String method) {
        if (method.equals("G")) {
            this.getRequestCounter++;
        } else {
            this.postRequestCounter++;
        }
    }

    public void putURI(String uri) {
        if (!uriMap.containsKey(uri)) {
            uriMap.put(uri, true);
        }
    }

    public HashMap<String, Boolean> getUriMap() {
        return uriMap;
    }

    private void setUriMap(HashMap<String, Boolean> uriMap) {
        this.uriMap = uriMap;
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
        return String.format("%s %d/%d %d %d\n", this.timeStamp, this.getRequestCounter,
                this.postRequestCounter, this.uriMap.size(), this.timer);
    }
}
