package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.task.ToDo;
import duke.Ui;

public class ToDoCommand extends Command {
    private String toDoDescription;

    public ToDoCommand(String toDoDescription) {
        super();
        this.toDoDescription = toDoDescription;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ToDo toDo = new ToDo(toDoDescription);
        taskList.addToTaskList(toDo);
        ui.showAddTaskMessage(taskList, toDo);
    }
}