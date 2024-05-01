package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Slot {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

    private int capacity;
    private int startDate;
    private int endDate;
    
    @ManyToOne
    Location location;
    
    @ManyToOne
    Event event;
    
    @ManyToMany
    Collection<User> participants;

	public Slot() {};

	public Slot(int capacity, int startDate, int endDate, Location location, Event event, Collection<User> participants) {
		this.capacity = capacity;
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
		this.event = event;
		this.participants = participants;
	}
    
	/** 
	 * @return int
	 */
	public int getId() {
		return id;
	}


	
	/** 
	 * @return int
	 */
	public int getCapacity() {
		return capacity;
	}

	
	/** 
	 * @param capacity
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	
	/** 
	 * @return Location
	 */
	public Location getLocation() {
		return location;
	}

	
	/** 
	 * @param location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	
	/** 
	 * @return int
	 */
	public int getStartDate() {
		return startDate;
	}

	
	/** 
	 * @param startDate
	 */
	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}

	
	/** 
	 * @return int
	 */
	public int getEndDate() {
		return endDate;
	}

	
	/** 
	 * @param endDate
	 */
	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}

	
	/** 
	 * @return Event
	 */
	public Event getEvent() {
		return event;
	}

	
	/** 
	 * @param event
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	
	/** 
	 * @return Collection<User>
	 */
	public Collection<User> getParticipants() {
		return participants;
	}

	
	/** 
	 * @param participants
	 */
	public void setParticipants(Collection<User> participants) {
		this.participants = participants;
	}

	
	/** 
	 * Add an user to the participant of the slot
	 * @param participant
	 */
	public void addParticipant(User participant) {
		// TODO throw error if capacity exeeded
		this.participants.add(participant);
	}
}
