/**
 * Main class to execute program
 */


import duke.dukehelper.DateTimeManager;
import duke.dukehelper.Parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static duke.dukehelper.Routine.queryRoutine;
import static java.time.LocalDate.parse;

public class Duke {

    public static void main(String[] args) {
/*        String input = Parser.parseRawDateTime("20-4-3 1425");
        DateTimeManager dateTime;
        String[] tokens = input.split(",");
        for (String token: tokens){
            if(token == null){
                continue;
            }
            LocalDateTime localDate = LocalDateTime.parse(token);
            String output = localDate.format(DateTimeFormatter.ofPattern("MMM/d/YYYY H:m:s"));
            System.out.println(localDate.toLocalDate().format(DateTimeFormatter.ofPattern("MMM d YYYY")));
            dateTime = new DateTimeManager(token);
            String[] arr = {"datetime"};
            System.out.println(dateTime.getDateFormatted(arr));
        }*/
        queryRoutine(args);
    }
}

