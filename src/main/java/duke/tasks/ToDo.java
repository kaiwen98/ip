/**
 * Subclass of Task.
 */
package duke.tasks;
import duke.dukehelper.Constants;
import duke.dukehelper.DukeException;

public class ToDo extends Task{
    public ToDo(String taskName, int id){
        super(taskName, id);
        super.taskType = Task.TaskType.TODO;
        this.error = Constants.Error.NO_ERROR;
    }

    @Override
    protected Constants.Error handleParams(String paramType){
        String customErrorMessage = "";
        switch(paramType){
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
        return "";
    }
}
