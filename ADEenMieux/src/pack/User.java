package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;
	private String firstName;
	private String lastName;
	private String hashedPassword;

	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
	private Collection<GroupClass> groups;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	Collection<Agenda> agendas;

	@ManyToMany(mappedBy = "guests")
	Collection<Event> signed_up_events;

	// @ManyToMany(mappedBy = "organisers", fetch = FetchType.EAGER)
	// Collection<Event> organised_events;

	@OneToMany(mappedBy = "user_token", fetch = FetchType.EAGER)
	Collection<ConnexionToken> tokens;

	@ManyToMany(mappedBy = "participants", fetch = FetchType.EAGER)
	Collection<Slot> slots_user;

	private boolean isAdmin;

	public User() {
	};

	public User(String username, String firstName, String lastName, Collection<GroupClass> groups,
			Collection<Agenda> agendas, boolean isAdmin) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.groups = groups;
		this.agendas = agendas;
		this.isAdmin = isAdmin;
	}

	/**
	 * Get the ID of the user.
	 *
	 * @return The ID of the user.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get the username of the user.
	 *
	 * @return The username of the user.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the username of the user.
	 *
	 * @param username The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get the first name of the user.
	 *
	 * @return The first name of the user.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Set the first name of the user.
	 *
	 * @param firstName The first name to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get the last name of the user.
	 *
	 * @return The last name of the user.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set the last name of the user.
	 *
	 * @param lastName The last name to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Get the groups that the user belongs to.
	 *
	 * @return The groups that the user belongs to.
	 */
	public Collection<GroupClass> getGroups() {
		return groups;
	}

	/**
	 * Set the groups that the user belongs to.
	 *
	 * @param groups The groups to set.
	 */
	public void setGroups(Collection<GroupClass> groups) {
		this.groups = groups;
	}

	/**
	 * Get the agendas of the user.
	 *
	 * @return The agendas of the user.
	 */
	public Collection<Agenda> getAgendas() {
		return agendas;
	}

	/**
	 * Set the agendas of the user.
	 *
	 * @param agendas The agendas to set.
	 */
	public void setAgendas(Collection<Agenda> agendas) {
		this.agendas = agendas;
	}

	/**
	 * Check if the user is an admin.
	 *
	 * @return true if the user is an admin, false otherwise.
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * Set whether the user is an admin or not.
	 *
	 * @param isAdmin true if the user is an admin, false otherwise.
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * Get the events that the user has signed up for.
	 *
	 * @return The events that the user has signed up for.
	 */
	public Collection<Event> getSigned_up_events() {
		return signed_up_events;
	}

	/**
	 * Set the events that the user has signed up for.
	 *
	 * @param signed_up_events The events to set.
	 */
	public void setSigned_up_events(Collection<Event> signed_up_events) {
		this.signed_up_events = signed_up_events;
	}

	/**
	 * Get the hashed password of the user.
	 *
	 * @return The hashed password of the user.
	 */
	public String getHashedPassword() {
		return hashedPassword;
	}

	/**
	 * Set the hashed password of the user.
	 *
	 * @param hashedPassword The hashed password to set.
	 */
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	/**
	 * Get the slots of the user.
	 *
	 * @return The slots of the user.
	 */
	public Collection<Slot> getSlots_user() {
		return slots_user;
	}

	/**
	 * Set the slots of the user.
	 *
	 * @param slots_user The slots to set.
	 */
	public void setSlots_user(Collection<Slot> slots_user) {
		this.slots_user = slots_user;
	}
}