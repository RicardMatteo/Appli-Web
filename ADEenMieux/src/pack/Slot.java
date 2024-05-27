package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * Represents a time slot for an event.
 */
@Entity
public class Slot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int capacity;
	private long startDate;
	private long endDate;

	@ManyToOne(fetch = FetchType.EAGER)
	Place location;

	@ManyToOne(fetch = FetchType.EAGER)
	Event event_slot;

	@ManyToMany(fetch = FetchType.EAGER)
	Collection<User> participants;

	/**
	 * The Slot class represents a time slot in a schedule.
	 * It is used to store information about a specific time period.
	 */
	public Slot() {
	};

	/**
	 * Constructs a new Slot object with the specified capacity, start date, and end
	 * date.
	 *
	 * @param capacity  the maximum number of items that can be scheduled in this
	 *                  time slot
	 * @param startDate the start date of the time slot in milliseconds since the
	 *                  epoch
	 * @param endDate   the end date of the time slot in milliseconds since the
	 *                  epoch
	 */
	public Slot(int capacity, long startDate, long endDate) {
		this.capacity = capacity;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Constructs a new Slot object with the specified parameters.
	 *
	 * @param capacity     the maximum number of participants allowed in the slot
	 * @param startDate    the start date of the slot
	 * @param endDate      the end date of the slot
	 * @param location     the location of the slot
	 * @param event_slot   the event associated with the slot
	 * @param participants the collection of participants in the slot
	 */
	public Slot(int capacity, long startDate, long endDate, Place location, Event event_slot,
			Collection<User> participants) {
		this.capacity = capacity;
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
		this.event_slot = event_slot;
		this.participants = participants;
	}

	/**
	 * Get the ID of the slot.
	 *
	 * @return The ID of the slot.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get the capacity of the slot.
	 *
	 * @return The capacity of the slot.
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Set the capacity of the slot.
	 *
	 * @param capacity The capacity of the slot.
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Get the location of the slot.
	 *
	 * @return The location of the slot.
	 */
	public Place getLocation() {
		return location;
	}

	/**
	 * Set the location of the slot.
	 *
	 * @param location The location of the slot.
	 */
	public void setLocation(Place location) {
		this.location = location;
	}

	/**
	 * Get the start date of the slot.
	 *
	 * @return The start date of the slot.
	 */
	public long getStartDate() {
		return startDate;
	}

	/**
	 * Set the start date of the slot.
	 *
	 * @param startDate The start date of the slot.
	 */
	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	/**
	 * Get the end date of the slot.
	 *
	 * @return The end date of the slot.
	 */
	public long getEndDate() {
		return endDate;
	}

	/**
	 * Set the end date of the slot.
	 *
	 * @param endDate The end date of the slot.
	 */
	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	/**
	 * Get the event associated with the slot.
	 *
	 * @return The event associated with the slot.
	 */
	public Event getEvent() {
		return event_slot;
	}

	/**
	 * Set the event associated with the slot.
	 *
	 * @param event The event associated with the slot.
	 */
	public void setEvent(Event event_slot) {
		this.event_slot = event_slot;
	}

	/**
	 * Get the participants of the slot.
	 *
	 * @return The participants of the slot.
	 */
	public Collection<User> getParticipants() {
		return participants;
	}

	/**
	 * Set the participants of the slot.
	 *
	 * @param participants The participants of the slot.
	 */
	public void setParticipants(Collection<User> participants) {
		this.participants = participants;
	}

	/**
	 * Add a participant to the slot.
	 *
	 * @param participant The participant to add.
	 */
	public void addParticipant(User participant) {
		// TODO throw error if capacity exceeded
		this.participants.add(participant);
	}
}
