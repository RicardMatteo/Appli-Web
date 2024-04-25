package pack;

import java.util.Collection;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Singleton
public class Facade {

	@PersistenceContext
	private EntityManager em;
	
	public Facade() {}
	
	public void ajoutPersonne(String nom, String prenom) {
		Personne personne = new Personne(nom, prenom);
		em.persist(personne);
	}
	
	public void ajoutAdresse(String rue, String ville) {
		Adresse adresse = new Adresse(rue, ville);
		em.persist(adresse);
	}
	
	public Collection<Personne> listePersonnes() {
		TypedQuery<Personne> req = em.createQuery("select c from Personne c", Personne.class);
		return req.getResultList();
	}
	
	public Collection<Adresse> listeAdresses() {
		TypedQuery<Adresse> req = em.createQuery("select c from Adresse c", Adresse.class);
		return req.getResultList();
	}
	
	public void associer(int personneId, int adresseId) {
		Personne pers = em.find(Personne.class, personneId);
		Adresse addr = em.find(Adresse.class, adresseId);
		// addr.setPersonne(pers); // OneToMany
		// addr.getPersonnes().add(pers); // ManyToMany
		addr.setPersonne(pers); // OneToOne
	}
}
