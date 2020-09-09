/**
 * Main class to execute program
 */

import duke.dukehelper.CommandHandler;
import duke.dukehelper.Constants;
import duke.dukehelper.DukeException;
import duke.dukehelper.Parser;
import duke.dukehelper.UiManager;
import duke.taskhelper.ListTasks;
import duke.taskhelper.Packet;

import java.util.Scanner;

public class Duke {
    public static int charCount = Constants.MAX_PARTITION_LINE_LEN;
    public static boolean isListCreated = false;
    public static ListTasks list = null;
    public static Constants.Command commandToReply = null;

    public static void main(String[] args) {
        String input;
        Packet packet = null;
        String[] inputParams = new String[Constants.MAX_ARRAY_LEN];
        boolean continueQuery = true;
        Scanner in = new Scanner(System.in);

        UiManager.drawPartition();
        CommandHandler.handleCommand(Constants.Command.HELLO);
        while (continueQuery){
            CommandHandler.handleCommand(Constants.Command.PROMPT_INPUT);
            input = in.nextLine();
            if (commandToReply != null) {
                packet = new Packet("reply", input.toLowerCase().trim());
                commandToReply = CommandHandler.handleCommand(commandToReply, packet, true, true);
                continue;
            }
            packet = Parser.parseInput(input);
            switch (packet.getPacketType()) {
            case "bye":
                commandToReply = CommandHandler.handleCommand(Constants.Command.BYE);
                continueQuery = false;
                break;
            case "list":
                commandToReply = CommandHandler.handleCommand(Constants.Command.SHOW_LIST);
                break;
            case"commands":
                // Flow through
            case "command":
                commandToReply = CommandHandler.handleCommand(Constants.Command.SHOW_COMMANDS);
                break;
            case "done": ;
                commandToReply = CommandHandler.handleCommand(Constants.Command.MARK_TASK_DONE, packet);
                break;
            case "todo":
                commandToReply = CommandHandler.handleCommand(Constants.Command.INSERT_TASK_TODO, packet);
                break;
            case "event":
                commandToReply = CommandHandler.handleCommand(Constants.Command.INSERT_TASK_EVENT, packet);
                break;
            case "deadline":
                commandToReply = CommandHandler.handleCommand(Constants.Command.INSERT_TASK_DEADLINE, packet);
                break;
            case "remove":
                commandToReply = CommandHandler.handleCommand(Constants.Command.REMOVE_TASK, packet);
                break;
            case "save":
                commandToReply = CommandHandler.handleCommand(Constants.Command.SAVE_FILE, packet);
                break;
            case "load":
                commandToReply = CommandHandler.handleCommand(Constants.Command.LOAD_FILE, packet);
                break;
            case "saves":
                commandToReply = CommandHandler.handleCommand(Constants.Command.SHOW_SAVE_STATES);
                break;
            default:
                DukeException.printErrorMessage(Constants.Error.INVALID_COMMAND);
                break;
            }
        }
    }
}

