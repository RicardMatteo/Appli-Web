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

@Entity
public class Personne {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String lastName;
	private String firstName;
	
	// OneToMany bidirectionnelle
	@OneToMany(mappedBy = "personne", fetch=FetchType.EAGER)
	Collection<Adresse> addresses;
	
	// ManyToMany
	// @ManyToMany(mappedBy = "personnes", fetch=FetchType.EAGER)
	// Collection<Adresse> addresses;
	
	// OneToOne
	// @OneToOne(mappedBy="personne")
	// Adresse adresse;

	
	public Personne() {}
	
	public Collection<Adresse> getAddresses() {
		return addresses;
	}

	public void setAddresses(Collection<Adresse> addresses) {
		this.addresses = addresses;
	}

	public Personne(String lastName, String firstName) {
		this.setLastName(lastName);
		this.setFirstName(firstName);
	}
	
	public String getPersonne() {
		return lastName.concat(" " + firstName);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
