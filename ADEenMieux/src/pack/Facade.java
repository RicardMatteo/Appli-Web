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

	
	/**
	 * Add an event to the DB 
	 * @param name
	 * @param guests
	 * @param organisers
	 */
	public void addEvent(String name, Collection<User> guests, Collection<User> organisers) {
		Event event = new Event(name, guests, organisers);
		em.persist(event);
	}

	
	/**
	 * Add an event to the DB 
	 * @param name
	 */
	public void addEvent(String name) {
		addAgenda(name, null, null);
	}

	
	/** 
	 * Add a guest to an event
	 * @param guestId
	 * @param eventId
	 */
	public void addGuestInEvent(int guestId, int eventId) {
		User guest = em.find(User.class, guestId);
		Event event = em.find(Event.class, eventId);
		event.addGuest(guest);
	}

	
	/** 
	 * Add an organiser to an event
	 * @param orgaId
	 * @param eventId
	 */
	public void addOrganiserInEvent(int orgaId, int eventId) {
		User organiser = em.find(User.class, orgaId);
		Event event = em.find(Event.class, eventId);
		event.addOrganiser(organiser);
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

}
