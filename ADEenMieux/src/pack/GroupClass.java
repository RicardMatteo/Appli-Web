package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class GroupClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<User> users;

	public GroupClass() {
	};

	/**
	 * Constructs a new GroupClass object with the specified name.
	 * 
	 * @param name the name of the group class
	 */
	public GroupClass(String name) {
		this.name = name;
	}

	/**
	 * Constructs a new GroupClass object with the specified name and users.
	 *
	 * @param name  the name of the group
	 * @param users the collection of users in the group
	 */
	public GroupClass(String name, Collection<User> users) {
		this.name = name;
		this.users = users;
	}

	/**
	 * Get the ID of the group.
	 *
	 * @return The ID of the group.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get the name of the group.
	 *
	 * @return The name of the group.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the group.
	 *
	 * @param name The name of the group.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the users in the group.
	 *
	 * @return The users in the group.
	 */
	public Collection<User> getUsers() {
		return users;
	}

	/**
	 * Set the users in the group.
	 *
	 * @param users The users to set in the group.
	 */
	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	/**
	 * Add a user to the group.
	 *
	 * @param user The user to add to the group.
	 */
	public void addUser(User user) {
		this.users.add(user);
	}

	/**
	 * Remove a user from the group.
	 *
	 * @param user The user to remove from the group.
	 */
	public void removeUser(User user) {
		this.users.remove(user);
	}
}
