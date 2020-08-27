import java.util.*;
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
        printMessage(command, null);
    }

    public static void printMessage(String command, String param){
        String output = "";
        boolean isDrawPartition = true;
        switch(command){
        case "hello":
            output = message.messageHello;
            break;
        case "bye":
            output = message.messageBye;
            break;
        case "echo":
            output = String.format("%s\n", param);
            break;
        case "input":
            output = ">>> ";
            isDrawPartition = false;
            break;
        default:
            output = message.messageErr;
            break;
        }
        System.out.print(output);

        if (isDrawPartition){
            drawPartition();
        }
    }

    public static void main(String[] args) {
        String input;
        boolean continueQuery = true;
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        drawPartition();
        printMessage("hello");
        while(continueQuery){
            printMessage("input");
            Scanner in = new Scanner(System.in);
            input = in.nextLine();
            switch(input) {
            case "bye":
                printMessage("bye");
                continueQuery = false;
                break;
            default:
                printMessage("echo", input);
                break;
            }
        }
    }
}

