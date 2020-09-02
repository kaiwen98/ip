package duke.dukehelper;
import duke.taskhelper.*;
public class Parser {

    /**
     * Process input string, tokenising it by a space seperator and removing trailing spaces per token.
     * @param input Input string from users
     * @return A packet instance with different categories of information sorted out.
     */
    public static Packet parseInput(String input){
        // A String buffer is used as placeholder for extracted string fragments from parsing input string.
        input += "  /";
        String[] buffer = new String[]{"", "", "", ""};
        Packet packet = null;

        // Extract command type
        buffer[0] = input.substring(0, input.indexOf(" ")).toLowerCase();
        // Extract task name
        buffer[1] = (input.substring(input.indexOf(" "), input.indexOf('/'))).replace(" ", " ").trim();
        packet = new Packet(buffer[0]);
        packet.setPacketPayload(buffer[1]);

        // Implemented in case future commands with multiple param types are to be implemented
        do {
            input = input.substring(input.indexOf('/')).trim();
            if (input.length() == 1) break;
            // Extract param type
            buffer[2] = input.substring(input.indexOf('/'), input.indexOf(" ")).toLowerCase();
            // Remove param type from input string
            input = input.replaceFirst("(/\\w+)(\\s+)", "\0");
            // Extract param name
            buffer[3] = (input.substring(0, input.indexOf('/'))).trim();
            // If following param is blank, do not add to map
            if ( !buffer[3].equals("")) {
                packet.addParamToMap(buffer[2], buffer[3]);
            }
        } while (true);

        return packet;
    }
}
