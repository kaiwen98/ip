/**
 * A subclass of Task that takes in date and time when the event happens
 */
package duke.tasks;
import duke.dukehelper.*;
import java.util.Hashtable;
public class Event extends Task{
    private String date;
    private String time;

    // Constructor
    public Event(String taskName, Hashtable paramMap){
        super(taskName, false, paramMap);
        super.taskType = TaskType.EVENT;
        this.error = (paramMap == null) ? Constants.Error.WRONG_ARGUMENTS : Constants.Error.NO_ERROR;
        if (this.error == Constants.Error.NO_ERROR){
            for (Object paramType: this.getParamTypes()){
                if (parsePayload((String) paramType) != Constants.Error.NO_ERROR) {
                    this.error = Constants.Error.WRONG_ARGUMENTS;
                }
                if (this.error ==  Constants.Error.WRONG_ARGUMENTS) break;
            }
        }
    }

    // Checks for param type and corresponding param, and returns error if
    // given param is not recognised.
    private Constants.Error parsePayload(String paramType){
        String[] token = null;
        switch(paramType){
        case "/at":
            try {
                token = ((String) this.paramMap.get(paramType)).split(" ");
                this.date = token[0];
                this.time = token[1];
            } catch (java.lang.ArrayIndexOutOfBoundsException exception){
                String customErrorMessage = String.format("You seem to have missed either the date or the time, " +
                        "or you have forgotten to separate with spacing. Check your input. \n", paramType);
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                return Constants.Error.WRONG_ARGUMENTS;
            }
            break;
        default:
            String customErrorMessage = String.format("The parameter type %s is not implemented. You may want to check your spelling.\n", paramType);
            DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
            return Constants.Error.WRONG_ARGUMENTS;
        }
        return Constants.Error.NO_ERROR;
    }

    @Override
    public String getTypeMessage(){
        String output = "";
        for(Object paramType: this.getParamTypes()) {
            switch ((String) paramType) {
            case "/at":
                output = String.format("(at: %s %s)", this.date, this.time);
                break;
            default:
                // Due to exception handling at parsePayload above, there is no need to catch errors here
                // Fall through
                break;
            }
        }
        return output;
    }

}
