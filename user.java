
public class user{
    private string username;
    private string Password;
    private string email;

    public user(string username, string  hashedpassword, string email){
        this.username = username;
        this.Password = Password(password);
        this.email = email;
    }

    public string getusername();
    {
        return username;

    }

    public string checkpassword(string password)
    {
        return this.password.equals(Password);

    }


}
