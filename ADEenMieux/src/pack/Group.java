package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Group {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String name;

    Collection<User> users;
    
    public Group() {};
	
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
