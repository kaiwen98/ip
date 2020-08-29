public class Deadline extends Task{
    private String deadline;

    public Deadline(String taskName, String deadline){
        super(taskName);
        super.taskType = Task.TaskType.TODO;
        this.deadline = deadline;
    }

    @Override
    public String getTypeMessage(){
        return String.format("(by: %s)", this.deadline);
    }
}
