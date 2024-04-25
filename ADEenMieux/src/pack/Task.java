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

	
	/** 
	 * @return int
	 */
	public int getId() {
		return id;
	}

	
	/** 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	
	/** 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	
	/** 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/** 
	 * @return int
	 */
	public int getDeadline() {
		return deadline;
	}

	
	/** 
	 * @param deadline
	 */
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

	
	/** 
	 * @return Collection<User>
	 */
	public Collection<User> getUsers() {
		return users;
	}

	
	/** 
	 * @param users
	 */
	public void setUsers(Collection<User> users) {
		this.users = users;
	}
}
