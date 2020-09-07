/**
 * Subclass of Task.
 */
package duke.tasks;
import duke.dukehelper.Constants;

public class ToDo extends Task{
    public ToDo(String taskName){
        super(taskName);
        super.taskType = Task.TaskType.TODO;
        this.error = Constants.Error.NO_ERROR;
    }

    @Override
    protected Constants.Error handleParams(String paramType){
        return Constants.Error.NO_ERROR;
    }
}
