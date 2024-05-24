package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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

	public Agenda(String name) {
		this.name = name;
	}

	public Agenda(String name, Collection<Task> tasks, Collection<Slot> slots) {
		this.name = name;
		// TODO check if null
		this.tasks = tasks;
		this.slots = slots;
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
	 * @return Collection<Task>
	 */
	public Collection<Task> getTasks() {
		return tasks;
	}

	/**
	 * @param tasks
	 */
	public void setTasks(Collection<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * @return Collection<Slot>
	 */
	public Collection<Slot> getSlots() {
		return slots;
	}

	/**
	 * @param slots
	 */
	public void setSlots(Collection<Slot> slots) {
		this.slots = slots;
	}

	public void addSlot(Slot slot) {
		this.slots.add(slot);
	}

	/**
	 * @return User
	 */
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
