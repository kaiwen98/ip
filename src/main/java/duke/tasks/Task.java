/**
 * Task class to encapsulate its features and methods.
 * Acts as super class to ToDo, Event and Deadline classes.
 */
package duke.tasks;

import duke.dukehelper.Command;
import duke.dukehelper.Constants;

import java.util.HashMap;
import java.util.Arrays;

public abstract class Task extends Command {
    protected String taskName;
    protected IsDone isDone;
    protected TaskType taskType;
    protected String[] taskMessage = {"none"};
    protected int id;

    enum TaskType {
        TODO("T"),
        DEADLINE("D"),
        EVENT("E");

        private String literal;

        TaskType(String literal) {
            this.literal = literal;
        }

        @Override
        public String toString() {
            return this.literal;
        }
    }

    enum IsDone {
        DONE(Constants.DONE_SYMBOL, true),
        NOT_DONE(Constants.NOT_DONE_SYMBOL, false);

        private String literal;
        private boolean bool;

        IsDone(String literal, boolean bool) {
            this.literal = literal;
            this.bool = bool;
        }

        @Override
        public String toString() {
            return this.literal;
        }

        public boolean toBoolean() {
            return bool;
        }
    }

    public Task(String taskName, int id, boolean isDone, HashMap paramMap) {
        this.taskName = taskName;
        this.setIsDone(isDone);
        this.taskMessage = new String[Constants.MAX_ARRAY_LEN];
        this.id = id;
        Arrays.fill(this.taskMessage, "");
        if (paramMap != null) {
            this.setParamMap(paramMap);
        }
    }

    public Task(String taskName, int id) {
        this(taskName, id, false, null);
    }

    public String getTaskName() {
        return this.taskName;
    }

    public String getTaskType() {
        return String.format("%s", this.taskType);
    }

    public int getId() {
        return this.id;
    }

    public String getIsDone() {
        return String.format("%s", this.isDone);
    }

    public boolean getIsDoneBool() {
        return this.isDone.toBoolean();
    }

    public abstract String getTypeMessage(String[] args) ;

    public String getTypeMessage(String format){
        String[] arg = {format};
        return getTypeMessage(arg);
    }

    public String getTypeMessage(){ ;
        return getTypeMessage("");
    }

    public void setIsDone(boolean isDone) {
        this.isDone = (isDone) ? IsDone.DONE : IsDone.NOT_DONE;
    }

    /**
     * Get output message that is associated with the task, particularly datetime outputs
     * @param outputFormat
     * @return String
     */
    public String getOutputLine(String[] outputFormat){
        return String.format("%s %s\n", this, this.getTypeMessage(outputFormat));
    }

    public String getOutputLine(){
        return String.format("%s %s\n", this, this.getTypeMessage());
    }

    @Override
    public String toString(){
        String output = "";
        output = String.format("[%s][%s] %s", this.taskType, this.isDone, this.taskName);
        return output;
    }
}
