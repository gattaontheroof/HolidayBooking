

/**
 *
 * @author agata
 */

public abstract class User implements Authenticatable{
    protected String id;
    protected String password;
   
    public User(String username,String password) {
        this.id = username;
        this.password = password;
    }
    
    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
    
    @Override
    public boolean login(String password) {
        return this.password.equals(password);
    }
}
