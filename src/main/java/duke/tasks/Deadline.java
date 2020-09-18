/**
 * Sub-class of Task that takes in a date as deadline of task
 */
package duke.tasks;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import duke.dukehelper.*;
import duke.taskhelper.TaskException;

public class Deadline extends Task{
    private DateTimeManager deadlineDateTime;

    public Deadline(String taskName, int id, HashMap paramMap){
        super(taskName, id, false, paramMap);
        this.taskType = Task.TaskType.DEADLINE;
    }

    @Override
    protected Constants.Error handleParams(String paramType) {
        String token = "";
        String customErrorMessage = "";
        switch(paramType){
        case "/by":
            try {
                if (((String) this.paramMap.get(paramType)).length() == 0){
                    throw new TaskException.IllegalParam();
                }
                token = Parser.parseRawDateTime(this.getParam(paramType)).replace(",", "");
                this.deadlineDateTime = new DateTimeManager(token);
            } catch (TaskException.IllegalParam exception){
                customErrorMessage = String.format("Param %s is expecting 2 string arguments: "
                        + "Deadline date and time. Check your input.\n", paramType);
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
            boolean isDone;
            if (((String)this.paramMap.get(paramType)).equals(Constants.DONE_SYMBOL)){
                isDone = true;
            } else if (((String)this.paramMap.get(paramType)).equals(Constants.NOT_DONE_SYMBOL)){
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
        return String.format("(by: %s)", deadlineDateTime.getDateFormatted(args));
    }
}



