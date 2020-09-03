/**
 * Contains constants used in command, error, etc.
 */
package duke.dukehelper;
public class Constants {
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
        SHOW_COMMANDS,
        MARK_TASK_DONE,
        REMOVE_TASK,
        SAVE
    }
}