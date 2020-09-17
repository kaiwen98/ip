/**
 * A subclass of Task that takes in date and time when the event happens
 */
package duke.tasks;
import duke.dukehelper.Constants;
import duke.dukehelper.DateTimeManager;
import duke.dukehelper.DukeException;
import duke.taskhelper.TaskException;
import java.util.HashMap;

public class Event extends Task{
    private DateTimeManager startDateTime;
    private DateTimeManager endDateTime;
    private String test;

    // Constructor
    public Event(String taskName, HashMap paramMap){
        super(taskName, false, paramMap);
        this.taskType = TaskType.EVENT;
    }

    private String dateTimeFormat(String whichDate){
        String startDTString =  startDateTime.getDateFormatted(whichDate);
        String endDTString = endDateTime.getDateFormatted(whichDate);
        String[] output = new String[2];
        switch(whichDate){
        case "verbose":
            output[0] = startDateTime.getDateFormatted("date") + " " + startDateTime.getDateFormatted("time");
            if (!startDateTime.getDateFormatted("date").equals(endDateTime.getDateFormatted("date"))){
                output[1] = endDateTime.getDateFormatted("date") + " " + endDateTime.getDateFormatted("time");
            } else {
                output[1] = endDateTime.getDateFormatted("time");
            }
            return String.join(" to ", output).trim();

        default:
            if(startDTString.equals(endDTString)){
                return startDTString;
            } else {
                return startDTString + " to " + endDTString;
            }
        }
    }

    // Checks for param type and corresponding param, and returns error if
    // given param is not recognised.
    @Override
    protected Constants.Error handleParams(String paramType) {
        String[] token = null;
        String customErrorMessage = "";
        String param = "";
        switch(paramType){
        case "/at":
            param = this.getParam(paramType);
            try {
                if (((String) this.paramMap.get(paramType)).length() == 0){
                    throw new TaskException.IllegalParam();
                }
                token = param.split(" ");
                this.startDateTime = new DateTimeManager(token[0]);
                this.endDateTime = new DateTimeManager(token[1]);
                this.taskMessage[0] = "at: " + this.dateTimeFormat(param);
            } catch (ArrayIndexOutOfBoundsException | TaskException.IllegalParam exception){
                customErrorMessage = String.format("Param %s is expecting 2 string arguments: "
                        + "Date and Time. Check your input.\n", paramType);
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                return Constants.Error.WRONG_ARGUMENTS;
            }
            break;

        case "/sorry":
            this.taskMessage[1] = "< Sorry, the code is very extra. I'm just trying to learn java. >";
            break;

        case "/done":
            param = this.getParam(paramType);
            boolean isDone;
            if (param.equals(Constants.DONE_SYMBOL)){
                isDone = true;
            } else if (param.equals(Constants.NOT_DONE_SYMBOL)){
                isDone = false;
            } else {
                customErrorMessage = "Done symbol is not recognised from source file!\n";
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                return Constants.Error.WRONG_ARGUMENTS;
            }
            this.setIsDone(isDone);
            break;

        default:
            customErrorMessage = String.format("The parameter type %s is not implemented.\n", paramType);
            DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
            return Constants.Error.WRONG_ARGUMENTS;
        }
        return Constants.Error.NO_ERROR;
    }
}
