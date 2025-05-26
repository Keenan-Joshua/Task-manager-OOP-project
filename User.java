import org.mindrot.jbcrypt.BCrypt;
public class User {
    private final String username;
    private final String hashedPassword;
    private final String email;

    public User(String uname, String hashPass, String mail){
        username = uname;
        hashedPassword=hashPass;
        email=mail;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail(){
        return email;
    }
    protected String getHashedPassword(){
        return hashedPassword;
    }
    public boolean checkPassword(String pass){
        return BCrypt.checkpw(pass, hashedPassword);
    }


}
