/**
 * Task class to encapsulate its features and methods.
 */
public class Task {
    private String taskName;
    private boolean isDone;

    public Task(String taskName){
        this.taskName = taskName;
        this.isDone = false;
    }

    public Task(String taskName, boolean isDone){
        this.taskName = taskName;
        this.isDone = isDone;
    }

    public void setIsDone(boolean isDone){
        this.isDone = isDone;
    }

    public String getTaskName(){
        return this.taskName;
    }

    public boolean getIsDone(){
        return this.isDone;
    }

    public String getIsDoneSymbol(){
        return (this.isDone)? "[✓]":"[✗]";
    }
}
