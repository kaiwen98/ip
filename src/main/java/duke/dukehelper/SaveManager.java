package duke.dukehelper;
import duke.taskhelper.ListTasks;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class SaveManager {
    private ArrayList<String> saveStatesNames = new ArrayList<String>();
    public Constants.Error saveToTxt(String saveName, ListTasks list) throws IOException {
        saveName = String.format("\\%s%s", saveName, ".txt\\");
        try {
            FileWriter fileWriter = new FileWriter(Constants.SAVE_PATH + saveName);
            fileWriter.write(list.showAllTasks());
            fileWriter.close();
            this.saveStatesNames.add(saveName);
        } catch (IOException e) {
            return Constants.Error.FILE_SAVE_FAIL;
        }
        return Constants.Error.NO_ERROR;
    }
    public static void loadFromTxt() {
        return;
    }
}
