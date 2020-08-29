import java.util.Scanner;

public class Duke {
    public static int charCount = 60;
    public static boolean isListCreated = false;
    public static ListOfTasks list = null;

    /**
     * Prints partition line following each message
     */
    public static void drawPartition(){
        String partitionLine = new String(new char[charCount]).replace("\0", "_");
        System.out.println(partitionLine);
    }

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
     * @param param an input parameter for the message to print, given the command requires further inputs.
     */
    public static void handleCommand(Constants.Command command, String[] param){
        String output = "";
        boolean isDrawPartition = true;
        Constants.Error err = null;

        switch(command){
        case HELLO:
            output = Messages.MESSAGE_HELLO;
            break;
        case BYE:
            output = Messages.MESSAGE_BYE;
            break;
        case ECHO:
            output = String.format("%s\n", param[0]);
            break;
        case INPUT:
            output = ">>> ";
            isDrawPartition = false;
            break;
        case INSERT_TASK_TODO:
            if (!isListCreated){
                isListCreated = true;
                list = new ListOfTasks();
            }
            err = list.addTask(param[0]);
            if (err != Constants.Error.NO_ERROR){
                output = Messages.getMessageError(err);
            } else {
                output = String.format("Added: %s\n", param[0]);
            }
            break;

        case INSERT_TASK_EVENT:
            if (!isListCreated){
                isListCreated = true;
                list = new ListOfTasks();
            }
            Event inputEvent = new Event(param[0], param[1], param[2]);
            err = list.addTask(inputEvent);
            if (err != Constants.Error.NO_ERROR){
                output = Messages.getMessageError(err);
            } else {
                output = String.format("Added: %s\n", param[0]);
            }
            break;

        case INSERT_TASK_DEADLINE:
            if (!isListCreated){
                isListCreated = true;
                list = new ListOfTasks();
            }
            Deadline inputDeadline = new Deadline(param[0], param[1]);
            err = list.addTask(inputDeadline);
            if (err != Constants.Error.NO_ERROR){
                output = Messages.getMessageError(err);
            } else {
                output = String.format("Added: %s\n", param[0]);
            }
            break;
        case SHOW_LIST:
            if(!isListCreated){
                output = Messages.getMessageError(Constants.Error.NO_LIST);
            } else{
                output = list.showAllTasks();
            }
            break;
        case MARK_TASK_AS_DONE:
            int index = Integer.parseInt(param[0]) - 1;
            if(!isListCreated){
                output = Messages.getMessageError(Constants.Error.NO_LIST);
            }
            err = list.markTaskAsDone(index);
            if (err != Constants.Error.NO_ERROR){
                output = Messages.getMessageError(err);
            } else {
                Task outputTask = list.getTaskByIndex(index);
                output = Messages.getMessageTaskMarkAsDone(outputTask);
            }
            break;
        case SHOW_COMMANDS:
            output = Messages.MESSAGE_COMMAND_LIST;
            break;
        default:
            output = Messages.getMessageError(Constants.Error.INVALID_COMMAND);
            break;
        }
        System.out.print(output);
        if (isDrawPartition){
            drawPartition();
        }
    }

    /**
     * Process input string, tokenising it by a space seperator and removing trailing spaces per token.
     * @param input
     * @return String array of tokens
     */
    public static String[] parseInput(String input){
        String[]
        String[] token = input.split("/a" +
                "");
        for(String i: token){
            i = i.trim();
        }
        return token;
    }

    public static void main(String[] args) {
        String input;
        String[] inputArray = null;
        String[] inputParams = new String[10];
        boolean continueQuery = true;
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        drawPartition();
        handleCommand(Constants.Command.HELLO);
        while(continueQuery){
            handleCommand(Constants.Command.INPUT);
            Scanner in = new Scanner(System.in);
            input = in.nextLine();
            inputArray = parseInput(input.toLowerCase());

            switch(inputArray[0]) {
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
            case "done":
                inputParams[0] = inputArray[1];
                handleCommand(Constants.Command.MARK_TASK_AS_DONE, inputParams);
                break;
            case "todo":
                //inputParams = parseInput(input);
                inputParams[0] = input;
                handleCommand(Constants.Command.INSERT_TASK_TODO, inputParams);
                break;
            case "event":
                System.arraycopy(inputArray, 1, inputParams, 0, inputArray.length-1);
                handleCommand(Constants.Command.INSERT_TASK_EVENT, inputParams);
            case "deadline":
                System.arraycopy(inputArray, 1, inputParams, 0, inputArray.length-1);
                handleCommand(Constants.Command.INSERT_TASK_DEADLINE, inputParams);
            }
        }
    }
}

