public class CommandHandler {
    /**
     * Handles command and prints messages, if any onto console depending on input parameter.
     *
     * @param command a string of a simple root word to represent a particular message
     * @param packet an input parameter for the message to print, given the command requires further inputs.
     */
    public static void handleCommand(Constants.Command command, Packet packet){
        String output = "";
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
            if (!Duke.isListCreated){
                Duke.isListCreated = true;
                Duke.list = new ListTasks();
            }
            ToDo inputToDo = new ToDo(packet.getPacketPayload());
            err = Duke.list.addTask(inputToDo);
            if (err != Constants.Error.NO_ERROR ){
                DukeException.printErrorMessage(Constants.Error.TASK_NOT_CREATED);
                break;
            } else {
                output = UiManager.getMessageTaskAdded(inputToDo);
                output += UiManager.getMessageReportNumTasks(Duke.list);
            }
            break;

        case INSERT_TASK_EVENT:
            if (!Duke.isListCreated){
                Duke.isListCreated = true;
                Duke.list = new ListTasks();
            }
            try {
                packet.getParamMap();
            } catch(NullPointerException exception) {
                String customErrorMessage = "This command requires a 2 parameter headers and parameter inputs. eg. /at Monday 12-6pm\n";
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                DukeException.printErrorMessage(Constants.Error.TASK_NOT_CREATED);
                break;
            }
            Event inputEvent = new Event(packet.getPacketPayload(), packet.getParamMap());
            inputEvent.setParamMap(packet.getParamMap());
            err = Duke.list.addTask(inputEvent);

            if (err != Constants.Error.NO_ERROR ){
                DukeException.printErrorMessage(Constants.Error.TASK_NOT_CREATED);
                break;
            } else {
                output = UiManager.getMessageTaskAdded(inputEvent);
                output += UiManager.getMessageReportNumTasks(Duke.list);
            }
            break;
        case INSERT_TASK_DEADLINE:
            if (!Duke.isListCreated){
                Duke.isListCreated = true;
                Duke.list = new ListTasks();
            }
            try {
                packet.getParamMap();
            } catch(NullPointerException exception) {
                String customErrorMessage = "This command requires a parameter header and parameter inputs. eg. /by Monday\n";
                customErrorMessage += "Due to error input, the task is not added. Try again.";
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                break;
            }
            Deadline inputDeadline = new Deadline(packet.getPacketPayload(), packet.getParamMap());
            err = Duke.list.addTask(inputDeadline);
            if (err != Constants.Error.NO_ERROR ){
                DukeException.printErrorMessage(Constants.Error.TASK_NOT_CREATED);
                break;
            } else {
                output = UiManager.getMessageTaskAdded(inputDeadline);
                output += UiManager.getMessageReportNumTasks(Duke.list);
            }
            break;
        case SHOW_LIST:
            if(!Duke.isListCreated || Duke.list.getNumTasks() == 0){
                DukeException.printErrorMessage(Constants.Error.NO_LIST);
            } else{
                output = Duke.list.showAllTasks();
            }
            break;
        case MARK_TASK_AS_DONE:
            String customErrorMessage = "";
            if(!Duke.isListCreated){
                DukeException.printErrorMessage(Constants.Error.NO_LIST);
            } else {
                try {
                    int index = Integer.parseInt(packet.getPacketPayload().trim()) - 1;
                    err = Duke.list.markTaskAsDone(index);
                    Task outputTask = Duke.list.getTaskByIndex(index);
                    output = UiManager.getMessageTaskMarkAsDone(outputTask);
                } catch (IndexOutOfBoundsException exception) {
                    if (Duke.list.getNumTasks() != 1) {
                        customErrorMessage = "Your list number ranges from 1 to";
                        customErrorMessage += String.format(" %d. Please check your input list number.", Duke.list.getNumTasks());
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
