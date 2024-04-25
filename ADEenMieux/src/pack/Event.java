package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Event {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

    private String name;
    Collection<User> guests;
    Collection<User> organisers;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Collection<User> getGuests() {
		return guests;
	}
	public void setGuests(Collection<User> guests) {
		this.guests = guests;
	}
	public Collection<User> getOrganisers() {
		return organisers;
	}
	public void setOrganisers(Collection<User> organisers) {
		this.organisers = organisers;
	}
}
