import java.util.Scanner;
import duke.taskhelper.*;
import duke.dukehelper.*;

public class Duke {
    public static int charCount = Constants.MAX_PARTITION_LINE_LEN;
    public static boolean isListCreated = false;
    public static ListTasks list = null;


    public static void main(String[] args) {
        String input;
        Packet packet = null;
        String[] inputParams = new String[Constants.MAX_ARRAY_LEN];
        boolean continueQuery = true;
        Scanner in = new Scanner(System.in);

        UiManager.drawPartition();
        CommandHandler.handleCommand(Constants.Command.HELLO);
        while(continueQuery){
            CommandHandler.handleCommand(Constants.Command.PROMPT_INPUT);
            input = in.nextLine();
            packet = Parser.parseInput(input.toLowerCase());
            switch(packet.getPacketType()) {
            case "bye":
                CommandHandler.handleCommand(Constants.Command.BYE);
                continueQuery = false;
                break;
            case "list":
                CommandHandler.handleCommand(Constants.Command.SHOW_LIST);
                break;
            case"commands":
                // Flow through
            case "command":
                CommandHandler.handleCommand(Constants.Command.SHOW_COMMANDS);
                break;
            case "done": ;
                CommandHandler.handleCommand(Constants.Command.MARK_TASK_DONE, packet);
                break;
            case "todo":
                CommandHandler.handleCommand(Constants.Command.INSERT_TASK_TODO, packet);
                break;
            case "event":
                CommandHandler.handleCommand(Constants.Command.INSERT_TASK_EVENT, packet);
                break;
            case "deadline":
                CommandHandler.handleCommand(Constants.Command.INSERT_TASK_DEADLINE, packet);
                break;
            case "remove":
                CommandHandler.handleCommand(Constants.Command.REMOVE_TASK, packet);
                break;
            default:
                DukeException.printErrorMessage(Constants.Error.INVALID_COMMAND);
                break;
            }
        }
    }
}

