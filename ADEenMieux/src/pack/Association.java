package pack;

import java.io.Serializable;

/**
 * The Association class represents a relationship between two entities.
 * It stores the IDs of the first and second entities.
 */
public class Association implements Serializable {

	/**
	 * The serial version UID for serialization/deserialization.
	 */
	private static final long serialVersionUID = -5654173171216288003L;

	/**
	 * The ID of the first entity.
	 */
	int firstId;

	/**
	 * The ID of the second entity.
	 */
	int secondId;

	/**
	 * Default constructor for the Association class.
	 */
	public Association() {
	}

	/**
	 * Constructs an Association object with the specified IDs.
	 * 
	 * @param firstId  The ID of the first entity.
	 * @param secondId The ID of the second entity.
	 */
	public Association(int firstId, int secondId) {
		this.firstId = firstId;
		this.secondId = secondId;
	}

	/**
	 * Retrieves the ID of the second entity.
	 * 
	 * @return The ID of the second entity.
	 */
	public int getSecondId() {
		return secondId;
	}

	/**
	 * Sets the ID of the second entity.
	 * 
	 * @param secondId The ID of the second entity.
	 */
	public void setSecondId(int secondId) {
		this.secondId = secondId;
	}

	/**
	 * Retrieves the ID of the first entity.
	 * 
	 * @return The ID of the first entity.
	 */
	public int getFirstId() {
		return firstId;
	}

	/**
	 * Sets the ID of the first entity.
	 * 
	 * @param firstId The ID of the first entity.
	 */
	public void setFirstId(int firstId) {
		this.firstId = firstId;
	}
}
