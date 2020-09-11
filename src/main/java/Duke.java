/**
 * Main class to execute program
 */


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static duke.dukehelper.Routine.queryRoutine;
import static java.time.LocalDate.parse;

public class Duke {
    public static void main(String[] args) {
        String output;
        String[] arr = {
                "dog",
                "cat"
        };
        LocalDateTime localDate = LocalDateTime.parse("2004-03-04T10:15:40");
        output = localDate.format(DateTimeFormatter.ofPattern("MMM/d/YYYY H:m:s"));
        System.out.println(localDate.toLocalDate().format(DateTimeFormatter.ofPattern("MMM d YYYY")));
        System.out.println(String.join(" to ", arr));
        //queryRoutine(args);
    }
}

