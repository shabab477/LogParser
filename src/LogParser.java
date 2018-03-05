import models.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shabab on 3/5/18.
 */
public class LogParser {

    public static void main(String[] args) {
        System.out.printf("Time   GET/POST-Count   Unique-URI-Count   Total-Response-Time\n\n\n");
        if(args.length == 1)
        {
            List<Log> list = parseFile(args[0]);
            list.stream().forEach(System.out::println);
        }
        else if(args.length == 2)
        {
            if(args[1].equals("--sorted") || args[1].equals("--s"))
            {
                List<Log> list = parseFile(args[0], (a, b) -> (b.getGCounter() + b.getPCounter()) -  (a.getGCounter() + a.getPCounter()));
                list.stream().forEach(System.out::println);
            }
            else
            {
                System.err.println("Wrong flag has been used");
            }
        }
        else
        {
            System.err.println("Must give at least a file name");
        }
    }


    private static List<Log> parseFile(String fileName, Comparator<Log> comparator)
    {
        List<Log> list = parseFile(fileName);
        list.sort(comparator);
        return list;
    }

    private static List<Log> parseFile(String fileName) {
        ArrayList<Log> list = initList();
        int counter = 0;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
            String line = null;

            while ((line = reader.readLine()) != null)
            {
                String array[] = line.split("\\s+");
                Pattern p = Pattern.compile(".*ms$");
                Matcher matcher = p.matcher(line);
                if(matcher.find())
                {
                    String timeString = (array[array.length - 1]);
                    String methodString = array[array.length - 2];
                    String uriString = array[array.length - 3];
                    counter++;

                    long time = Long.parseLong(timeString.split("=")[1].replace("ms", ""));
                    String method = methodString.replace(",", "");
                    Pattern pattern = Pattern.compile("\\[(.*?)\\]");
                    Matcher m = pattern.matcher(uriString);
                    String uri = null;
                    while(m.find()) {
                        uri = (m.group(1));
                    }

                    Log log = list.get(getHourFromTime(array[1]));
                    log.addTimer(time);
                    log.putMethod(method);
                    log.setURI(uri);
                }
            }

        } catch (FileNotFoundException e) {

            System.err.println("File Name not correct");
        } catch (IOException e) {
            System.err.println("File could not be read");
        }


        return list;

    }

    private static ArrayList<Log> initList() {
        ArrayList<Log> list = new ArrayList<Log>();
        for(int c = 0; c < 24; c++)
        {
            list.add(new Log(c));
        }
        return list;
    }

    private static int getHourFromTime(String timeStamp)
    {
        return Integer.parseInt(timeStamp.split(":")[0]);
    }


}
