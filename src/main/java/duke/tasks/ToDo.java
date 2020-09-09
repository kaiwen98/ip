/**
 * Subclass of Task.
 */
package duke.tasks;
import duke.dukehelper.Constants;
import duke.dukehelper.DukeException;

public class ToDo extends Task{
    public ToDo(String taskName){
        super(taskName);
        super.taskType = Task.TaskType.TODO;
        this.error = Constants.Error.NO_ERROR;
    }

    @Override
    protected Constants.Error handleParams(String paramType){
        String customErrorMessage = "";
        switch(paramType){
        case "/done":
            boolean isDone;
            if (((String)this.paramMap.get(paramType)).equals("✓")){
                isDone = true;
            } else if (((String)this.paramMap.get(paramType)).equals("✓")){
                isDone = false;
            } else {
                customErrorMessage = "Done symbol is not recognised from source file!\n";
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                return Constants.Error.WRONG_ARGUMENTS;
            }
            this.setIsDone(isDone);
            break;
        default:
            // Pass through
            break;
        }
        return Constants.Error.NO_ERROR;
    }
}
