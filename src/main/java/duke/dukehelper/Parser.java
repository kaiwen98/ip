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
     * @param input Input string from users
     * @return A packet instance with different categories of information sorted out.
     */
    public static Packet parseInput(String input){
        String regex = "(\\s+)(/\\w+)(\\s+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = null;
        // A String buffer is used as placeholder for extracted string fragments from parsing input string.
        String[] buffer = new String[]{"", "", "", ""};
        Packet packet = null;
        input += " /end ";
        // Extract command type
        buffer[0] = input.substring(0, input.indexOf(" ")).toLowerCase();
        // Extract task name
        buffer[1] = (input.substring(input.indexOf(" "), input.indexOf(" /", 1))).replace(" ", " ").trim();

        // Initialize packet with known parameters: command and command payload
        packet = new Packet(buffer[0]);
        packet.setPacketPayload(buffer[1]);

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
}
