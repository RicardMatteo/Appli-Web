package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * @author nauchere
 *
 */
@Entity
public class Adresse {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String rue;
	private String ville;
	
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
	
	public Adresse(String rue, String ville) {
		this.setRue(rue);
		this.setVille(ville);
	}
	
	public void setAdresse(String rue, String ville) {
		this.setRue(rue);
		this.setVille(ville);
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}
	
	public String getAdresse() {
		return rue.concat(" " + ville);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}

