/**
 * Class to parse inputs and classify the various segments into relevant data types and structures.
 */
package duke.dukehelper;
import duke.taskhelper.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    /**
     * Process input string, tokenising it by a space seperator and removing trailing spaces per token.
     * @param rawInput Input string from users
     * @return A packet instance with different categories of information sorted out.
     */
    public Packet parseInput(String rawInput){
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

    public static String[] parseRawDateTime(String input){
        input = input.replace("to", " ");
        String[] token = input.split("[\\s+] | [\\sto\\s]");
        String[] output = new String[2];
        String date = parseDateTime(token[0], "date");
        String[] time = new String[2];
        for (int i = 0; i < 2; i++) {
            if (token[i+1] != null){
                time[i] = parseDateTime(token[i], "time");
                output[i] = date+"T"+time[i];
            }
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
            System.out.println(tokens[i]);
            if (tokens[i] == null) {
                output[i] = "00";
            } else if (tokens[i].length() < 2) {
                output[i] = "0" + tokens[i];
            } else if(i == 0 && tokens[i].matches("[0-9]{2}") && mode.equals("date")) {
                output[i] = "20" + tokens[i].substring(2,4);
            } else {
                output[i] = tokens[i];
            }
        }
        System.out.println(String.join(partition, output));
        return String.join(partition, output);
    }
}
