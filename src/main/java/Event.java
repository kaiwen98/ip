public class Event extends Task{
    private String date;
    private String time;

    public Event(String taskName, String date, String time){
        super(taskName);
        super.taskType = Task.TaskType.TODO;
        this.time = time;
        this.date = date;
    }

    @Override
    public String getTypeMessage(){
        return String.format("(at: %s %s)", this.date, this.time);
    }
}
