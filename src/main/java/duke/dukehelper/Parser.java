/**
 * Class to parse inputs and classify the various segments into relevant data types and structures.
 */
package duke.dukehelper;
import duke.taskhelper.Packet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    /**
     * Process input string, tokenising it by a space seperator and removing trailing spaces per token.
     * @param rawInput Input string from users
     * @return A packet instance with different categories of information sorted out.
     */

    public static Packet parseInput(String rawInput){
        String regex = "(\\s+)(/\\w+)(\\s+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = null;
        // A String buffer is used as placeholder for extracted string fragments from parsing input string.
        String[] buffer = new String[]{"", "", "", ""};
        Packet packet = null;
        String input = rawInput + " /end ";
        // Extract command type
        buffer[0] = input.substring(0, input.indexOf(" ")).toLowerCase();
        // Extract task name
        buffer[1] = (input.substring(input.indexOf(" "), input.indexOf(" /", 1))).replace(" ", " ").trim();

        // Initialize packet with known parameters: command and command payload
        packet = new Packet(buffer[0], rawInput);
        packet.setPacketPayload(buffer[1]);
//        System.out.println(String.format("0: %s, 1: %s", buffer[0], buffer[1]));

        // Implemented in case future commands with multiple param types are to be implemented
        do {
            input = input.substring(input.indexOf(" /")).trim();
            if (input.equals("/end")) break;
            // Extract param type
            buffer[2] = input.substring(input.indexOf("/"), input.indexOf(" ")).toLowerCase();
            // Remove param type from input string
            input = (input.replaceFirst("(/\\w+)(\\s+)", "\0")).trim();
            input = " ".concat(input).concat(" ");
            // Extract param name
            matcher = pattern.matcher(input);
            matcher.find();
            buffer[3] = (input.substring(0, matcher.start())).trim();
            packet.addParamToMap(buffer[2], buffer[3]);
        } while (true);

        return packet;
    }
    // 030420 8 to 9 pm
    // 03/04/20 8 am to 10 pm
    public static String parseRawDateTime(String input){
        int matchCount = 0;
        String[] tokens = input.replaceAll("[\\s]+[\\D]+[\\s]+|,", " ").split("[\\s]+");

        // If input matches the required pattern, no need to continue processing
        for (String token : tokens){
            if (token.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
                matchCount++;
            }
        }
        if (matchCount == tokens.length) {
            return input;
        }
        String[] output = new String[2];
        String date = "";
        String time = "";
        if (tokens.length == 2) {
            date = parseDateTime(tokens[0], "date");
            output[0] = date + "T" + parseDateTime(tokens[1], "time");
            output[1] = "\0";
        } else if (tokens.length == 3) {
            date = parseDateTime(tokens[0], "date");
            output[0] = date + "T" + parseDateTime(tokens[1], "time");
            output[1] = date + "T" + parseDateTime(tokens[2], "time");
        } else if (tokens.length == 4) {
            date = parseDateTime(tokens[0], "date");
            output[0] = date + "T" + parseDateTime(tokens[1], "time");
            date = parseDateTime(tokens[2], "date");
            output[1] = date + "T" + parseDateTime(tokens[3], "time");
        }
        return String.join(",", output).trim();
    }

    private static String parseDateTime(String input, final String mode) {
        String partition = mode.equals("time") ? ":":
                mode.equals("date") ? "-":
                        "?";
        String[] output = new String[3];
        if (input.matches("[0-9]{4}") && mode.equals("time")) {
            input = input.substring(0,2) + ","+ input.substring(2) + ",00";
        } else if (input.matches("[0-9]{6}") && mode.equals("date")) {
            input = input.substring(0,2) + ","+ input.substring(2, 4) + "," + input.substring(4);
        }
        input = input.replaceAll("[\\D]+", "c");
        String[] tokens = input.split("c");
        for(int i = 0; i < output.length; i++) {
            if (i >  tokens.length-1) {
                output[i] = "00";
            } else if (tokens[i].length() < 2) {
                output[i] = "0" + tokens[i];
            } else if (i == 0 && tokens[i].matches("[0-9]{2}") && mode.equals("date")) {
                output[i] = "20" + tokens[i].substring(0,2);
            } else {
                output[i] = tokens[i];
            }
        }
        return String.join(partition, output);
    }
}
