/**
 * Subclass of Task.
 */
public class ToDo extends Task{
    public ToDo(String taskName){
        super(taskName);
        super.taskType = Task.TaskType.TODO;
    }
}
