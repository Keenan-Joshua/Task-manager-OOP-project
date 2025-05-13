import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<Task> tasks = new ArrayList<>();

    public void addTask(String title, String description) { 
        tasks.add(new Task(title, description));
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet.");
            return; // Prevents unnecessary loop
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void markTaskAsDone(int index) {
        checkIndex(index);
        tasks.get(index).markAsDone();
        System.out.println("Task marked as done.");
    }

    public void deleteTask(int index) {
        checkIndex(index);
        tasks.remove(index);
        System.out.println("Task deleted.");
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= tasks.size()) { 
            throw new IndexOutOfBoundsException("Invalid task number.");
        }
    }
}
