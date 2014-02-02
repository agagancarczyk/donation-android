package app.models;

public class Admin extends User
{
    public Long   id;
    public String username;
    
    public Admin()
    {}
    
    public String toString()
    {
      return username + ", " + password;
    }
}
