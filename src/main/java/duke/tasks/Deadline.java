/**
 * Sub-class of Task that takes in a date as deadline of task
 */
package duke.tasks;
import java.util.Hashtable;
import duke.dukehelper.*;
public class Deadline extends Task{
    private String deadline;

    public Deadline(String taskName, Hashtable paramMap){
        super(taskName, false, paramMap);
        super.taskType = Task.TaskType.DEADLINE;
        // Terminate if there are no param inputs
        this.error = (paramMap == null) ? Constants.Error.WRONG_ARGUMENTS : Constants.Error.NO_ERROR;
        // Terminate if there are invalid params
        if (this.error == Constants.Error.NO_ERROR){
            for (Object paramType: this.getParamTypes()){
                if (parsePayload((String) paramType) != Constants.Error.NO_ERROR) {
                    this.error = Constants.Error.WRONG_ARGUMENTS;
                }
                if (this.error ==  Constants.Error.WRONG_ARGUMENTS) break;
            }
        }
    }

    private Constants.Error parsePayload(String paramType){
        String[] token = new String[Constants.MAX_ARRAY_LEN];
        switch (paramType){
        case "/by":
            token[0] = (String)(this.paramMap.get(paramType));
            this.deadline = token[0];
            break;
        default:
            String customErrorMessage = String.format("The parameter type %s is not implemented. You may want to check your spelling.\n", paramType);
            DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
            return Constants.Error.WRONG_ARGUMENTS;
        }
        return Constants.Error.NO_ERROR;
    }

    @Override
    public String getTypeMessage(){
        String output = "";
        for (Object paramType: this.getParamTypes()) {
            switch ((String) paramType) {
            case "/by":
                output = String.format("(by: %s)", this.deadline);

            default:
                // Due to exception handling at parsePayload above, there is no need to catch errors here
                break;
            }
        }
        return output;
    }
}
