package pack;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author nauchere
 *
 */
@Entity
public class Adresse {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String street;
	private String city;
	
	// OneToMany bidirectionnelle
	@ManyToOne
	Personne personne;
	
	// ManyToMany
	// @ManyToMany
	// Collection<Personne> personnes;
	
	// OneToOne
	// @OneToOne
	// Personne personne;
	
	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public Adresse() {}
	
	public Adresse(String street, String city) {
		this.setStreet(street);
		this.setCity(city);
	}
	
	public void setAdresse(String street, String city) {
		this.setStreet(street);
		this.setCity(city);
	}
	
	public String getAdresse() {
		return street.concat(" " + city);
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

