/**
 * Task class to encapsulate its features and methods.
 */
import java.util.Hashtable;
import java.util.Set;

public class Task {
    protected String taskName;
    protected IsDone isDone;
    protected TaskType taskType;
    protected Hashtable paramMap;
    protected Constants.Error error;

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

    public Task(String taskName, boolean isDone, Hashtable paramMap){
        this.taskName = taskName;
        this.setIsDone(isDone);
        this.error = Constants.Error.NO_ERROR;
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
    public String getTaskName(){
        return this.taskName;
    }
    public boolean getIsDone(){
        return this.isDone.toBoolean();
    }
    public String getTypeMessage(){
        return "";
    }
    public void setIsDone(boolean isDone){
        this.isDone = (isDone) ? IsDone.DONE:IsDone.NOT_DONE;
    }
    public void setParamMap(Hashtable paramMap){
        this.paramMap = new Hashtable();
        this.paramMap = (Hashtable) paramMap.clone();
    }

    @Override
    public String toString(){
        return String.format("%s%s %s %s", this.taskType, this.isDone, this.taskName, this.getTypeMessage());
    }
}
