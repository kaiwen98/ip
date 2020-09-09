package duke.dukehelper;

import java.util.Hashtable;
import java.util.Set;

public abstract class Command {
    protected Hashtable paramMap;
    protected Constants.Error error;

    protected abstract Constants.Error handleParams(String paramType);

    /**
     * For each param type in the hash table (Param Map), task to handle the contents accordingly with a corresponding action.
     */
    protected void processParamMap() {
        String customErrorMessage = "";
        if (!this.paramMap.isEmpty()) {
            for (Object paramType : this.getParamTypes()) {
                // If there is at least one valid param type and param to process, no need to dismiss the entire task.
                if (this.handleParams((String) paramType) == Constants.Error.NO_ERROR) {
                    this.error = Constants.Error.NO_ERROR;
                }
            }
        } else {
            customErrorMessage = "This command expects a param type-param input, eg. /by Monday etc.\n";
            DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
        }
    }
    public void setParamMap(Hashtable paramMap){
        this.paramMap = new Hashtable();
        this.paramMap = (Hashtable) paramMap.clone();
        processParamMap();
    }
    public Set getParamTypes() {
        return paramMap.keySet();
    }
}
