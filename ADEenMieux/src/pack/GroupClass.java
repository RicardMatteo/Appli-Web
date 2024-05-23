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

	public GroupClass(String name) {
		this.name = name;
	}

	public GroupClass(String name, Collection<User> users) {
		this.name = name;
		this.users = users;
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

	/**
	 * Add an user to the group
	 * 
	 * @param user
	 */
	public void addUser(User user) {
		this.users.add(user);
	}

	/**
	 * Remove an user of the group
	 * 
	 * @param user
	 */
	public void removeUser(User user) {
		this.users.remove(user);
	}
}
