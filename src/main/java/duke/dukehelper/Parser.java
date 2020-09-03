package duke.dukehelper;
import duke.taskhelper.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    // Eliminated because it is not very elegant, but kept for reference
    /*public static Packet parseInput(String input){
        String[] token = input.split(" ");
        // A String buffer is used as placeholder for extracted string fragments from parsing input string.
        String[] buffer = new String[]{"", "", "", ""};
        Packet packet = null;
        boolean isParam = false;
        boolean scanParams = false;

        // Accounts for command type
        buffer[0] = token[0].trim();
        packet = new Packet(buffer[0]);
        for(int scannedTokens = 1 ; scannedTokens < token.length; scannedTokens++){
            token[scannedTokens] = token[scannedTokens].trim();

            if (!token[scannedTokens].matches("/.*")) {
                if (!scanParams) {
                    // Accounts for task name
                    buffer[1] += token[scannedTokens] + " ";
                    if (scannedTokens == token.length-1){
                        packet.setPacketPayload(buffer[1]);
                    }
                } else {
                    // Accounts for params, that follow the param type in user command
                    buffer[3] += token[scannedTokens];
                    if((scannedTokens == token.length - 1) || token[scannedTokens+1].matches("/.*")){
                        packet.addParamToMap(buffer[2], buffer[3]);
                        continue;
                    }
                    buffer[3] += " ";
                }
            } else {
                // Accounts for param type, eg. /at
                if (!scanParams){
                    packet.setPacketPayload(buffer[1]);
                }
                scanParams = true;
                buffer[2] = token[scannedTokens];
                packet = new Packet(buffer[0], buffer[1]);
                buffer[3] = "";
            }
        }
        return packet;
    }
*/
    /**
     * Process input string, tokenising it by a space seperator and removing trailing spaces per token.
     * @param input Input string from users
     * @return A packet instance with different categories of information sorted out.
     */
    public static Packet parseInput(String input){
        String regex = "(/\\w+)(\\s+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        input += " /end ";
        // A String buffer is used as placeholder for extracted string fragments from parsing input string.
        String[] buffer = new String[]{"", "", "", ""};
        Packet packet = null;
        //try {
            // Extract command type
            buffer[0] = input.substring(0, input.indexOf(" ")).toLowerCase();
            // Extract task name
            buffer[1] = (input.substring(input.indexOf(" "), input.indexOf(" /", 1))).replace(" ", " ").trim();
            packet = new Packet(buffer[0]);
            packet.setPacketPayload(buffer[1]);

            // Implemented in case future commands with multiple param types are to be implemented
            do {
                input = input.substring(input.indexOf(" /")).trim();
                if (input.equals("end")) break;
                // Extract param type
                buffer[2] = input.substring(input.indexOf("/"), input.indexOf(" ")).toLowerCase();
                System.out.println(input);
                // Remove param type from input string
                input = input.replaceFirst("(/\\w+)(\\s+)", "\0");
                input =" " + input;
                System.out.println(input);
                // Extract param name
                System.out.println(input.indexOf(' '));
                matcher = pattern.matcher(input);
                buffer[3] = (input.substring(0, input.indexOf("/e")));
                System.out.println(buffer[3]);
                // If following param is blank, do not add to map
                packet.addParamToMap(buffer[2], buffer[3]);
            } while (true);
/*
        } catch (StringIndexOutOfBoundsException e) {
            DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, "Cannot start command with a param call!");
        }*/

        return packet;
    }
}
