package beight.wishlist.model;

import org.springframework.stereotype.Component;

@Component
public class UserProfile {
    private String username;
    private String password;


    public UserProfile(){
    }

    public UserProfile(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
