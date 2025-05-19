package User;

public class User {
    private String username;
    private String hashedPassword;
    private String email;

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
    public boolean checkPassword(String pass){
        return BCrypt.checkpw(pass hashedPassword);
    }


}
