package duke.dukehelper;
import duke.taskhelper.ListTasks;
import duke.taskhelper.Packet;
import duke.taskhelper.TaskException;
import duke.tasks.Task;

import java.io.File;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;

public class SaveManager extends Command {
    public static ArrayList<String> saveStatesNames = new ArrayList<String>();
    private String name = "";
    private String saveFolderPath = Constants.DEFAULT_SAVE_PATH;
    public SaveManager(){
    }
    public SaveManager(Hashtable paramMap){
        super.setParamMap(paramMap);
    }

    public void printSaveStates(){
        String output = "";
        int i = 0;
        for(String fileName : this.getSaveStateNames()) {
            i++;
            output += String.format("%d.\t%s\n", i, fileName);
        }
        System.out.println(output);
    }

    public boolean isExistingFileName(String fileName){
        //System.out.println(fileName);
        return Arrays.stream(getSaveStateNames()).anyMatch(fileName::equals);
    }

    public String getFilePath(){
        return this.saveFolderPath + this.name + "\\";
    }

    public String getFileName(){
        return this.name;
    }


    public String[] getSaveStateNames(){
        //System.out.println(this.saveFolderPath);
        File saveStatesFolder = new File(this.saveFolderPath);
        File[] saveStates = saveStatesFolder.listFiles();
        String[] saveStateNames = new String[saveStates.length];
        for (int i = 0; i < saveStates.length; i++){
            saveStateNames[i] = saveStates[i].getName();
        }
        return saveStateNames;
    }

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

    public Constants.Error saveToTxt(ListTasks list) {
        this.setParamMap(this.paramMap);
        try {
            if (this.error != Constants.Error.NO_ERROR){
                throw new DukeException.ListSaveLoadFail();
            }
            FileWriter fileWriter = new FileWriter(this.saveFolderPath + this.name);
            fileWriter.write(showAllTasksSave(list));
            fileWriter.close();
            saveStatesNames.add(this.name);
        } catch (IOException | DukeException.ListSaveLoadFail exception) {
            return Constants.Error.FILE_SAVE_FAIL;
        }
        return this.error;
    }

    public Constants.Error loadFromTxt(ListTasks list) {
        char[] inputArray = new char[1024];
        String output = null;
        String customErrorMessage = "";
        try {
            if (this.error != Constants.Error.NO_ERROR){
                throw new DukeException.ListSaveLoadFail();
            }
            if (!isExistingFileName(this.name)) {
                throw new DukeException.FileNotFound();
            }
            FileReader fileReader = new FileReader(this.getFilePath());
            fileReader.read(inputArray, 0, inputArray.length);
            fileReader.close();
            output = new String(inputArray);
            processSaveStateToString(output);
            saveStatesNames.add(this.name);
        } catch (IOException | DukeException.ListSaveLoadFail exception) {
            return Constants.Error.FILE_LOAD_FAIL;
        } catch (DukeException.FileNotFound exception){
            customErrorMessage = this.getFilePath() + "\n";
            DukeException.printErrorMessage(Constants.Error.FILE_NOT_FOUND, customErrorMessage);
            this.printSaveStates();
            return Constants.Error.FILE_LOAD_FAIL;
        }
        return this.error;
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
                this.name = String.format("%s.txt", token);
            } catch (TaskException.IllegalParam exception){
                String customErrorMessage = String.format("Param %s is expecting 1 string argument: "
                        + "Name of file. Check your input.\n", paramType);
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                return Constants.Error.WRONG_ARGUMENTS;
            }
            break;

        case "/dir":
            this.saveFolderPath = (String) this.paramMap.get(paramType);
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
    public String processSaveStateToString(String saveState){
        Packet packet;

        saveState = saveState.trim();
        String[] tokens = saveState.split("\r\n");
        String[] buffer;
        String[] paramBuffer;
        Constants.Command command = null;

        for (String token: tokens) {
//            System.out.println("newln");
//            System.out.println(token);
            token = token.replace("|", ";").replaceFirst(";", "\0");
            buffer = token.split(";");
//            System.out.println(token);
//            System.out.println(buffer[1]);
//            System.out.println(buffer[2]);
            for (int i = 0; i < buffer.length; i ++){
                buffer[i] = buffer[i].trim();
            }
            command = (buffer[0].equals("D")) ? Constants.Command.INSERT_TASK_DEADLINE :
                      (buffer[0].equals("T")) ? Constants.Command.INSERT_TASK_TODO :
                      (buffer[0].equals("E")) ? Constants.Command.INSERT_TASK_EVENT : null;
            buffer[0] = (buffer[0].equals("D")) ? "deadline" :
                        (buffer[0].equals("T")) ? "todo" :
                        (buffer[0].equals("E")) ? "event" : "";

//            System.out.println(buffer[3]);
            packet = new Packet(buffer[0], buffer[2].strip());
            packet.addParamToMap("/done", buffer[1]);
            if (buffer.length == 4) {
                paramBuffer = buffer[3].replace(":", ";").split(";");
//                System.out.println(paramBuffer[0]);
//                System.out.println(paramBuffer[1]);
                packet.addParamToMap("/" + paramBuffer[0].trim(), paramBuffer[1].trim());
            }
//            System.out.println(command);
            CommandHandler.handleCommand(command, packet, false, false);
        }
        return saveState;
    }
}
