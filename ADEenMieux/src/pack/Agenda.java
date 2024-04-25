package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Agenda {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

    private String name;
    Collection<Task> tasks;
    Collection<Slot> slots;
    
    public Agenda() {};
    
    public Agenda(String name) {
        this.name = name; 
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
}
