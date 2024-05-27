package pack;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Personne {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String lastName;
	private String firstName;

	// OneToMany bidirectionnelle
	@OneToMany(mappedBy = "personne", fetch = FetchType.EAGER)
	@JsonManagedReference
	Collection<Adresse> addresses;

	// ManyToMany
	// @ManyToMany(mappedBy = "personnes", fetch=FetchType.EAGER)
	// Collection<Adresse> addresses;

	// OneToOne
	// @OneToOne(mappedBy="personne")
	// Adresse adresse;

	public Personne() {
	}

	/**
	 * Get the collection of addresses associated with this person.
	 *
	 * @return Collection<Adresse> The collection of addresses.
	 */
	public Collection<Adresse> getAddresses() {
		return addresses;
	}

	/**
	 * Set the collection of addresses associated with this person.
	 *
	 * @param addresses The collection of addresses.
	 */
	public void setAddresses(Collection<Adresse> addresses) {
		this.addresses = addresses;
	}

	/**
	 * Create a new Personne object with the specified last name and first name.
	 *
	 * @param lastName  The last name of the person.
	 * @param firstName The first name of the person.
	 */
	public Personne(String lastName, String firstName) {
		this.setLastName(lastName);
		this.setFirstName(firstName);
	}

	/**
	 * Get the full name of the person.
	 *
	 * @return String The full name of the person.
	 */
	public String getPersonne() {
		return lastName.concat(" " + firstName);
	}

	/**
	 * Get the last name of the person.
	 *
	 * @return String The last name of the person.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set the last name of the person.
	 *
	 * @param lastName The last name of the person.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Get the first name of the person.
	 *
	 * @return String The first name of the person.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Set the first name of the person.
	 *
	 * @param firstName The first name of the person.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get the ID of the person.
	 *
	 * @return int The ID of the person.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the ID of the person.
	 *
	 * @param id The ID of the person.
	 */
	public void setId(int id) {
		this.id = id;
	}
}
