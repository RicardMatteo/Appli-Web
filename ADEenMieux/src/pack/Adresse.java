package pack;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author nauchere
 *
 */
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
	 * @return Personne
	 */
	// ManyToMany
	// @ManyToMany
	// Collection<Personne> personnes;

	// OneToOne
	// @OneToOne
	// Personne personne;

	public Personne getPersonne() {
		return personne;
	}

	/**
	 * @param personne
	 */
	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public Adresse() {
	}

	public Adresse(String street, String city) {
		this.setStreet(street);
		this.setCity(city);
	}

	/**
	 * @param street
	 * @param city
	 */
	public void setAdresse(String street, String city) {
		this.setStreet(street);
		this.setCity(city);
	}

	/**
	 * @return String
	 */
	public String getAdresse() {
		return street.concat(" " + city);
	}

	/**
	 * @return String
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return String
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
}
