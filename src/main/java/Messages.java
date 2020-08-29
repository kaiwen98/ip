/**
 * Class recording all possible messages printable on console
 */
public class Messages {
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
            + "\n5. * Other inputs *\n"
            + "\t Will be interpreted as input tasks. Input task will then be added to the list.\n "
            + "\t Tasks added this way are assumed to not be done and recorded accordingly.\n\n";

    public static String getMessageError(Constants.Error error, String customErrorMessage){
        customErrorMessage = customErrorMessage.equals("")? "" : "\t[NOTE]: "+ customErrorMessage;
        switch(error){
        case NO_LIST:
            return "No list exists yet. Begin by adding some tasks!\n" + customErrorMessage;
        case WRONG_ARGUMENTS:
            return "Wrong arguments supplied. Please enter \"command\" to check acceptable arguments.\n";
        case INVALID_COMMAND:
            return "Command not recognised. Please enter \"command\" for command list!\n";
        case NO_ERROR:
            return "";
        default:
            return customErrorMessage;
        }
    }

    public static String getMessageError(Constants.Error error){
        return getMessageError(error, "");
    }

    public static String getMessageTaskMarkAsDone(Task outputTask){
        return String.format("Nice! I've marked this task as done:%s\n", outputTask);
    }
}
