package pack;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Random;

@Entity
public class ConnexionToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user_token;

    public ConnexionToken() {
    };

    /**
     * Constructs a new ConnexionToken object with the specified token and user.
     * 
     * @param token      the connection token
     * @param user_token the associated user
     */
    public ConnexionToken(String token, User user_token) {
        this.token = token;
        this.user_token = user_token;
    }

    /**
     * Generates a random token string.
     *
     * @return The generated token string.
     */
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
     * Returns the ID of the ConnexionToken.
     *
     * @return The ID of the ConnexionToken.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the token string.
     *
     * @return The token string.
     */
    public String getToken() {
        return token;
    }

    /**
     * Returns the user associated with the token.
     *
     * @return The user associated with the token.
     */
    public User getUserToken() {
        return user_token;
    }

    /**
     * Sets the token string.
     *
     * @param token The token string to set.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Sets the user associated with the token.
     *
     * @param user_token The user to associate with the token.
     */
    public void setUserToken(User user_token) {
        this.user_token = user_token;
    }
}
