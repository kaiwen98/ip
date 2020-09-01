package duke.dukehelper;
import duke.taskhelper.*;
import duke.tasks.*;
public class CommandHandler {
    /**
     * Handles command and prints messages, if any onto console depending on input parameter.
     *
     * @param command a string of a simple root word to represent a particular message
     * @param packet an input parameter for the message to print, given the command requires further inputs.
     */
    private static boolean isListCreated = false;
    private static ListTasks list;

    public static void handleCommand(Constants.Command command, Packet packet){
        String output = "";
        String customErrorMessage = "";
        boolean isDrawPartition = true;
        Constants.Error err = null;

        switch(command){
        case HELLO:
            output = UiManager.MESSAGE_LOGO;
            output += UiManager.MESSAGE_HELLO;
            break;
        case BYE:
            output = UiManager.MESSAGE_BYE;
            break;
        case ECHO:
            output = String.format("%s\n", packet.getPacketPayload());
            break;
        case PROMPT_INPUT:
            output = ">>> ";
            isDrawPartition = false;
            break;
        case INSERT_TASK_TODO:
            if (!isListCreated){
                isListCreated = true;
                list = new ListTasks();
            }
            ToDo inputToDo = new ToDo(packet.getPacketPayload());
            err = list.addTask(inputToDo);
            if (err != Constants.Error.NO_ERROR ){
                DukeException.printErrorMessage(Constants.Error.TASK_NOT_CREATED);
                break;
            } else {
                output = UiManager.getMessageTaskAdded(inputToDo);
                output += UiManager.getMessageReportNumTasks(list);
            }
            break;

        case INSERT_TASK_EVENT:
            if (!isListCreated){
                isListCreated = true;
                list = new ListTasks();
            }
            try {
                packet.getParamMap();
            } catch(NullPointerException exception) {
                customErrorMessage = "This command requires a 2 parameter headers and parameter inputs. eg. /at Monday 12-6pm\n";
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                DukeException.printErrorMessage(Constants.Error.TASK_NOT_CREATED);
                break;
            }
            Event inputEvent = new Event(packet.getPacketPayload(), packet.getParamMap());
            inputEvent.setParamMap(packet.getParamMap());
            err = list.addTask(inputEvent);

            if (err != Constants.Error.NO_ERROR ){
                DukeException.printErrorMessage(Constants.Error.TASK_NOT_CREATED);
                break;
            } else {
                output = UiManager.getMessageTaskAdded(inputEvent);
                output += UiManager.getMessageReportNumTasks(list);
            }
            break;
        case INSERT_TASK_DEADLINE:
            if (!isListCreated){
                isListCreated = true;
                list = new ListTasks();
            }
            try {
                packet.getParamMap();
            } catch(NullPointerException exception) {
                customErrorMessage = "This command requires a parameter header and parameter inputs. eg. /by Monday\n";
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                DukeException.printErrorMessage(Constants.Error.TASK_NOT_CREATED);
                break;
            }
            Deadline inputDeadline = new Deadline(packet.getPacketPayload(), packet.getParamMap());
            err = list.addTask(inputDeadline);
            if (err != Constants.Error.NO_ERROR ){
                DukeException.printErrorMessage(Constants.Error.TASK_NOT_CREATED);
                break;
            } else {
                output = UiManager.getMessageTaskAdded(inputDeadline);
                output += UiManager.getMessageReportNumTasks(list);
            }
            break;
        case SHOW_LIST:
            if(!isListCreated || list.getNumTasks() == 0){
                DukeException.printErrorMessage(Constants.Error.NO_LIST);
            } else{
                output = list.showAllTasks();
            }
            break;
        case MARK_TASK_DONE:
            if(!isListCreated){
                DukeException.printErrorMessage(Constants.Error.NO_LIST);
            } else {
                try {
                    int index = Integer.parseInt(packet.getPacketPayload().trim()) - 1;
                    err = list.markTaskAsDone(index);
                    Task outputTask = list.getTaskByIndex(index);
                    output = UiManager.getMessageTaskMarkAsDone(outputTask);
                } catch (IndexOutOfBoundsException exception) {
                    if (list.getNumTasks() != 1) {
                        customErrorMessage = "Your list number ranges from 1 to";
                        customErrorMessage += String.format(" %d. Please check your input list number.\n", list.getNumTasks());
                    } else {
                        customErrorMessage = "There is only 1 task in your list.";
                    }
                    DukeException.printErrorMessage(err, customErrorMessage);
                } catch (NullPointerException exception){
                    customErrorMessage = "So which task have you done? Specify a task number.\n";
                    DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                }
            }
            break;
        case SHOW_COMMANDS:
            output = UiManager.MESSAGE_COMMAND_LIST;
            break;
        default:
            DukeException.printErrorMessage(Constants.Error.INVALID_COMMAND);
            break;
        }
        System.out.print(output);
        if (isDrawPartition){
            UiManager.drawPartition();
        }
    }

    public static void handleCommand(Constants.Command command){
        CommandHandler.handleCommand(command, null);
    }
}
