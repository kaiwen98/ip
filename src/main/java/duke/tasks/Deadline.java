/**
 * Sub-class of Task that takes in a date as deadline of task
 */
package duke.tasks;
import java.util.Hashtable;
import duke.dukehelper.*;
import duke.taskhelper.TaskException;

public class Deadline extends Task{
    private String deadline;

    public Deadline(String taskName, Hashtable paramMap){
        super(taskName, false, paramMap);
        this.taskType = Task.TaskType.DEADLINE;
        this.processParamMap();
    }

    // Checks for param type and corresponding param, and returns error if
    // given param is not recognised.
    @Override
    protected Constants.Error handleParams(String paramType) {
        String token = "";
        switch(paramType){
        case "/by":
            try {
                if (((String) this.paramMap.get(paramType)).length() == 0){
                    throw new TaskException.IllegalParam();
                }
                token = (String) this.paramMap.get(paramType);
                this.deadline = token;
                this.taskMessage[0] = String.format("(by: %s)", this.deadline);
            } catch (TaskException.IllegalParam exception){
                String customErrorMessage = String.format("Param %s is expecting 1 string argument: "
                        + "Deadline date. Check your input.\n", paramType);
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



