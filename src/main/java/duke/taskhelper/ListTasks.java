package duke.taskhelper;
import duke.tasks.*;
import duke.dukehelper.*;
import java.util.ArrayList;
/**
 * Class to collate all the tasks added.
 */
public class ListTasks {

    private ArrayList<Task> tasks;
    private int numTasks;

    /**
     * Non-parameterized class constructor
     */
    public ListTasks(){
        this.tasks = new ArrayList<Task>();
        this.numTasks = 0;
    }

    /**
     * Retrieves task instance by index on the task array.
     * @param index Number on the task array
     * @return Corresponding task instance.
     */
    public Task getTaskByIndex(int index){
        return tasks.get(index);
    }

    public int getNumTasks(){
        return this.numTasks;
    }
    /**
     * Append new tasks into the list.
     * @param newTask Task that is created beforehand via a separate process.
     */
    public Constants.Error addTask(Task newTask){
        if (newTask.error != Constants.Error.NO_ERROR){
            return newTask.error;
        }
        this.tasks.add(newTask);
        this.numTasks++;
        return Constants.Error.NO_ERROR;
    }

    /**
     * Add task to list.
     * @param newTaskParam Task name, if the task is to be created by its name, then which it is automatically assumed to be an uncompleted task.
     * @return Error code
     */
    public Constants.Error addTask(String newTaskParam){
        return addTask(new Task(newTaskParam));
    }

    /**
     * Mark a specific task as done.
     * @param taskNumber index of the task on the task array.
     * @return Error code
     */
    public Constants.Error markTaskAsDone(int taskNumber){
        if (taskNumber < 0 || taskNumber > this.numTasks -1){
            return Constants.Error.WRONG_ARGUMENTS;
        } else{
            tasks.get(taskNumber).setIsDone(true);
            return Constants.Error.NO_ERROR;
        }
    }

    /**
     * Prints the contents of the list, displaying also its number and whether it is completed.
     * @return String representing the contents of the list
     */
    public String showAllTasks(){
        String output = "";
        for (int i = 0; i < this.numTasks; i++){
            output += String.format("%d.%s\n", i+1, this.tasks.get(i));
        }
        return output;
    }
}
