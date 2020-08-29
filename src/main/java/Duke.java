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
    public static void handleCommand(Command command){
        handleCommand(command, null);
    }

    /**
     * Handles command and prints messages, if any onto console depending on input parameter.
     *
     * @param command a string of a simple root word to represent a particular message
     * @param param an input parameter for the message to print, given the command requires further inputs.
     */
    public static void handleCommand(Command command, String[] param){
        String output = "";
        boolean isDrawPartition = true;
        Error err = null;

        switch(command){
        case HELLO:
            output = Messages.messageHello;
            break;
        case BYE:
            output = Messages.messageBye;
            break;
        case ECHO:
            output = String.format("%s\n", param[0]);
            break;
        case INPUT:
            output = ">>> ";
            isDrawPartition = false;
            break;
        case INSERT_TASK:
            if (!isListCreated){
                isListCreated = true;
                list = new ListOfTasks();
            }
            err = list.addTask(param[0]);
            if (err != Error.NO_ERROR){
                output = Messages.getMessageError(err);
            } else {
                output = String.format("Added: %s\n", param[0]);
            }
            break;
        case SHOW_LIST:
            if(!isListCreated){
                output = Messages.getMessageError(Error.NO_LIST);
            } else{
                output = list.showAllTasks();
            }
            break;
        case MARK_TASK_AS_DONE:
            // By convention, we take index as the index on an array (Starting from 0)
            int index = Integer.parseInt(param[0]) - 1;
            if(!isListCreated){
                output = Messages.getMessageError(Error.NO_LIST);
            }
            err = list.markTaskAsDone(index);
            if (err != Error.NO_ERROR){
                output = Messages.getMessageError(err);
            } else {
                Task outputTask = list.getTaskByIndex(index);
                output = Messages.getMessageTaskMarkAsDone(outputTask);
            }
            break;
        case SHOW_COMMANDS:
            output = Messages.messageCommandList;
            break;
        default:
            output = Messages.getMessageError(Error.INVALID_COMMAND);
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
        String[] token = input.split("\\s+");
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
        handleCommand(Command.HELLO);
        while(continueQuery){
            handleCommand(Command.INPUT);
            Scanner in = new Scanner(System.in);
            input = in.nextLine();
            inputArray = parseInput(input.toLowerCase());

            switch(inputArray[0]) {
            case "bye":
                handleCommand(Command.BYE);
                continueQuery = false;
                break;
            case "list":
                handleCommand(Command.SHOW_LIST);
                break;
            case"commands":
                // Flow through
            case "command":
                handleCommand(Command.SHOW_COMMANDS);
                break;
            case "done":
                inputParams[0] = inputArray[1];
                handleCommand(Command.MARK_TASK_AS_DONE, inputParams);
                break;
            default:
                //inputParams = parseInput(input);
                inputParams[0] = input;
                handleCommand(Command.INSERT_TASK, inputParams);
                break;
            }
        }
    }
}

