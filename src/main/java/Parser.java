public class Parser {
    /**
     * Process input string, tokenising it by a space seperator and removing trailing spaces per token.
     * @param input Input string from users
     * @return A packet instance with different categories of information sorted out.
     */
    public static Packet parseInput(String input){
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
}
