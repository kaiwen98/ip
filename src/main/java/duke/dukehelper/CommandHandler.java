/**
 * Class to handle command inputs by user
 */
package duke.dukehelper;
import duke.taskhelper.*;
import duke.tasks.*;

public class CommandHandler {
    private static ListTasks list = new ListTasks();

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
                DukeException.printErrorMessage(Constants.Error.NO_LIST);
            } else {
                output = list.showAllTasks();
            }
            break;

        case REMOVE_TASK:
            // Fall through
        case MARK_TASK_DONE:
            try {
                validatePayload(packet);
                validateList();
                int index = Integer.parseInt(packet.getPacketPayload().trim()) - 1;
                err = handleTaskCommands(list, command, index);
                Task outputTask = list.getTaskByIndex(index);
                output = getMessageTaskCommands(command, outputTask);
            } catch (IndexOutOfBoundsException exception) {
                if (list.getNumTasks() != 1) {
                    customErrorMessage = "Your list number ranges from 1 to";
                    customErrorMessage += String.format(" %d. Please check your input list number.\n", list.getNumTasks());
                } else {
                    customErrorMessage = "There is only 1 task in your list.\n";
                }
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
            } catch (DukeException.InvalidDescription exception){
                customErrorMessage = "You have not entered a valid task number!\n";
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
            } catch (DukeException.NoList exception) {
                DukeException.printErrorMessage(Constants.Error.NO_LIST);
            } catch (NumberFormatException exception){
                customErrorMessage = "Non-numeric inputs are not acceptable.\n";
                DukeException.printErrorMessage(Constants.Error.WRONG_ARGUMENTS, customErrorMessage);
            } finally {
                if (err != Constants.Error.NO_ERROR){
                    DukeException.printErrorMessage(Constants.Error.TASK_COMMAND_FAIL);
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

    /**
     * Handles commands that are specific to a particular task.
     * @param list lists containing the tasks
     * @param command command input by user
     * @param index index of task to be deleted/ marked as done
     * @return error due to task commands failing if applicable
     */
    private static Constants.Error handleTaskCommands(ListTasks list, Constants.Command command, int index){
        switch (command) {
        case MARK_TASK_DONE:
            return list.markTaskAsDone(index);
        case REMOVE_TASK:
            return list.removeTask(index);
        default:
            return Constants.Error.OTHER_ERROR;
        }
    }
}
