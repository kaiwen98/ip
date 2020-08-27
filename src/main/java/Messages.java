/**
 * Class recording all possible messages printable on console
 */
public class Messages {

    public static String messageHello = "Hello! I'm Duke."
            + "\nType \"command\" to check available commands to try out!"
            + "\nWhat can I do for you?\n";

    public static String messageBye = "Bye. Hope to see you again soon!\n";

    public static String messageCommandList = "\nThese are implemented commands that you can use.\n"
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

    public static String getMessageError(Error error){
        switch(error){
        case NO_LIST:
            return "No list exists yet. Begin by adding some tasks!\n";
        case WRONG_ARGUMENTS:
            return "Wrong arguments supplied. Please enter \"command\" to check acceptable arguments.\n";
        case INVALID_COMMAND:
            return "Command not recognised. Please enter \"command\" for command list!\n";
        default:
            return "";
        }
    }

    public static String getMessageTaskMarkAsDone(Task outputTask){
        return "Nice! I've marked this task as done:\n"
                + String.format("  %s %s\n", outputTask.getIsDoneSymbol(), outputTask.getTaskName());
    }
}
