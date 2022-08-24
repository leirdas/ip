import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Storage {
    private Path relativeDirectoryPath;
    private Path relativeFilePath;

    public Storage(String projectRoot) {
        this.relativeDirectoryPath = Paths.get(projectRoot, "data");
        this.relativeFilePath = Paths.get(projectRoot, "data", "duke.txt");
    }


    public boolean isDirectoryCreated() {
        return Files.exists(relativeDirectoryPath);
    }


    public boolean isFilePresent() {
        return Files.exists(relativeFilePath);
    }


    public void createDirectory() {
        try {
            Files.createDirectory(relativeDirectoryPath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void createFile() {
        try {
            Files.createFile(relativeFilePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public Task parseStringToTask(String details, String taskType) {
        Task task = null;
        switch (taskType) {
            case "T": {
                String[] taskDetails = details.split(" \\| ");
                task = new ToDo(taskDetails[1]);
                if (Integer.parseInt(taskDetails[0]) == 1) {
                    task.markAsDone();
                }
                break;
            }
            case "D":
            case "E": {
                String[] taskDetails = details.split(" \\| ");
                task = new Deadline(taskDetails[1], taskDetails[2]);
                if (Integer.parseInt(taskDetails[0]) == 1) {
                    task.markAsDone();
                }
                break;
            }
            default:
                break;
        }
        return task;
    }


    public ArrayList<Task> load() {
        ArrayList<Task> taskList = new ArrayList<>();
        try {
            List<String> loadData = Files.readAllLines(relativeFilePath);
            for (String taskString : loadData) {
                String[] taskDetails = taskString.split(" \\| ", 2);
                Task task = null;
                String taskType = taskDetails[0];
                switch (taskType) {
                    case "T": {
                        task = parseStringToTask(taskDetails[1], "T");
                        break;
                    }
                    case "D": {
                        task = parseStringToTask(taskDetails[1], "D");
                        break;
                    }
                    case "E": {
                        task = parseStringToTask(taskDetails[1], "E");
                        break;
                    }
                    default:
                        break;
                }
                if (task != null) {
                    taskList.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return taskList;
    }


    public void save(ArrayList<Task> taskList) {
        try {
            String data;
            String[] stringDataArr = new String[taskList.size()];
            int i = 0;
            for (Task task : taskList) {
                switch (task.getTaskType()) {
                case "T": {
                    data = "T" + " | " + task.getStatus() + " | " + task.getDescription();
                    stringDataArr[i] = data;
                    break;
                }
                case "D": {
                    data = "D" + " | " + task.getStatus() + " | " + task.getDescription();
                    stringDataArr[i] = data;
                    break;
                }
                case "E": {
                    data = "E" + " | " + task.getStatus() + " | " + task.getDescription();
                    stringDataArr[i] = data;
                    break;
                }
                default:
                    break;
                }
                i++;
            }
            Files.write(relativeFilePath, Arrays.asList(stringDataArr));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}