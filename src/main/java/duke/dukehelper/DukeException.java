/**
 * Handles exceptions with regards to input errors.
 */
package duke.dukehelper;
public class DukeException {
    public static class InvalidDescription extends Exception{
    }
    public static class NoList extends Exception{
    }
    public static class TaskCreateFail extends Exception{
    }
    public static class ListSaveLoadFail extends Exception{
    }
    public static class FileNotFound extends Exception{
    }
    public static class TaskAlreadyDone extends Exception{
    }

    public static void printErrorMessage(Constants.Error error, String customErrorMessage){
        String output = "";
        customErrorMessage = customErrorMessage.equals("")? "" : "\t[NOTE]: "+ customErrorMessage;
        switch(error){
        case NO_LIST:
            output = "List is empty\n";
            break;
        case WRONG_ARGUMENTS:
            output = "Wrong arguments supplied. Please enter \"command\" to check acceptable arguments.\n";
            break;
        case INVALID_COMMAND:
            output = "Command not recognised. Please enter \"command\" for command list!\n";
            break;
        case TASK_NOT_CREATED:
            output = "Due to error in input, the task is not added. Try again.\n";
            break;
        case TASK_COMMAND_FAIL:
            output = "Due to error in input, the task command failed. Try again. \n";
            break;
        case FILE_SAVE_FAIL:
            output = "Due to error in input, the list save failed. Try again. \n";
            break;
        case FILE_LOAD_FAIL:
            output = "Due to error in input, the list load failed. Try again. \n";
            break;
        case FILE_NOT_FOUND:
            output = "The file name supplied does not exist in the directory: \n";
            break;
        case FILE_EXISTS:
            output = "The file name supplied already exists in the directory. Are you sure you want to override it? [Y\\N]\n";
            break;
        case LIST_EXISTS:
            output = "There is a list currently being constructed. Would you like to save it first? [Y\\N]\n";
            break;
        case NO_ERROR:
            output = "Process completed successfully!\n";
            break;
        default:
            // Fall through
            break;
        }
        System.out.print(output + customErrorMessage);
    }

    public static void printErrorMessage(Constants.Error error){
        printErrorMessage(error, "");
    }
}
