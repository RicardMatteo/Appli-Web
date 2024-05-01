package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Task {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;    

    private String name;
    
    private int deadline;
    
    @ManyToOne
    Agenda agenda;

	public Task() {};

	public Task(String name, int deadline, Collection<User> users) {
		this.name = name;
		this.deadline = deadline;
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
	 * @return agenda
	 */
	public Agenda getAgenda() {
		return agenda;
	}
	
	/**
	 * Choisit l'agenda auquel associer la tâche
	 * @param agenda
	 */
	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}
}
