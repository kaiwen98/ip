/**
 * Task class to encapsulate its features and methods.
 */
public class Task {
    protected String taskName;
    protected IsDone isDone;
    protected TaskType taskType;
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

    public Task(String taskName){
        this(taskName, false);
    }

    public Task(String taskName, boolean isDone){
        this.taskName = taskName;
        this.setIsDone(isDone);
    }

    public void setIsDone(boolean isDone){
        this.isDone = (isDone) ? IsDone.DONE:IsDone.NOT_DONE;
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

    @Override
    public String toString(){
        return String.format("%s%s %s %s", this.isDone, this.taskName, this.getTypeMessage());
    }
}
