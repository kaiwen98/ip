/**
 * Class to collate all the tasks added.
 */
public class ListOfTasks {

    private Task[] tasks;
    private int numOfTasks;

    /**
     * Non-parameterized class constructor
     */
    public ListOfTasks(){
        this.tasks = new Task[100];
        this.numOfTasks = 0;
    }

    /**
     * Retrieves task instance by index on the task array.
     * @param index Number on the task array
     * @return Corresponding task instance.
     */
    public Task getTaskByIndex(int index){
        return tasks[index];
    }

    /**
     * Append new tasks into the list.
     * @param newTask Task that is created beforehand via a separate process.
     */
    public void addTask(Task newTask){
        this.tasks[this.numOfTasks] = newTask;
        this.numOfTasks ++;
    }

    /**
     * Add task to list.
     * @param newTaskParam Task name, if the task is to be created by its name, then which it is automatically assumed to be an uncompleted task.
     * @return Error code
     */
    public Error addTask(String newTaskParam){
        this.tasks[this.numOfTasks] = new Task(newTaskParam);
        this.numOfTasks ++;
        return Error.NO_ERROR;
    }

    /**
     * Mark a specific task as done.
     * @param taskNumber index of the task on the task array.
     * @return Error code
     */
    public Error markTaskAsDone(int taskNumber){
        if (taskNumber < 0 || taskNumber > this.numOfTasks-1){
            return Error.WRONG_ARGUMENTS;
        } else{
            tasks[taskNumber].setIsDone(true);
            return Error.NO_ERROR;
        }
    }

    /**
     * Add task to list.
     * @param newTaskParams Parameters specified to initialize an instance of a task.
     * @return Error code
     */
    public Error addTask(String[] newTaskParams){
        if (!(newTaskParams[1].toLowerCase().equals("true") || newTaskParams[1].toLowerCase().equals("false"))){
            return Error.WRONG_ARGUMENTS;
        }
        this.tasks[this.numOfTasks] = new Task(newTaskParams[0], Boolean.parseBoolean(newTaskParams[1]));
        this.numOfTasks ++;
        return Error.NO_ERROR;
    }

    /**
     * Prints the contents of the list, displaying also its number and whether it is completed.
     * @return String representing the contents of the list
     */
    public String showAllTasks(){
        String output = "";
        for (int i = 0; i < this.numOfTasks; i++){
            output += String.format("%d.%s %s\n", i+1, this.tasks[i].getIsDoneSymbol(), this.tasks[i].getTaskName());
        }
        return output;
    }
}
