package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private int deadline;

	@ManyToOne(fetch = FetchType.EAGER)
	Agenda agenda;

	public Task() {
	};

	public Task(String name, int deadline, Collection<User> users) {
		this.name = name;
		this.deadline = deadline;
	}

	/**
	 * Get the ID of the task.
	 *
	 * @return The ID of the task.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get the name of the task.
	 *
	 * @return The name of the task.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the task.
	 *
	 * @param name The name of the task.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the deadline of the task.
	 *
	 * @return The deadline of the task.
	 */
	public int getDeadline() {
		return deadline;
	}

	/**
	 * Set the deadline of the task.
	 *
	 * @param deadline The deadline of the task.
	 */
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

	/**
	 * Get the agenda associated with the task.
	 *
	 * @return The agenda associated with the task.
	 */
	public Agenda getAgenda() {
		return agenda;
	}

	/**
	 * Set the agenda associated with the task.
	 *
	 * @param agenda The agenda to associate with the task.
	 */
	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}
}
