/**
 * Contains constants used in command, error, etc.
 */
package duke.dukehelper;

public class Constants {
    public static final String DEFAULT_SAVE_PATH = System.getProperty("user.dir") + "\\savestates\\";
    public static final int MAX_ARRAY_LEN = 10;
    public static final int MAX_PARTITION_LINE_LEN = 60;
    public static final int MAX_LIST_LEN = 10;

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
        OTHER_ERROR,
    }

    public enum Command {
        HELLO,
        BYE,
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
        LOAD_FILE
    }
}
