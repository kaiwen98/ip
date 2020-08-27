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
