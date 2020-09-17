/**
 * Class to handle command inputs by user
 */
package duke.dukehelper;
import duke.taskhelper.ListTasks;
import duke.taskhelper.Packet;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.ToDo;

import static duke.dukehelper.Routine.replyRoutine;

public class CommandHandler {
    public static ListTasks list = new ListTasks();
    private static SaveManager saveManager = null;
    private static int conversationCounter = 0;
    /**
     * Validates if the input supplies a description as needed by the command
     * @param packet
     * @throws DukeException.InvalidDescription
     */
    private static void validatePayload(Packet packet) throws DukeException.InvalidDescription {
        if (packet.getPacketPayload() == null){
            throw new DukeException.InvalidDescription();
        }
    }

    /**
     * Validates if list is empty
     * @throws DukeException.NoList
     */
    private static void validateList() throws DukeException.NoList {
        if (list.getNumTasks() == 0){
            throw new DukeException.NoList();
        }
    }
    /**
     * Handles command and prints messages, if any onto console depending on input parameter.
     *
     * @param command a string of a simple root word to represent a particular message
     * @param packet an input parameter for the message to print, given the command requires further inputs.
     */
    public static Constants.Command handleCommand(Constants.Command command, Packet packet, boolean verbose, boolean isReply){
        String output = "";
        String customErrorMessage = "";
        boolean isDrawPartition = true;
        // If true, data will not be overwritten, and user will be prompted to input to respond to the command requirements.
        boolean exit = false;
        // If a command demands a response from the user, this variable will relay to Duke the command to reply to.
        // If no reply is needed, this variable stays at null.
        Constants.Command commandToReply = null;
        // Error code where applicable.
        Constants.Error err = null;

        switch(command){
        case HELLO:
            output = UiManager.MESSAGE_LOGO;
            output += UiManager.MESSAGE_HELLO;
            break;

        case BYE:
            output = UiManager.MESSAGE_BYE;
            break;

        case PROMPT_INPUT:
            output = ">>> ";
            isDrawPartition = false;
            break;

        case ECHO:
            output = "<ECHO> " + packet.getRawInput() + "\n";
            break;

        case INSERT_TASK_DEADLINE:
            //Fall through
        case INSERT_TASK_TODO:
            //Fall through
        case INSERT_TASK_EVENT:
            /**
             *  The exception handling task is split into 2 types.
             *  The first exception type should be detected by the command handler; If the command needs a
             *  description and the input does not supply any, we throw an exception here.
             *  The second exception type is unique to what the different variants of tasks want,
             *  so we encapsulate the task-unique exception handling within their own class.
             *  This principle is the same with saving, loading, task removing and marking as done.
             */
            label: try {
                validatePayload(packet);
                Task inputEvent = generateTask(command, packet);
                err = list.addTask(inputEvent);
                if (err != Constants.Error.NO_ERROR) {
                    break label;
                }
                output = UiManager.getMessageTaskAdded(inputEvent);
                output += UiManager.getMessageReportNumTasks(list);
            } catch (DukeException.InvalidDescription exception) {
                customErrorMessage = "You have not entered a valid description!\n";
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
            } finally {
                if (err != Constants.Error.NO_ERROR ) {
                    DukeException.printErrorMessage(Constants.Error.TASK_NOT_CREATED);
                }
            }
            break;

        case SHOW_LIST:
            if (list.getNumTasks() == 0){
                customErrorMessage = "Begin by adding some tasks!\n";
                DukeException.printErrorMessage(Constants.Error.NO_LIST, customErrorMessage);
            } else {
                list.setParamMap(packet.getParamMap());
                output = list.showAllTasks();
            }
            break;

        case REMOVE_TASK:
            label:try {
                validatePayload(packet);
                validateList();
                // We offset index by negative 1 to correspond with array index sequence.
                int index = Integer.parseInt(packet.getPacketPayload().trim()) - 1;
                err = list.removeTask(index);
                if (err != Constants.Error.NO_ERROR) {
                    break label;
                }
                Task outputTask = list.getDeletedTask();
                output = getMessageTaskCommands(command, outputTask);
            } catch (DukeException.InvalidDescription exception){
                customErrorMessage = "You have not entered a valid task number!\n";
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
            } catch (DukeException.NoList exception) {
                DukeException.printErrorMessage(Constants.Error.NO_LIST);
            } finally {
                if (err != Constants.Error.NO_ERROR){
                    DukeException.printErrorMessage(Constants.Error.TASK_COMMAND_FAIL);
                } else {
                    customErrorMessage = String.format("You have %d task/s left.\n", list.getNumTasks());
                    DukeException.printErrorMessage(Constants.Error.NO_ERROR, customErrorMessage);
                }
            }
            break;

        case MARK_TASK_DONE:
            label:try {
                validatePayload(packet);
                validateList();
                // We offset index by negative 1 to correspond with array index sequence.
                int index = Integer.parseInt(packet.getPacketPayload().trim()) - 1;
                err = list.markTaskAsDone(index);
                if (err != Constants.Error.NO_ERROR) {
                    break label;
                }
                Task outputTask = list.getTaskByIndex(index);
                output = getMessageTaskCommands(command, outputTask);
            } catch (DukeException.InvalidDescription exception){
                customErrorMessage = "You have not entered a valid task number!\n";
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
            } catch (DukeException.NoList exception) {
                DukeException.printErrorMessage(Constants.Error.NO_LIST);
            } finally {
                if (err != Constants.Error.NO_ERROR){
                    DukeException.printErrorMessage(Constants.Error.TASK_COMMAND_FAIL);
                }
            }
            break;

        case SAVE_FILE:
            /**
             * Saves list to a file in the local disk.
             * If the file already exist with same file name,
             * prompt the user on whether the file is to be overwritten.
             */
            if (!isReply) {
                saveManager = new SaveManager();
                saveManager.setParamMap(packet.getParamMap());
                if (saveManager.isExistingFileName(saveManager.getFileName())){
                    DukeException.printErrorMessage(Constants.Error.FILE_EXISTS);
                    commandToReply = Constants.Command.SAVE_FILE;
                    break;
                }
            } else {
                switch(packet.getPacketType()) {
                case "y":
                    customErrorMessage  = String.format("Alright, save state below will be overwritten:\t[%s]\n", saveManager.getFilePath());
                    DukeException.printErrorMessage(Constants.Error.NO_ERROR, customErrorMessage);
                    break;

                case "n":
                    customErrorMessage = "Got it. Enter some other commands then!\n";
                    DukeException.printErrorMessage(Constants.Error.NO_ERROR, customErrorMessage);
                    commandToReply = null;
                    exit = true;
                    break;

                default:
                    customErrorMessage = "Input not recognised. Enter either \"Y\" or \"N\".\n";
                    DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                    commandToReply = Constants.Command.SAVE_FILE;
                    exit = true;
                    break;
                }
            }

            if (!exit) {
                err = saveManager.saveToTxt(list);
                if (err == Constants.Error.NO_ERROR) {
                    output = UiManager.getMessageListSaved(list);
                } else {
                    DukeException.printErrorMessage(Constants.Error.FILE_SAVE_FAIL);
                }
            }
            break;

        case LOAD_FILE:
            /**
             * Saves list to a file in the local disk.
             * If the file already exist with same file name,
             * prompt the user on whether the file is to be overwritten.
             * Also, if the user has an existing list,
             * prompt user to either save existing work via recursive call of handleCommand()
             * or to discard current changes.
             */
            if (!isReply) {
                saveManager = new SaveManager();
                saveManager.setParamMap(packet.getParamMap());
                if (saveManager.error != Constants.Error.NO_ERROR) {
                    // Fall-through
                }
                else if (list.getNumTasks() > 0){
                    DukeException.printErrorMessage(Constants.Error.LIST_EXISTS);
                    commandToReply = Constants.Command.LOAD_FILE;
                    break;
                }
            } else {
                switch(packet.getPacketType()) {
                case "y":
                    customErrorMessage  = String.format("Alright, Enter the save command now:\n");
                    DukeException.printErrorMessage(Constants.Error.NO_ERROR, customErrorMessage);
                    // Switch context to save command, then return back to this point when done.
                    replyRoutine(Constants.Command.SAVE_FILE, false);
                    break;

                case "n":
                    customErrorMessage = "Got it. I will discard the current list and load in the save state.\n";
                    list.removeAllTasks();
                    DukeException.printErrorMessage(Constants.Error.NO_ERROR, customErrorMessage);
                    exit = true;
                    break;

                default:
                    customErrorMessage = "Input not recognised. Enter either \"Y\" or \"N\".\n";
                    DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
                    commandToReply = Constants.Command.LOAD_FILE;
                    break;
                }
            }

            if (!exit) {
                list.removeAllTasks();
                err = saveManager.loadFromTxt(list);
                if (err == Constants.Error.NO_ERROR) {
                    output = UiManager.getMessageListLoaded(list);
                } else {
                    DukeException.printErrorMessage(Constants.Error.FILE_LOAD_FAIL);
                }
            }
            break;

        case SHOW_COMMANDS:
            output = UiManager.MESSAGE_COMMAND_LIST;
            break;

        case SHOW_SAVE_STATES:
            saveManager = new SaveManager();
            if(saveManager.getNumSaveStates() == 0){
                customErrorMessage = "Begin by saving some lists!\n";
                DukeException.printErrorMessage(Constants.Error.NO_LIST, customErrorMessage);
            } else {
                output = saveManager.getSaveStates();
            }

            break;

        case REFRESH_FILE:
            saveManager = new SaveManager();
            saveManager.deleteDefaultSaveFile();
            break;

        default:
            DukeException.printErrorMessage(Constants.Error.INVALID_COMMAND);
            break;
        }
        if (verbose) {
            System.out.print(output);
            if (isDrawPartition){
                UiManager.drawPartition();
            }
        }
        return commandToReply;
    }
    public static Constants.Command handleCommand(Constants.Command command, Packet packet){
        return handleCommand(command, packet, true, false);
    }
    public static Constants.Command handleCommand(Constants.Command command){
        return CommandHandler.handleCommand(command, null, true, false);
    }

    /**
     * Since task removal and task completion record is given a generalised treatment in handleCommand(),
     * We will use this function to output the corresponding status message.
     * @param command command by user
     * @param outputTask task to be deleted/ marked as done
     * @return
     */
    private static String getMessageTaskCommands(Constants.Command command, Task outputTask) {
        switch (command){
        case MARK_TASK_DONE:
            return UiManager.getMessageTaskMarkAsDone(outputTask);
        case REMOVE_TASK:
            return UiManager.getMessageTaskRemove(outputTask);
        default:
            return null;
        }
    }

    /**
     * Since all variants of tasks are given a generalised treatment in handleCommand(),
     * We will use this function to output the corresponding task to add to list.
     * @param command command by user
     * @param packet packet that are supplying params and payload of data
     * @return task with corresponding type
     */
    private static Task generateTask(Constants.Command command, Packet packet){
        switch (command) {
        case INSERT_TASK_DEADLINE:
            return new Deadline(packet.getPacketPayload(), packet.getParamMap());
        case INSERT_TASK_EVENT:
            return new Event(packet.getPacketPayload(), packet.getParamMap());
        case INSERT_TASK_TODO:
            return new ToDo(packet.getPacketPayload());
        default:
            return null;
        }
    }
}
