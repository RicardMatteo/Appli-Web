package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	Collection<User> guests;

	@ManyToMany(fetch = FetchType.EAGER)
	Collection<User> organisers;

	public Event() {
	};

	public Event(String name, Collection<User> guests, Collection<User> organisers) {
		this.name = name;
		// TODO check if null
		this.guests = guests;
		this.organisers = organisers;
	}

	/**
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name of the Event
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
	public Collection<User> getGuests() {
		return guests;
	}

	/**
	 * @param guests
	 */
	public void setGuests(Collection<User> guests) {
		this.guests = guests;
	}

	/**
	 * @return Collection<User>
	 */
	public Collection<User> getOrganisers() {
		return organisers;
	}

	/**
	 * @param organisers
	 */
	public void setOrganisers(Collection<User> organisers) {
		this.organisers = organisers;
	}

	/**
	 * @param guest
	 */
	public void addGuest(User guest) {
		guests.add(guest);
	}

	/**
	 * @param organiser
	 */
	public void addOrganiser(User organiser) {
		organisers.add(organiser);
	}
}
