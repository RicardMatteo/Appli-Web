package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * The Agenda class represents a user's agenda.
 * It contains information about the agenda's ID, name, tasks, slots, and user.
 */
@Entity
public class Agenda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@OneToMany(mappedBy = "agenda", fetch = FetchType.EAGER)
	Collection<Task> tasks;

	@OneToMany(fetch = FetchType.EAGER)
	Collection<Slot> slots;

	@ManyToOne(fetch = FetchType.EAGER)
	User user;

	public Agenda() {
	};

	/**
	 * Constructs a new Agenda object with the specified name.
	 *
	 * @param name the name of the agenda
	 */
	public Agenda(String name) {
		this.name = name;
	}

	/**
	 * Constructs a new Agenda object with the specified name, tasks, and slots.
	 *
	 * @param name  the name of the agenda
	 * @param tasks the collection of tasks associated with the agenda
	 * @param slots the collection of slots associated with the agenda
	 */
	public Agenda(String name, Collection<Task> tasks, Collection<Slot> slots) {
		this.name = name;
		// TODO check if null
		this.tasks = tasks;
		this.slots = slots;
	}

	/**
	 * Returns the ID of the agenda.
	 *
	 * @return the ID of the agenda
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the name of the agenda.
	 *
	 * @return the name of the agenda
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the agenda.
	 *
	 * @param name the name of the agenda
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the collection of tasks associated with the agenda.
	 *
	 * @return the collection of tasks associated with the agenda
	 */
	public Collection<Task> getTasks() {
		return tasks;
	}

	/**
	 * Sets the collection of tasks associated with the agenda.
	 *
	 * @param tasks the collection of tasks associated with the agenda
	 */
	public void setTasks(Collection<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Returns the collection of slots associated with the agenda.
	 *
	 * @return the collection of slots associated with the agenda
	 */
	public Collection<Slot> getSlots() {
		return slots;
	}

	/**
	 * Sets the collection of slots associated with the agenda.
	 *
	 * @param slots the collection of slots associated with the agenda
	 */
	public void setSlots(Collection<Slot> slots) {
		this.slots = slots;
	}

	/**
	 * Adds a slot to the collection of slots associated with the agenda.
	 *
	 * @param slot the slot to be added
	 */
	public void addSlot(Slot slot) {
		this.slots.add(slot);
	}

	/**
	 * Returns the user associated with the agenda.
	 *
	 * @return the user associated with the agenda
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user associated with the agenda.
	 *
	 * @param user the user associated with the agenda
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
