import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/task_manager_db";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";//Change your_password to my/your MySQL password

    private Connection connection;//Manages the connection between Java and task_manager_db.

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, hashed_password, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, User.getUsername());
            stmt.setString(2, User.hashedPassword);
            stmt.setString(3, User.getEmail());
            stmt.executeUpdate();
        }
    }

    public User loadUser(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("hashed_password");
                String email = rs.getString("email");
                User user = new User(username, hashedPassword, email);
                return user;
            }
        }
        return null;
    }

    public void saveTask(Task task, String username) throws SQLException {
        String sql = "INSERT INTO tasks (username, title, description, is_done) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, task.title);
            stmt.setString(3, task.description);
            stmt.setBoolean(4, task.isDone());
            stmt.executeUpdate();
        }
    }

    public List<Task> loadTasks(String username) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                String description = rs.getString("description");
                boolean isDone = rs.getBoolean("is_done");

                Task task = new Task(title, description);
                if (isDone) task.markAsDone();
                tasks.add(task);
            }
        }
        return tasks;
    }

    public void updateTaskStatus(int taskId, boolean isDone) throws SQLException {
        String sql = "UPDATE tasks SET is_done = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, isDone);
            stmt.setInt(2, taskId);
            stmt.executeUpdate();
        }
    }

    public void deleteTask(int taskId) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, taskId);
            stmt.executeUpdate();
        }
    }
}
