package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String username;
    private String firstName;
    private String lastName;

    Collection<Group> groups;
    Collection<Agenda> agendas;

    private boolean isAdmin;

	
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
	public String getUsername() {
		return username;
	}

	
	/** 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	
	/** 
	 * @return String
	 */
	public String getFirstName() {
		return firstName;
	}

	
	/** 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	
	/** 
	 * @return String
	 */
	public String getLastName() {
		return lastName;
	}

	
	/** 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	/** 
	 * @return Collection<Group>
	 */
	public Collection<Group> getGroups() {
		return groups;
	}

	
	/** 
	 * @param groups
	 */
	public void setGroups(Collection<Group> groups) {
		this.groups = groups;
	}

	
	/** 
	 * @return Collection<Agenda>
	 */
	public Collection<Agenda> getAgendas() {
		return agendas;
	}

	
	/** 
	 * @param agendas
	 */
	public void setAgendas(Collection<Agenda> agendas) {
		this.agendas = agendas;
	}

	
	/** 
	 * @return boolean
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	
	/** 
	 * @param isAdmin
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}