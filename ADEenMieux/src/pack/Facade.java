package pack;

import java.util.Collection;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Singleton
@Path("/")
public class Facade {

	@PersistenceContext
	private EntityManager em;

	public Facade() {}

	/**
	 * Add an user to the DB
	 * 
	 * @param username
	 * @param firstName
	 * @param lastName
	 * @param hashedPassword
	 */
	
	@POST
	@Path("/adduser")
	@Consumes({"application/json"})
	public void addUser(User user) { 
		em.persist(user);
	}
	

/**
		 * Add a task to the DB
		 * 
		 * @param name
		 * @param deadline
		 */
	/*
	 * public void addTask(String name, int deadline) { Task task = new Task(name,
	 * deadline, null); em.persist(task); }
	 * 
	 * 
	 *//**
		 * Add a time slot to the DB
		 * 
		 * @param capacity
		 * @param startDate
		 * @param endDate
		 * @param location
		 */
	/*
	 * public void addSlot(int capacity, int startDate, int endDate, Location
	 * location) { Slot slot = new Slot(capacity, startDate, endDate, location,
	 * null, null); em.persist(slot); }
	 * 
	 * 
	 *//**
		 * Add a location to the DB
		 * 
		 * @param name
		 * @param capacity
		 */
	/*
	 * public void addLocation(String name, int capacity) { Location location = new
	 * Location(name, capacity, null); em.persist(location); }
	 * 
	 * 
	 *//**
		 * Add a group to the DB
		 * 
		 * @param name
		 * @param users
		 */
	/*
	 * public void addGroup(String name, Collection<User> users) { Group group = new
	 * Group(name, users); em.persist(group); }
	 * 
	 * 
	 *//**
		 * Add an agenda to the DB
		 * 
		 * @param name
		 * @param tasks
		 * @param slots
		 */
	/*
	 * public void addAgenda(String name, Collection<Task> tasks, Collection<Slot>
	 * slots) { Agenda agenda = new Agenda(null, tasks, slots); em.persist(agenda);
	 * }
	 * 
	 * 
	 *//**
		 * Add an agenda to the DB
		 * 
		 * @param name
		 */
	/*
	 * public void addAgenda(String name) { addAgenda(name, null, null); }
	 * 
	 * 
	 *//**
		 * Add an event to the DB
		 * 
		 * @param name
		 * @param guests
		 * @param organisers
		 */
	/*
	 * public void addEvent(String name, Collection<User> guests, Collection<User>
	 * organisers) { Event event = new Event(name, guests, organisers);
	 * em.persist(event); }
	 * 
	 * 
	 *//**
		 * Add an event to the DB
		 * 
		 * @param name
		 */
	/*
	 * public void addEvent(String name) { addAgenda(name, null, null); }
	 * 
	 * 
	 *//**
		 * Add a guest to an event
		 * 
		 * @param guestId
		 * @param eventId
		 */
	/*
	 * public void addGuestInEvent(int guestId, int eventId) { User guest =
	 * em.find(User.class, guestId); Event event = em.find(Event.class, eventId);
	 * event.addGuest(guest); }
	 * 
	 * 
	 *//**
		 * Add an organiser to an event
		 * 
		 * @param orgaId
		 * @param eventId
		 */
	/*
	 * public void addOrganiserInEvent(int orgaId, int eventId) { User organiser =
	 * em.find(User.class, orgaId); Event event = em.find(Event.class, eventId);
	 * event.addOrganiser(organiser); }
	 * 
	 * 
	 *//**
		 * Get all the existing location
		 * 
		 * @return Collection<Location>
		 */
	/*
	 * public Collection<Location> getLocations() { TypedQuery<Location> req =
	 * em.createQuery("select l from Location l", Location.class); return
	 * req.getResultList(); }
	 * 
	 * 
	 *//**
		 * Add a participant to a slot
		 * 
		 * @param slotId
		 * @param userId
		 */
	/*
	 * public void addParticipantToSlot(int slotId, int userId) { User participant =
	 * em.find(User.class, userId); Slot slot = em.find(Slot.class, slotId);
	 * slot.addParticipant(participant); }
	 * 
	 * 
	 *//**
		 * Add an user to the group
		 * 
		 * @param groupId
		 * @param userId
		 */
	/*
	 * public void addUserToGroup(int groupId, int userId) { User user =
	 * em.find(User.class, userId); Group group = em.find(Group.class, groupId);
	 * group.addUser(user); }
	 * 
	 * 
	 *//**
		 * Remove an user of the group
		 * 
		 * @param groupId
		 * @param userId
		 *//*
			 * public void removeUserToGroup(int groupId, int userId) { User user =
			 * em.find(User.class, userId); Group group = em.find(Group.class, groupId);
			 * group.removeUser(user); }
			 */

	/*******************************************************
	 * 
	 * Pour tester le js
	 * 
	 ******************************************************/
	
	@POST
	@Path("/addperson")
	@Consumes({"application/json"})
	public void ajoutPersonne(Personne personne) {
		em.persist(personne);
	}
	
	
	/** 
	 * @param adresse
	 */
	@POST
	@Path("/addaddress")
	@Consumes({"application/json"})
	public void ajoutAdresse(Adresse adresse) {
		em.persist(adresse);
	}

	
	/** 
	 * @return Collection<Personne>
	 */
	@GET
	@Path("/listpersons")
	@Produces({"application/json"})
	public Collection<Personne> listePersonnes() {
		TypedQuery<Personne> req = em.createQuery("select c from Personne c", Personne.class);
		return req.getResultList();
	}
	
	
	/** 
	 * @return Collection<Adresse>
	 */
	@GET
	@Path("/listaddresses")
	@Produces({"application/json"})
	public Collection<Adresse> listeAdresses() {
		TypedQuery<Adresse> req = em.createQuery("select c from Adresse c", Adresse.class);
		return req.getResultList();
	}
	
	
	/** 
	 * @param association
	 */
	@POST
	@Path("/associate")
	@Consumes({"application/json"})
	public void associer(Association association) {
		System.out.println("On a l'association : "+association.getFirstId()+", "+association.getSecondId());
		Adresse addr = em.find(Adresse.class, association.getFirstId());
		Personne pers = em.find(Personne.class, association.getSecondId());
		addr.setPersonne(pers); // OneToMany
		// addr.getPersonnes().add(pers); // ManyToMany
		// addr.setPersonne(pers); // OneToOne
	}
}
