/**
 * Class to collate all the tasks added.
 */

package duke.taskhelper;
import duke.dukehelper.Command;
import duke.dukehelper.Constants;
import duke.dukehelper.DukeException;
import duke.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ListTasks extends Command {

    private ArrayList<Task> tasks = new ArrayList<>();
    private int numTasks;
    private int maxTaskNameLength = 0;
    private Task deletedTask = null;
    private String[] outputFormat = { "datetime" };

    /**
     * Non-parameterized class constructor
     */
    public ListTasks(){
        this.setAcceptEmptyParams(true);
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

    public int getMaxTaskNameLength(){
        return this.maxTaskNameLength;
    }

    public Task getDeletedTask(){
        return this.deletedTask;
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
        if (newTask.getTaskName().length() > this.maxTaskNameLength){
            this.maxTaskNameLength = newTask.getTaskName().length();
        }
        return Constants.Error.NO_ERROR;
    }

    /**
     * Mark a specific task as done.
     * @param taskIndex index of the task on the task array.
     * @return Error code
     */

    public Constants.Error markTaskAsDone(int taskIndex){
        String customErrorMessage = "";
        try {
            if (this.tasks.get(taskIndex).getIsDoneBool()) {
                throw new DukeException.TaskAlreadyDone();
            }
            this.tasks.get(taskIndex).setIsDone(true);
        } catch (DukeException.TaskAlreadyDone exception) {
            customErrorMessage = "Task is already done!\n";
            DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
            return Constants.Error.WRONG_ARGUMENTS;
        } catch (IndexOutOfBoundsException exception) {
            if (this.getNumTasks() > 1) {
                customErrorMessage = "Your list number ranges from 1 to";
                customErrorMessage += String.format(" %d. Please check your input list number.\n", this.getNumTasks());
            } else if (this.getNumTasks() == 1) {
                customErrorMessage = "There is only 1 task in your list.\n";
            } else {
                customErrorMessage = "Somehow, the list has negative number of tasks.\n";
            }
            DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
            return Constants.Error.WRONG_ARGUMENTS;
        } catch (NumberFormatException exception) {
            customErrorMessage = "Non-numeric inputs are not acceptable.\n";
            DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
            return Constants.Error.WRONG_ARGUMENTS;
        }
        return Constants.Error.NO_ERROR;
    }

    /**
     * Removes task from list
     * @param taskIndex index of task
     * @return error where applicable
     */
    public Constants.Error removeTask(int taskIndex){
        String customErrorMessage = "";
        try{
            this.deletedTask = this.tasks.get(taskIndex);
            this.tasks.remove(taskIndex);
            this.numTasks--;
        } catch (IndexOutOfBoundsException exception) {
            if (this.getNumTasks() > 1) {
                customErrorMessage = "Your list number ranges from 1 to";
                customErrorMessage += String.format(" %d. Please check your input list number.\n", this.getNumTasks());
            } else if (this.getNumTasks() == 1) {
                customErrorMessage = "There is only 1 task in your list.\n";
            } else {
                customErrorMessage = "Somehow, the list has negative number of tasks.\n";
            }
            DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
            return Constants.Error.WRONG_ARGUMENTS;
        } catch (NumberFormatException exception) {
            customErrorMessage = "Non-numeric inputs are not acceptable.\n";
            DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
            return Constants.Error.WRONG_ARGUMENTS;
        }
        return Constants.Error.NO_ERROR;
    }

    public void removeAllTasks(){
        while (this.getNumTasks() != 0) {
            this.removeTask(0);
        }
    }

    /**
     * Prints the contents of the list, displaying also its number and whether it is completed.
     * @return String representing the contents of the list
     */
    public String showAllTasks(){
        String output = "";
        String index = "";
        for (int i = 0; i < this.numTasks; i++){
            index = Integer.toString(i+1);
            output += String.format("%s. %s", index, this.tasks.get(i).getOutputLine(this.outputFormat));
        }
        return output;
    }

    /**
     * Stream implementation of keyword finding algorithm
     * @param input
     * @return List of tasks which have the matching keyword
     */
    public List<Task> find(String input){
        List<Task> output = new ArrayList<Task>();
        List<Integer> matchIds = this.tasks.stream()
                // If there is a one-to-one word existing in the description
                .filter(t -> Arrays.stream(t.getTaskName().split(" ")).anyMatch(input::equals))
                .sorted(Comparator.comparing(Task::getId))
                .map(Task::getId)
                .collect(toList());
        for (int id: matchIds) {
            output.add(this.getTaskByIndex(id));
        }
        return output;
    }

    @Override
    protected Constants.Error handleParams(String paramType){
        switch(paramType){
        case "/format":
            // Stores the input as an array of string parameters that determines the datetime format in list.
            this.outputFormat = this.getParam(paramType).split(" ");
            break;
        default:
            // Flow through
            break;
        }
        return Constants.Error.NO_ERROR;
    }
}
