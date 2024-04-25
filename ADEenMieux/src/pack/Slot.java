package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Slot {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

    private int capacity;
    private Location location;
    private int startDate;
    private int endDate;
    private Event event;

    Collection<User> participants;

}
