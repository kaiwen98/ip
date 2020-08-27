/**
 * Class recording all possible messages printable on console
 */
public class Message {
    public static String messageHello = "Hello! I'm Duke."
            + "\nType \"command\" to check available commands to try out!"
            + "\nWhat can I do for you?\n";

    public static String messageBye = "Bye. Hope to see you again soon!\n";

    public static String messageCommandList = "\nThese are implemented commands that you can use.\n"
            + "1. bye\n"
            + "\t Exit the program\n"
            + "2. list\n"
            + "\t Show full list of appended tasks\n"
            + "3. commands\n"
            + "\t Show full list of commands\n"
            + "4. done <integer>\n"
            + "\t Mark a task by number <integer> as done.\n"
            + "4. * Other inputs *\n"
            + "\t Will be interpreted as input tasks. Input task will then be added to the list.\n "
            + "\t It will be assumed to be incomplete.\n\n";


    public static String getErrorMessage(Error error){
        switch(error){
        case NO_LIST:
            return "No list exists yet. Begin by adding some tasks!";
        case WRONG_ARGUMENTS:
            return "Wrong arguments supplied. Please enter \"command\" for correct arguments.\n";
        case INVALID_COMMAND:
            return "Command not recognised. Please enter \"command\" for command list!\n";
        default:
            return "";
        }
    }
}
