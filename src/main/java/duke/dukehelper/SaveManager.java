/**
 * Class that handles all file IO operations concerning save states of the program.
 */

package duke.dukehelper;

import duke.taskhelper.ListTasks;
import duke.taskhelper.Packet;
import duke.taskhelper.TaskException;
import duke.tasks.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class SaveManager extends Command {
    private String name = "";
    private String saveFolderPath = Constants.DEFAULT_SAVE_PATH;

    public SaveManager(){
    }

    /**
     * Test if a valid load path is supplied, and if the given document is empty.
     */
    public void testLoadPath(){
        File load = new File(this.getFilePath());
        this.error = (isExistingFileName(this.name) && (load.length() > 0)) ? this.error : Constants.Error.WRONG_ARGUMENTS;
    }

    /**
     * Use Stream method to evaluate existence of file in directory.
     * Makes a folder in working directory if a savestates folder is not found.
     * @param fileName file name input by users
     * @return true if file exists in directory
     */
    public boolean isExistingFileName(String fileName){
        if (!Files.exists(Paths.get(this.saveFolderPath))){
            File file = new File(this.saveFolderPath);
            file.mkdir();
            return false;
        }
        return Arrays.stream(getSaveStateNames()).anyMatch(fileName::equals);
    }

    /**
     * Prints save states onto console in a list.
     */
    public void printSaveStates(){
        String output = String.format("Save states in [%s]:\n", this.saveFolderPath);
        int i = 0;
        for(String fileName : this.getSaveStateNames()) {
            i++;
            output += String.format("%d.\t%s\n", i, fileName);
        }
        System.out.println(output);
    }

    /**
     * Variant of printSaveStates()
     * @return save states onto console in a list in a string.
     */
    public String getSaveStates(){
        String output = String.format("Save states in [%s]:\n", this.saveFolderPath);
        int i = 0;
        for(String fileName : this.getSaveStateNames()) {
            i++;
            output += String.format("%d.\t%s\n", i, fileName);
        }
        return output;
    }

    /**
     * Return total number
     * @return total number of save states in the particular directory
     */
    public int getNumSaveStates(){
        File saveStatesFolder = new File(this.saveFolderPath);
        File[] saveStates = saveStatesFolder.listFiles();
        return saveStates.length;
    }

    /**
     * Get full file path of the specified file
     * @return string of the file path
     */
    public String getFilePath(){
        return this.saveFolderPath + this.name + "\\";
    }

    public String getFileName(){
        return this.name;
    }

    /**
     * Helper function, to consolidate names of save states for searching functions
     * @return String array of save state names in the particular directory
     */
    public String[] getSaveStateNames(){
        File saveStatesFolder = new File(this.saveFolderPath);
        File[] saveStates = saveStatesFolder.listFiles();
        String[] saveStateNames = new String[saveStates.length];
        for (int i = 0; i < saveStates.length; i++){
            saveStateNames[i] = saveStates[i].getName();
        }
        return saveStateNames;
    }

    /**
     * Formats the list in a particular way before outputting to .txt file
     * @param list
     * @return String of the formatted list
     */
    public String getAllTasksSaveToString(ListTasks list){
        Task task;
        String output = "";
        for(int i = 0; i < list.getNumTasks(); i ++) {
            task = list.getTaskByIndex(i);
            output += String.format("| %s | %s | %s%s",
                    task.getTaskType(),
                    task.getIsDone(),
                    getTaskNameFormatted(task.getTaskName(), list.getMaxTaskNameLength()),
                    getTypeMessageFormatted(task.getTypeMessage("datetime")));
            output += System.lineSeparator();
        }
        return output;
    }

    /**
     * Ensures that the width occupied by the task name in the output text file is the same, hence it looks neater. Purely cosmetic in purpose.
     * @param taskName
     * @param maxWidth With of the longest task name in the list for correct allocation of width sizes
     * @return String of task name left-adjusted in a char field of fixed width
     */
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

    /**
     * Function that performs the list-to-file conversion.
     * @param list
     * @return error code where applicable.
     */
    public Constants.Error saveToTxt(ListTasks list) {
        try {
            if (this.error != Constants.Error.NO_ERROR){
                throw new DukeException.ListSaveLoadFail();
            }
            if (!Files.exists(Paths.get(this.saveFolderPath))){
                File file = new File(this.saveFolderPath);
                file.mkdir();
            }
            FileWriter fileWriter = new FileWriter(this.saveFolderPath + this.name);
            fileWriter.write(getAllTasksSaveToString(list));
            fileWriter.close();
        } catch (IOException | DukeException.ListSaveLoadFail exception) {
            return Constants.Error.FILE_SAVE_FAIL;
        }
        return this.error;
    }

    /**
     * Function that performs the file-to-list conversion.
     * First, it will check whether the file exists in the specified directory.
     * If found, then it will retrieve the file and convert to a list within the program with all information inherited.
     * @param list
     * @return error code where applicable
     */
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
            this.testLoadPath();
            if(this.error != Constants.Error.NO_ERROR){
                return Constants.Error.OTHER_ERROR;
            }
            FileReader fileReader = new FileReader(this.getFilePath());
            fileReader.read(inputArray, 0, inputArray.length);
            fileReader.close();
            output = new String(inputArray);
            processSaveStateToString(output);
        } catch (IOException | DukeException.ListSaveLoadFail exception) {
            return Constants.Error.FILE_LOAD_FAIL;
        } catch (DukeException.FileNotFound exception){
            customErrorMessage = this.getFilePath() + "\n";
            DukeException.printErrorMessage(Constants.Error.FILE_NOT_FOUND, customErrorMessage);
            this.printSaveStates();
            return Constants.Error.FILE_LOAD_FAIL;
        } catch (NullPointerException exception) {
            customErrorMessage = "The file chosen is not compatible with the current version of Duke!\n";
            DukeException.printErrorMessage(Constants.Error.OTHER_ERROR, customErrorMessage);
            return Constants.Error.FILE_LOAD_FAIL;
        }
        return this.error;
    }

    /**
     * Parameters handled by the command
     * /name - Name for the save state file to be saved locally.
     * /dir - custom directory that is different from the default save directory.
     * @param paramType
     * @return error code where applicable.
     */
    @Override
    protected Constants.Error handleParams(String paramType){
        String token = "";
        switch(paramType){
        case "/name":
            try {
                if ((this.getParam(paramType)).length() == 0) {
                    throw new TaskException.IllegalParam();
                }
                token = ((String) this.paramMap.get(paramType)).replace(".txt", "");
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

    /**
     * From a string extracted from a given text file, parse the string to consolidate information in a packet class
     * Then, input the packet instance into handleCommand such that each task is added into the local list
     * Note that a boolean value = false is passed into handleCommand. This is to turn off the verbosity of the command,
     * Hence it will not spam the user while the tasks are extracted and added into the list.
     * Note that NullPointerException is thrown in case an incompatible save state is loaded into the program.
     * @param saveState
     */
    public void processSaveStateToString(String saveState) throws NullPointerException{
        Packet packet;

        saveState = saveState.trim();
        String[] tokens = saveState.split("\r\n");
        String[] buffer;
        String[] paramBuffer;
        Constants.Command command = null;

        for (String token: tokens) {
            token = token.replace("|", ";").replaceFirst(";", "\0");
            buffer = token.split(";");

            for (int i = 0; i < buffer.length; i ++){
                buffer[i] = buffer[i].trim();
            }
            command = (buffer[0].equals("D")) ? Constants.Command.INSERT_TASK_DEADLINE :
                      (buffer[0].equals("T")) ? Constants.Command.INSERT_TASK_TODO :
                      (buffer[0].equals("E")) ? Constants.Command.INSERT_TASK_EVENT : null;
            buffer[0] = (buffer[0].equals("D")) ? "deadline" :
                        (buffer[0].equals("T")) ? "todo" :
                        (buffer[0].equals("E")) ? "event" : "";

            packet = new Packet(buffer[0], buffer[2].strip(), "From load");
            packet.addParamToMap("/done", buffer[1]);

            // If the length is 4, there must be params associated with the particular task.
            // We will need to break it down into commands and rehandle it to recreate the list.
            if (buffer.length == 4) {
                buffer[3] = buffer[3].replace("(","").replace(")","");
                paramBuffer = buffer[3].replaceFirst(":", ";").split(";");

                packet.addParamToMap("/" + paramBuffer[0].replace("(","").trim(), paramBuffer[1].trim());
            }
            CommandHandler.handleCommand(command, packet, false, false);
        }
    }

    /**
     * Deletes the original default save file so that you can load in the current list without requiring overwrite prompt.
     */
    public void deleteDefaultSaveFile(){
        System.out.println(this.saveFolderPath + "lastSave.txt");
        File file = new File(this.saveFolderPath + "lastSave.txt");
        if(isExistingFileName("lastSave.txt")) {
            file.delete();
        }
    }
}
