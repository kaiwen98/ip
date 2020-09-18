/**
 * Contains constants used in command, error, etc.
 */
package duke.dukehelper;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    public static final String DEFAULT_SAVE_PATH = getWorkingDirectory() + "\\savestates\\";
    public static final int MAX_ARRAY_LEN = 10;
    public static final int MAX_PARTITION_LINE_LEN = 100;
    public static final int MAX_LIST_LEN = 10;
    public static final String DONE_SYMBOL = "O";
    public static final String NOT_DONE_SYMBOL = "X";

    public enum Error {
        WRONG_ARGUMENTS,
        INVALID_COMMAND,
        NO_LIST,
        NO_ERROR,
        TASK_NOT_CREATED,
        TASK_COMMAND_FAIL,
        FILE_LOAD_FAIL,
        FILE_SAVE_FAIL,
        FILE_NOT_FOUND,
        FILE_EXISTS,
        LIST_EXISTS,
        OTHER_ERROR
    }

    public enum Command {
        HELLO,
        BYE,
        ECHO,
        PROMPT_INPUT,
        INSERT_TASK_DEADLINE,
        INSERT_TASK_TODO,
        INSERT_TASK_EVENT,
        SHOW_LIST,
        SHOW_SAVE_STATES,
        SHOW_COMMANDS,
        MARK_TASK_DONE,
        REMOVE_TASK,
        SAVE_FILE,
        LOAD_FILE,
        REFRESH_FILE,
        FIND
    }

    public static String getWorkingDirectory(){
        Path rootPath = Paths.get(System.getProperty("user.dir"));
        System.out.println(rootPath.getFileName());
        return rootPath.toString();
    }

}
