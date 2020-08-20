public class Duke {
    public static Message message;
    public static int charCount = 30;

    /**
     * Prints partition line following each message
     */
    public static void drawPartition(){
        String partitionLine = new String(new char[charCount]).replace("\0", "_");
        System.out.println(partitionLine);
    }

    /**
     * Prints the corresponding messages onto console depending on input parameter.
     *
     * @param command a string of a simple root word to represent a particular message
     */
    public static void printMessage(String command){
        String output = "";
        switch(command){
            case "hello":
                output = message.messageHello;
                break;
            case "bye":
                output = message.messageBye;
                break;
            default:
                output = message.messageErr;
                break;
        }
        System.out.println(output);
        drawPartition();
    }

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        drawPartition();
        printMessage("hello");
        printMessage("bye");
    }
}

