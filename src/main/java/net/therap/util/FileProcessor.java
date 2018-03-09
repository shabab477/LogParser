package net.therap.util;

import net.therap.domain.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shabab
 * @since 3/7/18
 */
public class FileProcessor {
    private static final int TOTAL_HOURS = 24;

    public void process(String[] arguments) {
        if (arguments.length >= 1) {

            List<Log> logList = parseFile(arguments[0]);

            if (arguments.length == 2 && (arguments[1].equals("--sorted") || arguments[1].equals("--s"))) {
                logList.sort((logOne, logTwo) ->
                        (logTwo.getGetRequestCounter() + logTwo.getPostRequestCounter()) -
                                (logOne.getGetRequestCounter() + logOne.getPostRequestCounter()));
            }

            logList.stream().forEach(System.out::println);

        } else {

            System.err.println("Must give at least a file name");
        }
    }

    private List<Log> parseFile(String fileName) {
        List<Log> logList = initList();
        System.out.println(fileName);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
            String line;

            while ((line = reader.readLine()) != null) {
                String array[] = line.split("\\s+");
                Pattern p = Pattern.compile(".*ms$");
                Matcher matcher = p.matcher(line);

                if (matcher.find()) {
                    String timeString = (array[array.length - 1]);
                    String methodString = array[array.length - 2];
                    String uriString = array[array.length - 3];

                    long time = Long.parseLong(timeString.split("=")[1].replace("ms", ""));
                    String method = methodString.replace(",", "");
                    Pattern pattern = Pattern.compile("\\[(.*?)\\]");
                    Matcher m = pattern.matcher(uriString);
                    String uri = null;

                    while (m.find()) {
                        uri = (m.group(1));
                    }

                    int logTime = Integer.parseInt(array[1].split(":")[0]);
                    Log log = logList.get(logTime);
                    log.addTimer(time);
                    log.putMethod(method);
                    log.putURI(uri);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File Name not correct");
            return Collections.<Log>emptyList();
        } catch (IOException e) {
            System.err.println("File could not be read");
            return Collections.<Log>emptyList();
        }

        return logList;
    }

    private List<Log> initList() {
        List<Log> logList = new ArrayList<>();
        for (int c = 0; c < TOTAL_HOURS; c++) {
            logList.add(new Log(c));
        }
        return logList;
    }
}
