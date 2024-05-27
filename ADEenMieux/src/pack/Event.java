package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@ManyToMany()
	Collection<User> guests;

	@OneToMany(mappedBy = "event_slot", fetch = FetchType.EAGER)
	Collection<Slot> slots;

	/**
	 * Default constructor for the Event class.
	 */
	public Event() {
	};

	/**
	 * Constructor for the Event class with a name parameter.
	 *
	 * @param name the name of the event
	 */
	public Event(String name) {
		setName(name);
	}

	/**
	 * Get the collection of slots associated with the event.
	 *
	 * @return the collection of slots
	 */
	public Collection<Slot> getSlots() {
		return slots;
	}

	/**
	 * Set the collection of slots associated with the event.
	 *
	 * @param slots the collection of slots
	 */
	public void setSlots(Collection<Slot> slots) {
		this.slots = slots;
	}

	/**
	 * Get the ID of the event.
	 *
	 * @return the ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get the name of the event.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the event.
	 *
	 * @param name the name of the event
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the collection of guests associated with the event.
	 *
	 * @return the collection of guests
	 */
	public Collection<User> getGuests() {
		return this.guests;
	}

	/**
	 * Set the collection of guests associated with the event.
	 *
	 * @param guests the collection of guests
	 */
	public void setGuests(Collection<User> guests) {
		this.guests = guests;
	}

	/**
	 * Add a guest to the event.
	 *
	 * @param guest the guest to add
	 */
	public void addGuest(User guest) {
		guests.add(guest);
	}
}
