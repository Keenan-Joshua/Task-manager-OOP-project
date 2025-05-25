import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AuthManager authManager = new AuthManager();
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.connect();

        while (true) {
            System.out.println("\n--- Task Manager ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                boolean success = authManager.register(username, password);
                if (success) dbManager.saveUser(authManager.login(username, password));
            }

            else if (option == 2) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                User user = authManager.login(username, password);

                if (user != null) {
                    TaskManager taskManager = new TaskManager();
                    taskManager.tasks = dbManager.loadTasks(user.getUsername());

                    while (true) {
                        System.out.println("\n--- Task Menu ---");
                        System.out.println("1. Add Task");
                        System.out.println("2. View Tasks");
                        System.out.println("3. Mark Task as Done");
                        System.out.println("4. Delete Task");
                        System.out.println("5. Logout");
                        System.out.print("Select an option: ");
                        int taskOption = scanner.nextInt();
                        scanner.nextLine();

                        if (taskOption == 1) {
                            System.out.print("Enter task title: ");
                            String title = scanner.nextLine();
                            System.out.print("Enter task description: ");
                            String description = scanner.nextLine();
                            taskManager.addTask(title, description);
                            dbManager.saveTask(new Task(title, description), user.getUsername());
                        }

                        else if (taskOption == 2) {
                            taskManager.viewTasks();
                        }

                        else if (taskOption == 3) {
                            System.out.print("Enter task index to mark as done: ");
                            int index = scanner.nextInt();
                            scanner.nextLine();
                            taskManager.markTaskAsDone(index);
                            dbManager.updateTaskStatus(index, true);
                        }

                        else if (taskOption == 4) {
                            System.out.print("Enter task index to delete: ");
                            int index = scanner.nextInt();
                            scanner.nextLine();
                            taskManager.deleteTask(index);
                            dbManager.deleteTask(index);
                        }

                        else if (taskOption == 5) {
                            break;
                        }
                    }
                }
            }

            else if (option == 3) {
                break;
            }
        }

        scanner.close();
    }
}
