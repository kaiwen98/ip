/**
 * Task class to encapsulate its features and methods.
 * Acts as super class to ToDo, Event and Deadline classes.
 */
package duke.tasks;

import duke.dukehelper.Command;
import duke.dukehelper.Constants;

import java.util.Hashtable;
import java.util.Arrays;

public abstract class Task extends Command {
    protected String taskName;
    protected IsDone isDone;
    protected TaskType taskType;
    protected String[] taskMessage;

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

    // Constructor
    public Task(String taskName, boolean isDone, Hashtable paramMap) {
        this.taskName = taskName;
        this.setIsDone(isDone);
        this.taskMessage = new String[Constants.MAX_ARRAY_LEN];
        Arrays.fill(this.taskMessage, "");
        if (paramMap != null) {
            this.setParamMap(paramMap);
        }
    }

    public Task(String taskName) {
        this(taskName, false, null);
    }

    public String getTaskName() {
        return this.taskName;
    }

    public String getTaskType() {
        return String.format("%s", this.taskType);
    }

    public String getIsDone() {
        return String.format("%s", this.isDone);
    }

    public boolean getIsDoneBool() {
        return this.isDone.toBoolean();
    }



    public String getTypeMessage() {
        return String.join(" ", this.taskMessage);
    }

    public void setIsDone(boolean isDone) {
        this.isDone = (isDone) ? IsDone.DONE : IsDone.NOT_DONE;
    }

    @Override
    public String toString(){
        String output = "";
        output = String.format("[%s][%s] %s", this.taskType, this.isDone, this.taskName);
        if ((this.getTypeMessage().strip()).length() != 0){
            output += String.format(" (%s)", this.getTypeMessage().strip());
        }
        return output;
    }
}
