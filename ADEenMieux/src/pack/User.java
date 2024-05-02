package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class User {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String username;
    private String firstName;
    private String lastName;
    
//    @ManyToMany(mappedBy = "users") // Fetch.TYPE ?
//    Collection<Group> groups;
    
    @OneToMany(mappedBy = "user") // FetchType ?
    Collection<Agenda> agendas;
    
    @ManyToMany(mappedBy = "guests")
    Collection<Event> signed_up_events;
    
    @ManyToMany(mappedBy = "organisers")
    Collection<Event> organised_events;

    private boolean isAdmin;

	public User() {};

	public User(String username, String firstName, String lastName, Collection<Group> groups, Collection<Agenda> agendas, boolean isAdmin) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
//		this.groups = groups;
		this.agendas = agendas;
		this.isAdmin = isAdmin;
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
//	public Collection<Group> getGroups() {
//		return groups;
//	}

	
	/** 
	 * @param groups
	 */
//	public void setGroups(Collection<Group> groups) {
//		this.groups = groups;
//	}

	
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
	
	
	/** 
	 * @return Collection<Event>
	 */
	public Collection<Event> getSigned_up_events() {
		return signed_up_events;
	}

	
	/** 
	 * @param signed_up_events
	 */
	public void setSigned_up_events(Collection<Event> signed_up_events) {
		this.signed_up_events = signed_up_events;
	}

	
	/** 
	 * @return Collection<Event>
	 */
	public Collection<Event> getOrganised_events() {
		return organised_events;
	}

	
	/** 
	 * @param organised_events
	 */
	public void setOrganised_events(Collection<Event> organised_events) {
		this.organised_events = organised_events;
	}
}