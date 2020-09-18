/**
 * Class that handles front-end routines that deals directly with user input.
 */
package duke.dukehelper;

import duke.taskhelper.Packet;
import java.util.Scanner;

public class Routine {
    public static Constants.Command commandToReply = null;
    public static boolean echoCommand = false;
    public static Scanner in = new Scanner(System.in);
    public static String input;

    /**
     * Subroutine to handle nested command handling.
     * Useful if another command needs to be called from the current command.
     * @param command nested command in handleCommand()
     * @param isReply
     * @return
     */
    public static void replyRoutine(Constants.Command command, boolean isReply) {
        Packet packet = null;
        commandToReply = command;
        while (commandToReply != null) {
            CommandHandler.handleCommand(Constants.Command.PROMPT_INPUT);
            input = in.nextLine();
            packet = Parser.parseInput(input);
            if (echoCommand) {
                CommandHandler.handleCommand(Constants.Command.ECHO, packet);
            }
            commandToReply = CommandHandler.handleCommand(commandToReply, packet, true, isReply);
            isReply = true;
        }
    }

    /**
     * Main subroutine to receive inputs from users.
     * @param args passed from main()
     */
    public static void queryRoutine(String[] args) {
        // Passed from main.
        // If true, command inputs will be echoed to console.
        // Mainly cosmetic in purpose for printing output from script.
        echoCommand = (args.length > 0) && Boolean.parseBoolean(args[0]);
        System.out.println(Constants.DEFAULT_SAVE_PATH);
        Packet packet = null;
        boolean continueQuery = true;

        UiManager.drawPartition();
        CommandHandler.handleCommand(Constants.Command.HELLO);
        while (continueQuery){
            if (commandToReply != null) {
                replyRoutine(commandToReply, true);
            } else {
                CommandHandler.handleCommand(Constants.Command.PROMPT_INPUT);
                input = in.nextLine();
                packet = Parser.parseInput(input);

                if (echoCommand) {
                    CommandHandler.handleCommand(Constants.Command.ECHO, packet);
                }

                switch (packet.getPacketType()) {
                case "bye":
                    commandToReply = CommandHandler.handleCommand(Constants.Command.BYE);
                    continueQuery = false;
                    break;
                case "list":
                    commandToReply = CommandHandler.handleCommand(Constants.Command.SHOW_LIST);
                    break;
                case "commands":
                    // Flow through
                case "command":
                    commandToReply = CommandHandler.handleCommand(Constants.Command.SHOW_COMMANDS);
                    break;
                case "done":
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
}
