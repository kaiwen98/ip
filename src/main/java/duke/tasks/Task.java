/**
 * Task class to encapsulate its features and methods.
 * Acts as super class to ToDo, Event and Deadline classes.
 */
package duke.tasks;
import java.util.Hashtable;
import java.util.Set;
import java.util.Arrays;
import duke.dukehelper.*;

public class Task {
    protected String taskName;
    protected IsDone isDone;
    protected TaskType taskType;
    protected Hashtable paramMap;
    protected String[] taskMessage;
    public Constants.Error error;

    enum TaskType {
        TODO("[T]"),
        DEADLINE("[D]"),
        EVENT("[E]");

        private String literal;

        TaskType(String literal){
            this.literal = literal;
        }

        @Override
        public String toString(){
            return this.literal;
        }
    }

    enum IsDone {
        DONE("[✓]", true),
        NOT_DONE("[✗]", false);

        private String literal;
        private boolean bool;

        IsDone(String literal, boolean bool){
            this.literal = literal;
            this.bool = bool;
        }

        @Override
        public String toString(){
            return this.literal;
        }

        public boolean toBoolean(){
            return bool;
        }
    }

    // Constructor
    public Task(String taskName, boolean isDone, Hashtable paramMap){
        this.taskName = taskName;
        this.setIsDone(isDone);
        this.error = Constants.Error.WRONG_ARGUMENTS;
        this.taskMessage = new String[Constants.MAX_ARRAY_LEN];
        Arrays.fill(this.taskMessage, "");
        if (paramMap != null){
            this.setParamMap(paramMap);
        }
    }
    public Task(String taskName){
        this(taskName, false, null);
    }

    public Set getParamTypes(){
        return paramMap.keySet();
    }
    public String getTypeMessage(){
        return String.join(" ", this.taskMessage);
    }
    public void setIsDone(boolean isDone){
        this.isDone = (isDone) ? IsDone.DONE : IsDone.NOT_DONE;
    }
    public void setParamMap(Hashtable paramMap){
        this.paramMap = new Hashtable();
        this.paramMap = (Hashtable) paramMap.clone();
    }
    protected void processParamMap() {
        if (!this.paramMap.isEmpty()) {
            for (Object paramType : this.getParamTypes()) {
                // If there is at least one valid param type and param to process, no need to dismiss the entire task.
                if (this.handleParams((String) paramType) == Constants.Error.NO_ERROR) {
                    this.error = Constants.Error.NO_ERROR;
                }
            }
        }
    }

    // To be overridden by subclasses based on the param types they can receive
    protected Constants.Error handleParams(String ParamType) {
        return Constants.Error.NO_ERROR;
    }

    @Override
    public String toString(){
        return String.format("%s%s %s %s", this.taskType, this.isDone, this.taskName, this.getTypeMessage());
    }
}
