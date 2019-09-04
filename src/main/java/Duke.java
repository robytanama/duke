import java.io.FileNotFoundException;

import duke.storage.Storage;
import duke.ui.UI;
import duke.tasklist.TaskList;
import duke.command.Command;
import duke.parser.Parser;
import duke.exception.DukeException;

public class Duke {
    private Storage storage;
    private UI ui;
    private TaskList taskList;

    public Duke(String filepath) throws Exception {
        this.storage = new Storage(filepath);
        try {
            this.taskList = new TaskList(storage);
        } catch (FileNotFoundException error) {
            this.taskList = new TaskList();
        }

        this.ui = new UI(taskList);
    }

    public void run() throws Exception {
        ui.printIntro();
        storage.loadTasks();
        boolean programRunning = true;
        while(programRunning) {
            try {
                String input = ui.readLine();
                Command inputCommand = Parser.parse(input);
                inputCommand.execute(taskList, storage, ui);
            } catch (DukeException error) {
                ui.printError(error);
            }
        }
    }
    public static void main(String[] args) throws Exception {
        new Duke("D:\\Programs\\Java Projects\\duke\\src\\main\\Data\\Duke.txt").run();
    }
}
