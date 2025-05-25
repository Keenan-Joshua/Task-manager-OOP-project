import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class AuthManager {
    private Map<String, User> users = new HashMap<>();

    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("Registration failed: Username already exists.");
            return false;
        }

        String hashedPassword = hashPassword(password);
        User newUser = new User(username, hashedPassword);
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
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashed) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: Hashing algorithm not found.");
        }
    }
}
