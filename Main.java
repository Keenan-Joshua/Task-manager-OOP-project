import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n1. Add Task\n2. View Tasks\n3. Mark as Done\n4. Delete Task\n5. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            try {
                switch (choice) {
                    case "1" -> {
                        System.out.print("Title: ");
                        String title = sc.nextLine();
                        System.out.print("Description: ");
                        String desc = sc.nextLine();
                        manager.addTask(title, desc);
                        System.out.println("Task added.");
                    }
                    case "2" -> manager.viewTasks();
                    case "3" -> {
                        manager.viewTasks();
                        System.out.print("Task number to mark as done: ");
                        int doneIndex = Integer.parseInt(sc.nextLine()) - 1;
                        manager.markTaskAsDone(doneIndex);
                    }
                    case "4" -> {
                        manager.viewTasks();
                        System.out.print("Task number to delete: ");
                        int delIndex = Integer.parseInt(sc.nextLine()) - 1;
                        manager.deleteTask(delIndex);
                    }
                    case "5" -> running = false;
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
        System.out.println("Goodbye!");
    }
}
