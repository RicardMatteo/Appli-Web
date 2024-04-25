package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;    

    private String name;
    
    private int deadline;

    Collection<User> users;


}
