/**
 * Main class to execute program
 */


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static duke.dukehelper.Routine.queryRoutine;
import static java.time.LocalDate.parse;

public class Duke {
    public static String[] parseRawDateTime(String input){
        String[] tokens = input.replace("to", "").split("[\\s]+");
        for (String token: tokens){
            System.out.println(token);
        }
        String[] output = new String[2];
        String date = parseDateTime(tokens[0], "date");
        String[] time = new String[2];
        for (int i = 0; i < 2; i++) {
            if (i <= tokens.length-2){
                time[i] = parseDateTime(tokens[i+1], "time");
                output[i] = date+"T"+time[i];
            }
        }
        for(String out: output){
            System.out.println(out);
        }
        return output;
    }

    public static String parseDateTime(String input, final String mode) {
        String partition = mode.equals("time") ? ":":
                           mode.equals("date") ? "/":
                           "?";
        String[] output = new String[3];
        input = input.replaceAll("[\\D]+", "c");
        System.out.println(input);
        String[] tokens = input.split("c");
        for(int i = 0; i < output.length; i++) {
            if (i >  tokens.length-1) {
                output[i] = "00";
            } else if (tokens[i].length() < 2) {
                output[i] = "0" + tokens[i];
            } else if(i == 0 && tokens[i].matches("[0-9]{2}") && mode.equals("date")) {
                output[i] = "20" + tokens[i].substring(0,2);
            } else {
                output[i] = tokens[i];
            }
        }
        System.out.println(String.join(partition, output));
        return String.join(partition, output);
    }

    public static void main(String[] args) {
/*        String output;
        String[] arr = {
                "dog",
                "cat"
        };
        LocalDateTime localDate = LocalDateTime.parse("2004-03-04T08:15:40");
        output = localDate.format(DateTimeFormatter.ofPattern("MMM/d/YYYY H:m:s"));
        System.out.println(localDate.toLocalDate().format(DateTimeFormatter.ofPattern("MMM d YYYY")));
        System.out.println(String.join(" to ", arr));*/
        //String input = "03/04/20 8.30 to 04/04/20 10.30 pm";
        String input = "2020 4 5";
        //System.out.println(parseDateTime(input, "date"));
        System.out.println(parseRawDateTime("20/4/3 14:00"));


        //queryRoutine(args);
    }
}

