import org.mindrot.jbcrypt.BCrypt;
import java.util.HashMap;
import java.util.Map;

public class AuthManager {
    private Map<String, User> users = new HashMap<>();

    public boolean register(String username, String password, String email) {
        if (users.containsKey(username)) {
            System.out.println("Registration failed: Username already exists.");
            return false;
        }

        String hashedPassword = hashPassword(password);
        User newUser = new User(username, hashedPassword, email);
        users.put(username, newUser);
        System.out.println("User registered successfully.");
        return true;
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user == null) {
            System.out.println("Login failed: User not found.");
            return null;
        }

        if (!user.checkPassword(password)) {
            System.out.println("Login failed: Incorrect password.");
            return null;
        }

        System.out.println("Login successful.");
        return user;
    }

    public static String hashPassword(String password) {
        // Generate a salt and hash the password
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

}
