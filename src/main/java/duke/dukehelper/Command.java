/**
 * Abstract class that all classes that are commands in nature inherits from.
 * This abstracts the ability to capture params from packet and evaluate with their local requirements.
 * The evaluation function is handleParams, which must be overwritten by the subclasses.
 */

package duke.dukehelper;

import java.util.HashMap;
import java.util.Set;

public abstract class Command {
    protected HashMap paramMap;
    public Constants.Error error = Constants.Error.OTHER_ERROR;

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
    public void setParamMap(HashMap paramMap){
        this.paramMap = new HashMap();
        this.paramMap = (HashMap) paramMap.clone();
        processParamMap();
    }
    public Set getParamTypes() {
        return paramMap.keySet();
    }
}
