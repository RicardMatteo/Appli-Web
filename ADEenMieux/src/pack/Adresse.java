package pack;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Adresse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String street;
	private String city;

	// OneToMany bidirectionnelle
	@ManyToOne
	@JsonBackReference
	Personne personne;

	/**
	 * Get the associated Personne object.
	 *
	 * @return The associated Personne object.
	 */
	public Personne getPersonne() {
		return personne;
	}

	/**
	 * Set the associated Personne object.
	 *
	 * @param personne The Personne object to associate with this Adresse.
	 */
	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	/**
	 * Default constructor for Adresse class.
	 */
	public Adresse() {
	}

	/**
	 * Constructor for Adresse class.
	 *
	 * @param street The street of the address.
	 * @param city   The city of the address.
	 */
	public Adresse(String street, String city) {
		this.setStreet(street);
		this.setCity(city);
	}

	/**
	 * Set the street and city of the address.
	 *
	 * @param street The street of the address.
	 * @param city   The city of the address.
	 */
	public void setAdresse(String street, String city) {
		this.setStreet(street);
		this.setCity(city);
	}

	/**
	 * Get the full address.
	 *
	 * @return The full address.
	 */
	public String getAdresse() {
		return street.concat(" " + city);
	}

	/**
	 * Get the street of the address.
	 *
	 * @return The street of the address.
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Set the street of the address.
	 *
	 * @param street The street of the address.
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Get the city of the address.
	 *
	 * @return The city of the address.
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Set the city of the address.
	 *
	 * @param city The city of the address.
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Get the ID of the address.
	 *
	 * @return The ID of the address.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the ID of the address.
	 *
	 * @param id The ID of the address.
	 */
	public void setId(int id) {
		this.id = id;
	}
}
