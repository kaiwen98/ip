/**
 * Class handling all possible console outputs.
 */
package duke.dukehelper;
import duke.taskhelper.ListTasks;
import duke.tasks.Task;

public class UiManager {
    public static final String MESSAGE_LOGO = "Hello from\n"
            + "____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n\n\n"
            + "                                    ROFL:ROFL:ROFL:ROFL:ROFL:ROFL\n"
            + "................................................___|___.........\n"
            + ".........................................L..../.......[`\\........\n"
            + "........................................LOL===........[__|.......\n"
            + ".........................................L....\\..........|.......\n"
            + "...............................................\\_________/.......\n"
            + ".................................................|...|...........\n"
            + "................................................————————/........\n\n\n";

    public static final String MESSAGE_HELLO = "Hello! I'm Duke."
            + "\nType \"command\" to check available commands to try out!"
            + "\nWhat can I do for you?\n";

    public static final String MESSAGE_BYE = "Bye. Hope to see you again soon!\n";

    public static final String MESSAGE_COMMAND_LIST = "\nThese are implemented commands that you can use.\n"
            + "\n1. bye\n"
            + "\t< Exit the program >\n"

            + "\n2. list\n"
            + "\t< Show full list of appended tasks >\n"

            + "\n3. commands\n"
            + "\t< Show full list of commands >\n"

            + "\n4. done <integer>\n"
            + "\t< Mark a task by number <integer> as done. >\n"
            + "\t@ <integer>:\t Task number on the list. Out-of-bounds and negative inputs are not allowed.\n"

            + "\n5. todo <string>\n"
            + "\t< Will be interpreted as input tasks. Input task will then be added to the list. >\n "
            + "\t<!> Tasks added this way are assumed to not be done and recorded accordingly.\n"

            + "\n6. event <string> <parameter type> <parameter 1> <parameter 2>\n"
            + "\t< Add a task which is happening in the future with specific date and time >\n "
            + "\t@ <string>:\t Task name.\n"
            + "\t@ <parameter type>:\t /at\n"
            + "\t\t# <parameter 1>:\t Date\n"
            + "\t\t# <parameter 2>:\t Time from start to end, no spaces\n"

            + "\n7. deadline <string> <parameter type> <parameter 1>\n"
            + "\t< Add a task with a specific deadline> \n "
            + "\t@ <string>:\t Task name.\n"
            + "\t@ <parameter type>:\t /by\n"
            + "\t\t# <parameter 1>:\t Date w/o time\n"

            + "\n8. remove <integer>\n"
            + "\t< Remove task by number <integer> from list. >\n"
            + "   @ <integer>:\t Task number on the list. Out-of-bounds and negative inputs are not allowed.\n"

            + "\n9. save <parameter type 1> <parameter 1> <parameter type 2> <parameter 1>\n"
            + "\t< Saves current task to local disk. A default folder is: >\n"
            + "\t[" + Constants.DEFAULT_SAVE_PATH + "]\n"
            + "\t@ <parameter type 1>:\t /name\n"
            + "\t\t# <parameter 1>:\t File name, with or without extension. Only .txt files accepted.\n"
            + "\t@ <parameter type 2>:\t /dir\n"
            + "\t\t# <parameter 1>:\t Specify a custom save folder path.\n"

            + "\n10. load <parameter type 1> <parameter 1> <parameter type 2> <parameter 1>\n"
            + "\t< Loads saved task from local disk. A default folder is: >\n"
            + "\t[" + Constants.DEFAULT_SAVE_PATH + "]\n"
            + "\t@ <parameter type 1>:\t /name\n"
            + "\t\t# <parameter 1>:\t File name, with or without extension. Only .txt files accepted.\n"
            + "\t@ <parameter type 2>:\t /dir\n"
            + "\t\t# <parameter 1>:\t Specify a custom save folder path.\n"

            + "\n11. saves\n"
            + "\t< Show full list of save states in default directory >\n";



    public static String getMessageTaskAdded(Task task){
        return String.format("Got it! I've added this task:\n  %s\n", task);
    }
    public static String getMessageReportNumTasks(ListTasks list){
        char isS = list.getNumTasks() > 1? 's':0;
        return String.format("Now, you have %d task%c in the list.\n", list.getNumTasks(),isS);
    }
    public static String getMessageTaskMarkAsDone(Task outputTask){
        return String.format("Nice! I've marked this task as done:\n  %s\n", outputTask);
    }
    public static String getMessageTaskRemove(Task outputTask){
        return String.format("Noted! I've removed this task:\n  %s\n", outputTask);
    }
    public static String getMessageListSaved(ListTasks list){
        return String.format("Noted! I've saved the list to the following directory:\n  %s\n\n", Constants.DEFAULT_SAVE_PATH)
                + list.showAllTasks();
    }
    public static String getMessageListLoaded(ListTasks list){
        return String.format("Noted! I've loaded the list from the following directory:\n  %s\n\n", Constants.DEFAULT_SAVE_PATH)
                + list.showAllTasks();
    }

    public static String returnLineWithSymbol(int width, String symbol){
        return new String(new char[width]).replace("\0", symbol);
    }
    public static void printLineWithSymbol(int width, String symbol){
        System.out.println(returnLineWithSymbol(width, symbol));
    }

    public static void drawPartition(){
        printLineWithSymbol(Constants.MAX_PARTITION_LINE_LEN, "_");
    }
}
