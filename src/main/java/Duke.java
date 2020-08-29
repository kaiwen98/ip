import javax.swing.*;
import java.util.Scanner;

public class Duke {
    public static int charCount = Constants.MAX_PARTITION_LINE_LEN;
    public static boolean isListCreated = false;
    public static ListTasks list = null;

    /**
     * Prints partition line following each message
     */


    /**
     * Handles command and prints messages, if any onto console depending on input parameter.
     *
     * @param command A command that is to be executed by Duke.
     */
    public static void handleCommand(Constants.Command command){
        handleCommand(command, null);
    }

    /**
     * Handles command and prints messages, if any onto console depending on input parameter.
     *
     * @param command a string of a simple root word to represent a particular message
     * @param packet an input parameter for the message to print, given the command requires further inputs.
     */
    public static void handleCommand(Constants.Command command, Packet packet){
        String output = "";
        boolean isDrawPartition = true;
        Constants.Error err = null;

        switch(command){
        case HELLO:
            output = UiManager.MESSAGE_LOGO;
            output += UiManager.MESSAGE_HELLO;
            break;
        case BYE:
            output = UiManager.MESSAGE_BYE;
            break;
        case ECHO:
            output = String.format("%s\n", packet.getPacketPayload());
            break;
        case PROMPT_INPUT:
            output = ">>> ";
            isDrawPartition = false;
            break;
        case INSERT_TASK_TODO:
            if (!isListCreated){
                isListCreated = true;
                list = new ListTasks();
            }
            ToDo inputToDo = new ToDo(packet.getPacketPayload());
            err = list.addTask(inputToDo);
            if (err != Constants.Error.NO_ERROR){
                String customErrorMessage = "Due to error input, the task is not added. Try again.";
                UiManager.printErrorMessage(err, customErrorMessage);
                return;
            } else {
                output = UiManager.getMessageTaskAdded(inputToDo);
                output += UiManager.getMessageReportNumTasks(list);
            }
            break;

        case INSERT_TASK_EVENT:
            if (!isListCreated){
                isListCreated = true;
                list = new ListTasks();
            }
            try {
                packet.getParamMap();
            } catch(java.lang.NullPointerException exception) {
                String customErrorMessage = "This command requires a 2 parameter headers and parameter inputs. eg. /at Monday 12-6pm\n";
                customErrorMessage += "Due to error input, the task is not added. Try again.";
                UiManager.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                break;
            }
            Event inputEvent = new Event(packet.getPacketPayload(), packet.getParamMap());
            inputEvent.setParamMap(packet.getParamMap());
            err = list.addTask(inputEvent);

            if (err != Constants.Error.NO_ERROR ){
                String customErrorMessage = "Due to error input, the task is not added. Try again.";
                UiManager.printErrorMessage(err, customErrorMessage);

            } else {
                output = UiManager.getMessageTaskAdded(inputEvent);
                output += UiManager.getMessageReportNumTasks(list);
            }
            break;
        case INSERT_TASK_DEADLINE:
            if (!isListCreated){
                isListCreated = true;
                list = new ListTasks();
            }
            try {
                packet.getParamMap();
            } catch(java.lang.NullPointerException exception) {
                String customErrorMessage = "This command requires a parameter header and parameter inputs. eg. /by Monday\n";
                customErrorMessage += "Due to error input, the task is not added. Try again.";
                UiManager.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                break;
            }
            Deadline inputDeadline = new Deadline(packet.getPacketPayload(), packet.getParamMap());
            err = list.addTask(inputDeadline);
            if (err != Constants.Error.NO_ERROR){
                String customErrorMessage = "Due to error input, the task is not added. Try again.";
                UiManager.printErrorMessage(err, customErrorMessage);
            } else {
                output = UiManager.getMessageTaskAdded(inputDeadline);
                output += UiManager.getMessageReportNumTasks(list);
            }
            break;
        case SHOW_LIST:
            if(!isListCreated || list.getNumTasks() == 0){
                UiManager.printErrorMessage(Constants.Error.NO_LIST);
            } else{
                output = list.showAllTasks();
            }
            break;
        case MARK_TASK_AS_DONE:
            String customErrorMessage = "";
            int index = Integer.parseInt(packet.getPacketPayload().trim()) - 1;
            if(!isListCreated){
                UiManager.printErrorMessage(Constants.Error.NO_LIST);
            } else {
                err = list.markTaskAsDone(index);
                if (err != Constants.Error.NO_ERROR) {
                    if (list.getNumTasks() != 1) {
                        customErrorMessage = "Your list number ranges from 1 to";
                        customErrorMessage += String.format(" %d. Please check your input list number.", list.getNumTasks());
                    } else {
                        customErrorMessage = "There is only 1 task in your list.";
                    }
                    UiManager.printErrorMessage(err, customErrorMessage);
                } else {
                    Task outputTask = list.getTaskByIndex(index);
                    output = UiManager.getMessageTaskMarkAsDone(outputTask);
                }
            }
            break;
        case SHOW_COMMANDS:
            output = UiManager.MESSAGE_COMMAND_LIST;
            break;
        default:
            UiManager.printErrorMessage(Constants.Error.INVALID_COMMAND);
            break;
        }
        System.out.print(output);
        if (isDrawPartition){
            UiManager.drawPartition();
        }
    }

    /**
     * Process input string, tokenising it by a space seperator and removing trailing spaces per token.
     * @param input
     * @return String array of tokens
     */
    public static Packet parseInput(String input){
        String[] token = input.split(" ");
        String[] buffer = new String[]{"", "", "", ""};
        Packet packet = null;
        boolean isParam = false;
        boolean scanParams = false;
        buffer[0] = token[0].trim();
        packet = new Packet(buffer[0]);
        for(int scannedTokens = 1 ; scannedTokens < token.length; scannedTokens++){
            token[scannedTokens] = token[scannedTokens].trim();

            if (!token[scannedTokens].matches("/.*")) {
                if (!scanParams) {
                    buffer[1] += token[scannedTokens] + " ";
                    if (scannedTokens == token.length-1){
                        packet.setPacketPayload(buffer[1]);
                    }
                } else {
                    buffer[3] += token[scannedTokens];
                    if((scannedTokens == token.length - 1) || token[scannedTokens+1].matches("/.*")){
                        packet.addParamToMap(buffer[2], buffer[3]);
                        continue;
                    }
                    buffer[3] += " ";
                }
            } else {
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

    public static void main(String[] args) {
        String input;
        Packet packet = null;
        String[] inputParams = new String[Constants.MAX_ARRAY_LEN];
        boolean continueQuery = true;
        UiManager.drawPartition();
        handleCommand(Constants.Command.HELLO);
        while(continueQuery){
            handleCommand(Constants.Command.PROMPT_INPUT);
            Scanner in = new Scanner(System.in);
            input = in.nextLine();
            packet = parseInput(input.toLowerCase());
            switch(packet.getPacketType()) {
            case "bye":
                handleCommand(Constants.Command.BYE);
                continueQuery = false;
                break;
            case "list":
                handleCommand(Constants.Command.SHOW_LIST);
                break;
            case"commands":
                // Flow through
            case "command":
                handleCommand(Constants.Command.SHOW_COMMANDS);
                break;
            case "done": ;
                handleCommand(Constants.Command.MARK_TASK_AS_DONE, packet);
                break;
            case "todo":
                handleCommand(Constants.Command.INSERT_TASK_TODO, packet);
                break;
            case "event":
                handleCommand(Constants.Command.INSERT_TASK_EVENT, packet);
                break;
            case "deadline":
                handleCommand(Constants.Command.INSERT_TASK_DEADLINE, packet);
                break;
            default:
                UiManager.printErrorMessage(Constants.Error.INVALID_COMMAND);
                break;
            }
        }
    }
}

