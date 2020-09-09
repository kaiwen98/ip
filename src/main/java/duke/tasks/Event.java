/**
 * A subclass of Task that takes in date and time when the event happens
 */
package duke.tasks;
import duke.dukehelper.*;
import duke.taskhelper.TaskException;
import java.util.Hashtable;

public class Event extends Task{
    private String date;
    private String time;
    private String test;

    // Constructor
    public Event(String taskName, Hashtable paramMap){
        super(taskName, false, paramMap);
        this.taskType = TaskType.EVENT;
    }

    // Checks for param type and corresponding param, and returns error if
    // given param is not recognised.
    @Override
    protected Constants.Error handleParams(String paramType) {
        String[] token = null;
        switch(paramType){
        case "/at":
            try {
                if (((String) this.paramMap.get(paramType)).length() == 0){
                    throw new TaskException.IllegalParam();
                }
                token = ((String) this.paramMap.get(paramType)).split(" ");
                this.date = token[0];
                this.time = token[1];
                super.taskMessage[0] = String.format("at: %s %s", this.date, this.time);
            } catch (ArrayIndexOutOfBoundsException | TaskException.IllegalParam exception){
                String customErrorMessage = String.format("Param %s is expecting 2 string arguments: "
                        + "Date and Time. Check your input.\n", paramType);
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                return Constants.Error.WRONG_ARGUMENTS;
            }
            break;

        case "/sorry":
            super.taskMessage[1] = "< Sorry, the code is very extra. I'm just trying to learn java. >";
            break;

        default:
            String customErrorMessage = String.format("The parameter type %s is not implemented.\n", paramType);
            DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
            return Constants.Error.WRONG_ARGUMENTS;
        }
        return Constants.Error.NO_ERROR;
    }
}
