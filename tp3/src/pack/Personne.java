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
	
	private String nom;
	private String prenom;
	
	// OneToMany bidirectionnelle
	@OneToMany(mappedBy = "personne", fetch=FetchType.EAGER)
	Collection<Adresse> adresses;
	
	// ManyToMany
//	@ManyToMany(mappedBy = "personnes", fetch=FetchType.EAGER)
//	Collection<Adresse> adresses;
	
	// OneToOne
	// @OneToOne(mappedBy="personne")
	// Adresse adresse;

	
	public Personne() {}
	
	public Collection<Adresse> getAdresses() {
		return adresses;
	}

	public void setAdresses(Collection<Adresse> adresses) {
		this.adresses = adresses;
	}

	public Personne(String nom, String prenom) {
		this.setNom(nom);
		this.setPrenom(prenom);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getPersonne() {
		return nom.concat(" " + prenom);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
