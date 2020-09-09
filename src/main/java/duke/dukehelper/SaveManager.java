package duke.dukehelper;
import duke.taskhelper.ListTasks;
import duke.taskhelper.TaskException;
import duke.tasks.Task;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

public class SaveManager extends Command {
    public static ArrayList<String> saveStatesNames = new ArrayList<String>();
    private String name = "";
    private String savePath = Constants.DEFAULT_SAVE_PATH;

    public String showAllTasksSave(ListTasks list){
        Task task;
        String output = "";
        for(int i = 0; i < list.getNumTasks(); i ++) {
            task = list.getTaskByIndex(i);
            output += String.format("| %s | %s | %s%s",
                    task.getTaskType(),
                    task.getIsDone(),
                    getTaskNameFormatted(task.getTaskName(), list.getMaxTaskNameLength()),
                    getTypeMessageFormatted(task.getTypeMessage()));
            output += System.lineSeparator();
        }
        return output;
    }

    public Constants.Error saveToTxt(Hashtable paramMap, ListTasks list) {
        this.setParamMap(paramMap);
        try {
            if (this.error != Constants.Error.NO_ERROR){
                throw new DukeException.ListSaveLoadFail();
            }
            FileWriter fileWriter = new FileWriter(this.savePath + this.name);
            fileWriter.write(showAllTasksSave(list));
            fileWriter.close();
            saveStatesNames.add(this.name);
        } catch (IOException | DukeException.ListSaveLoadFail exception) {
            return Constants.Error.FILE_SAVE_FAIL;
        }
        return this.error;
    }

    public static void loadFromTxt() {
        return;
    }

    @Override
    protected Constants.Error handleParams(String paramType){
        String token = "";
        switch(paramType){
        case "/name":
            try {
                if (((String) this.paramMap.get(paramType)).length() == 0){
                    throw new TaskException.IllegalParam();
                }
                token = (String) this.paramMap.get(paramType);
                this.name = String.format("\\%s.txt\\", token);
            } catch (TaskException.IllegalParam exception){
                String customErrorMessage = String.format("Param %s is expecting 1 string argument: "
                        + "Name of file. Check your input.\n", paramType);
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                return Constants.Error.WRONG_ARGUMENTS;
            }
            break;

        case "/dir":
            this.savePath = (String) this.paramMap.get(paramType);
            break;

        default:
            String customErrorMessage = String.format("The parameter type %s is not implemented.\n", paramType);
            DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
            return Constants.Error.WRONG_ARGUMENTS;
        }
        return Constants.Error.NO_ERROR;
    }

    public static String getTaskNameFormatted(String taskName, int maxWidth) {
        return String.format("%s%s", taskName, UiManager.returnLineWithSymbol(maxWidth - taskName.length()," "));
    }
    public static String getTypeMessageFormatted(String typeMessage) {
        if (typeMessage.strip().length() != 0) {
            return " | " + typeMessage;
        } else {
            return "";
        }
    }
}
