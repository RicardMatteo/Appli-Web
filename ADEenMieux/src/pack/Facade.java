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
	
	
	/**
	 * Add an user to the DB 
	 * @param username
	 * @param firstName
	 * @param lastName
	 * @param isAdmin
	 */
	public void addUser(String username, String firstName, String lastName, boolean isAdmin) {
		User user = new User(username, firstName, lastName, null, null, isAdmin);
		em.persist(user);
	}

	
	/** 
	 * Add a task to the DB
	 * @param name
	 * @param deadline
	 */
	public void addTask(String name, int deadline) {
		Task task = new Task(name, deadline, null);
		em.persist(task);
	}

	
	/** 
	 * Add a time slot to the DB
	 * @param capacity
	 * @param startDate
	 * @param endDate
	 * @param location
	 */
	public void addSlot(int capacity, int startDate, int endDate, Location location) {
		Slot slot = new Slot(capacity, startDate, endDate, location, null, null);
		em.persist(slot);
	}


	
	/** 
	 * Add a location to the DB
	 * @param name
	 * @param capacity
	 */
	public void addLocation(String name, int capacity) {
		Location location = new Location(name, capacity, null);
		em.persist(location);
	}

	
	
	/** 
	 * Add a group to the DB
	 * @param name
	 * @param users
	 */
	public void addGroup(String name, Collection<User> users) {
		Group group = new Group(name, users);
		em.persist(group);
	}	

	
	
	/** 
	 * Add an agenda to the DB
	 * @param name
	 * @param tasks
	 * @param slots
	 */
	public void addAgenda(String name, Collection<Task> tasks, Collection<Slot> slots) {
		Agenda agenda = new Agenda(null, tasks, slots)
		em.persist(agenda);
	}

	
	
	/** 
	 * Add an agenda to the DB
	 * @param name
	 */
	public void addAgenda(String name) {
		addAgenda(name, null, null);
	}

	/*
	
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
	*/
}
