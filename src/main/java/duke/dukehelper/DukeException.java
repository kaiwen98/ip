/**
 * Handles exceptions with regards to input errors.
 */
package duke.dukehelper;
public class DukeException {
    static class InvalidDescription extends Exception{
    }
    static class NoList extends Exception{
    }

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
        case TASK_NOT_CREATED:
            output = "Due to error input, the task is not added. Try again.\n";
            break;
        case NO_ERROR:
            output = "";
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
