public class DukeException {
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
}
