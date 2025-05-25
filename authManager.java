import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class AuthManager {
    private Map<String, User> users = new HashMap<>();

    public boolean register(String username, String password, String surname, String otherNames,
                            String email, String phoneNumber, String nin) {

        if (users.containsKey(username)) {
            System.out.println("Registration failed: Username already exists.");
            return false;
        }

        if (!isValidName(surname) || !isValidName(otherNames)) {
            System.out.println("Registration failed: Names must only contain letters.");
            return false;
        }

        if (!isValidEmail(email)) {
            System.out.println("Registration failed: Email must end in .com, .org, .net, .gov, .mil, .edu or .biz");
            return false;
        }

        if (!isValidPhone(phoneNumber)) {
            System.out.println("Registration failed: Phone number must be exactly 10 digits.");
            return false;
        }

        if (!isValidNIN(nin)) {
            System.out.println("Registration failed: NIN must be 14 characters, start with 2 letters, followed by 12 alphanumeric characters.");
            return false;
        }

        String hashedPassword = hashPassword(password);
        User newUser = new User(username, hashedPassword, surname, otherNames, email, phoneNumber, nin);
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

        System.out.println("Login successful. Welcome " + user.getFullName());
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

    private boolean isValidName(String name) {
        return name != null && name.matches("[A-Za-z]+");
    }

    private boolean isValidEmail(String email) {
        if (email == null) return false;
        return email.matches("^[\\w.-]+@[\\w.-]+\\.(com|org|net|gov|mil|edu|biz)$");
    }

    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    private boolean isValidNIN(String nin) {
        return nin != null && nin.matches("^[A-Za-z]{2}[A-Za-z0-9]{12}$");
    }

    public void printAllUsers() {
        for (Map.Entry<String, User> entry : users.entrySet()) {
            User user = entry.getValue();
            System.out.println("Username: " + user.getUsername() +
                    ", Full Name: " + user.getFullName() +
                    ", Email: " + user.getEmail() +
                    ", Phone: " + user.getPhoneNumber() +
                    ", NIN: " + user.getNin());
        }
    }
}
