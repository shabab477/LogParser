package models;

import java.util.HashMap;

/**
 * Created by shabab on 3/5/18.
 */
public class Log {
    private HashMap<String, Boolean> map;
    private int gCounter;
    private int pCounter;
    private int uCounter;
    private long timer;
    private String timeStamp;

    public Log(int hour) {
        this.timeStamp = getTimeStampFromIndex(hour);
        this.map = new HashMap<String, Boolean>();
        this.gCounter = 0;
        this.pCounter = 0;
        this.uCounter = 0;
        this.timer = 0;
    }

    public void addTimer(long time)
    {
        timer += time;
    }

    public void putMethod(String method)
    {
        if(method.equals("G"))
        {
            incrementGetCounter();
        }
        else {
            incrementPostCounter();
        }
    }

    private static String getTimeStampFromIndex(int hour) {
        if(hour < 12)
        {
            if(hour == 0)
            {
                return "12.00 am - 1.00 am";
            }
            else
            {
                String time = String.valueOf(hour) + ".00 am - ";
                int temp = hour + 1;
                if(temp < 12)
                {
                    return time.concat(String.valueOf(temp) + ".00 am");
                }
                else
                {
                    return time.concat(String.valueOf(temp) + ".00 pm");

                }
            }
        }
        else
        {
            if(hour == 12)
            {
                return "12.00 pm - 1.00 pm";
            }
            else
            {
                int actualTime = hour - 12;
                String time = String.valueOf(actualTime)  + ".00 pm - ";
                int temp = hour + 1;
                if(temp < 24)
                {
                    return time.concat(String.valueOf(temp - 12) + ".00 pm");
                }
                else
                {
                    return time.concat(String.valueOf(temp - 12) + ".00 am");
                }

            }
        }
    }

    public int getGCounter() {
        return gCounter;
    }

    public int getPCounter() {
        return pCounter;
    }

    public void setURI(String uri)
    {
        if(!map.containsKey(uri))
        {
            map.put(uri, true);
            ++uCounter;
        }
    }

    private void incrementGetCounter()
    {
        ++gCounter;
    }

    private void incrementPostCounter()
    {
        ++pCounter;
    }

    public String toString()
    {
        return String.format("%s %d/%d %d %d\n", this.timeStamp, this.gCounter, this.pCounter, this.map.size(), this.timer);
    }
}
