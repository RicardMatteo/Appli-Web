package pack;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Random;

@Entity
public class ConnexionToken {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String token;

    @ManyToOne
    private User user_token;

    public ConnexionToken() {};

    public ConnexionToken(String token, User user_token) {
        this.token = token;
        this.user_token = user_token;
    }

    public String generateToken() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder token = new StringBuilder();
        Random rnd = new Random();
        while (token.length() < 50) { // length of the random string.
            int index = (int) (rnd.nextFloat() * chars.length());
            token.append(chars.charAt(index));
        }
        String finalToken = token.toString();
        return finalToken;
        
    }

    /** 
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * @return String
     */
    public String getToken() {
        return token;
    }

    /**
     * @return User
     */
    public User getUserToken() {
        return user_token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @param user_token
     */
    public void setUserToken(User user_token) {
        this.user_token = user_token;
    }
    
}
