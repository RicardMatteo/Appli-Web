package pack;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Represents a place with a name and capacity.
 */
@Entity
public class Place {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private int capacity;

	public Place() {
	};

	/**
	 * Constructs a new Place object with the specified name and capacity.
	 *
	 * @param name     the name of the place
	 * @param capacity the capacity of the place
	 */
	public Place(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
	}

	/**
	 * Returns the ID of the place.
	 *
	 * @return the ID of the place
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the name of the place.
	 *
	 * @return the name of the place
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the place.
	 *
	 * @param name the name of the place
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the capacity of the place.
	 *
	 * @return the capacity of the place
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Sets the capacity of the place.
	 *
	 * @param capacity the capacity of the place
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}
