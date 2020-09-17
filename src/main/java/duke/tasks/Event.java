/**
 * A subclass of Task that takes in date and time when the event happens
 */
package duke.tasks;
import duke.dukehelper.Constants;
import duke.dukehelper.DateTimeManager;
import duke.dukehelper.DukeException;
import duke.dukehelper.Parser;
import duke.taskhelper.TaskException;

import java.time.format.DateTimeParseException;
import java.util.HashMap;

public class Event extends Task{
    private DateTimeManager startDateTime;
    private DateTimeManager endDateTime;
    private String test;

    // Constructor
    public Event(String taskName, int id, HashMap paramMap){
        super(taskName, id, false, paramMap);
        this.taskType = TaskType.EVENT;
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
                token = Parser.parseRawDateTime(param).split(",");
                this.startDateTime = new DateTimeManager(token[0]);
                this.endDateTime = new DateTimeManager(token[1]);
            } catch (ArrayIndexOutOfBoundsException | TaskException.IllegalParam exception){
                customErrorMessage = String.format("Param %s is expecting at least 3 string arguments: "
                        + "Date, start time and end time. Check your input.\n", paramType);
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                return Constants.Error.WRONG_ARGUMENTS;
            } catch (DateTimeParseException exception) {
                customErrorMessage = "Your input param for date and time cannot be parsed!\n";
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

    @Override
    public String getTypeMessage(String[] args){
        String output = "";
        return String.format("(at: %s to %s)", startDateTime.getDateFormatted(args), endDateTime.getDateFormatted(args));
    }
}
