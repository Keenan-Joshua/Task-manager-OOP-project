import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.connect();

        AuthManager auth = new AuthManager();

        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        auth.register(username, password);
        User user = auth.login(username, password);
        dbManager.saveUser(user);

        dbManager.loadUser(user.getUsername());

        TaskManager taskManager = new TaskManager();

        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        taskManager.addTask(title, description);
        Task task = new Task(title, description);

        dbManager.saveTask(task, user.getUsername());

        taskManager.viewTasks();

        task.markAsDone();
        dbManager.updateTaskStatus(0, true);

        taskManager.markTaskAsDone(0);
        taskManager.viewTasks();

        dbManager.loadTasks(user.getUsername());

        taskManager.deleteTask(0);
        dbManager.deleteTask(0);
    }
}
