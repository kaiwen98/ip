/**
 * Class handling all possible console outputs.
 */
public class UiManager {
    public static final String MESSAGE_LOGO = "Hello from\n"
            + "____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n\n\n"
            + "ROFL:ROFL:ROFL:ROFL:ROFL:ROFL\n"
            + "..........___|___.........\n"
            + "...L..../.......[`\\........\n"
            + "..LOL===........[__|.......\n"
            + "...L....\\..........|.......\n"
            + ".........\\_________/.......\n"
            + "...........|...|...........\n"
            + "..........————————/........\n\n\n";

    public static final String MESSAGE_HELLO = "Hello! I'm Duke."
            + "\nType \"command\" to check available commands to try out!"
            + "\nWhat can I do for you?\n";

    public static final String MESSAGE_BYE = "Bye. Hope to see you again soon!\n";

    public static final String MESSAGE_COMMAND_LIST = "\nThese are implemented commands that you can use.\n"
            + "\n1. bye\n"
            + "\t Exit the program\n"
            + "\n2. list\n"
            + "\t Show full list of appended tasks\n"
            + "\n3. commands\n"
            + "\t Show full list of commands\n"
            + "\n4. done <integer>\n"
            + "\t Mark a task by number <integer> as done.\n"
            + "   @ <integer>:\t Task number on the list. Out-of-bounds and negative inputs are not allowed.\n"
            + "\n5. todo <string>\n"
            + "\t Will be interpreted as input tasks. Input task will then be added to the list.\n "
            + "\t Tasks added this way are assumed to not be done and recorded accordingly.\n\n"
            + "\n6. event <string> <parameter type> <parameter 1> <parameter 2>\n"
            + "\tAdd a task which is happening in the future with specific date and time\n "
            + "\t@ <string>:\t Task name.\n"
            + "\t@ <parameter type>:\t /at\n"
            + "\t\t# <parameter 1>:\t Date\n"
            + "\t\t# <parameter 2>:\t Time from start to end, no spaces\n"
            + "\n7. deadline <string> <parameter type> <parameter 1>\n"
            + "\tAdd a task with a specific deadline\n "
            + "\t@ <string>:\t Task name.\n"
            + "\t@ <parameter type>:\t /by\n"
            + "\t\t# <parameter 1>:\t Date w/o time\n";

    public static void printErrorMessage(Constants.Error error, String customErrorMessage){
        String output = "";
        customErrorMessage = customErrorMessage.equals("")? "" : "\t[NOTE]: "+ customErrorMessage;
        switch(error){
        case NO_LIST:
            output = "No list exists yet. Begin by adding some tasks!\n";
            break;
        case WRONG_ARGUMENTS:
            output = "Wrong arguments supplied. Please enter \"command\" to check acceptable arguments.\n";
            break;
        case INVALID_COMMAND:
            output = "Command not recognised. Please enter \"command\" for command list!\n";
            break;
        case NO_ERROR:
            output = "";
            break;
        default:
            // Fall through
            break;
        }
        System.out.println(output + customErrorMessage);
    }
    public static void printErrorMessage(Constants.Error error){
        printErrorMessage(error, "");
    }
    public static String getMessageTaskAdded(Task task){
        return String.format("Got it! I've added this task:\n  %s\n", task);
    }
    public static String getMessageReportNumTasks(ListTasks list){
        char isS = list.getNumTasks() > 1? 's':0;
        return String.format("Now, you have %d task%c in the list.\n", list.getNumTasks(),isS);
    }
    public static String getMessageTaskMarkAsDone(Task outputTask){
        return String.format("Nice! I've marked this task as done:%s\n", outputTask);
    }

    public static void drawPartition(){
        String partitionLine = new String(new char[Constants.MAX_PARTITION_LINE_LEN]).replace("\0", "_");
        System.out.println(partitionLine);
    }
}
