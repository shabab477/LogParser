package net.therap;

import net.therap.util.FileProcessor;

/**
 * @author shabab
 * @since 3/6/18
 */
public class LogParser {

    public static void main(String[] args) {
        System.out.printf("Time\t\tGET/POST-Count\t\tUnique-URI-Count\t\tTotal-Response-Time\n\n\n");
        FileProcessor fileProcessor = new FileProcessor();
        fileProcessor.process(args);
    }

}
