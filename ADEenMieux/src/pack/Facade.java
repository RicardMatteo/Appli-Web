package pack;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/")
public class Facade {

	@PersistenceContext
	private EntityManager em;

	public Facade() {
	}

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
	@Consumes({ "application/json" })
	public void addUser(User user) {
		em.persist(user);
	}

	public static class LoginInfo {
		private String username;
		private String hashedPassword;

		// Constructor
		public LoginInfo() {
		}

		public LoginInfo(String username, String hashedPassword) {
			this.username = username;
			this.hashedPassword = hashedPassword;
		}

		// Getters and setters
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public static String hashPassword(String Password) {
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance("SHA-256");
				byte[] encodedhash = digest.digest(Password.getBytes(StandardCharsets.UTF_8));
				String encodedHashString = bytesToHex(encodedhash);
				return encodedHashString;
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return "";
		}

		private static String bytesToHex(byte[] encodedhash) {
			StringBuilder sb = new StringBuilder();
			for (byte b : encodedhash) {
				sb.append(String.format("%02x", b));
			}
			String result = sb.toString();
			return result;
		}

		public String getHashedPassword() {
			return hashedPassword;
		}

		public void setHashedPassword(String hashedPassword) {
			this.hashedPassword = hashedPassword;
		}
	}

	/**
	 * Try to log an user
	 * 
	 * @param loginInfo
	 */
	@POST
	@Path("/login")
	@Consumes({ "application/json" })
	public Response login(LoginInfo loginInfo) {
		// look in the DB for the user with the given username with and sql query
		// SELECT * FROM User WHERE username = loginInfo.username
		// if the user is found, check if the password is correct
		// if the password is correct, generate a token and store it in the DB
		// return the token
		TypedQuery<User> req = em.createQuery("select u from User u where u.username = :username", User.class);
		req.setParameter("username", loginInfo.getUsername());
		System.out.println("select u from User u where u.username = :username");
		try {
			User user = req.getSingleResult();
			System.out.println("User found : " + user.getUsername());
			if (user != null) {
				if (user.getHashedPassword().equals(loginInfo.getHashedPassword())) {
					ConnexionToken token = new ConnexionToken();
					em.persist(token);
					token.setUserToken(user);
					token.setToken(token.generateToken());

					// return the token in the response data
					return Response.ok().header("AuthToken", token.getToken()).build();
				}
			}
		} catch (Exception e) {
			System.out.println("User not found");
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		System.out.println("Password incorrect");
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	public static class Cookie {
		private String cookie;

		// Constructor
		public Cookie() {
		}

		public Cookie(String cookie) {
			this.cookie = cookie;
		}

		public String getCookie() {
			return cookie;
		}

		public void setCookie(String cookie) {
			this.cookie = cookie;
		}
	}

	/**
	 * Get the current user from the cookie
	 * 
	 * @return Collection<User>
	 */
	@POST
	@Path("/getuser")
	@Consumes({ "application/json" })
	public Response getUser(Cookie cookie) {
		TypedQuery<ConnexionToken> req = em.createQuery("select c from ConnexionToken c where c.token = :token",
				ConnexionToken.class);
		req.setParameter("token", cookie.getCookie());
		try {
			ConnexionToken token = req.getSingleResult();
			if (token != null) {
				return Response.ok().header("username", token.getUserToken().getUsername()).build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	public static class GroupName {
		private String groupName;

		// Constructor
		public GroupName() {
		}

		public GroupName(String groupName) {
			this.groupName = groupName;
		}

		public String getGroupName() {
			return groupName;
		}

		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}
	}

	/**
	 * create an empty group
	 * 
	 * @param groupName
	 */
	@POST
	@Path("/creategroup")
	@Consumes({ "application/json" })
	public void createGroup(GroupName g) {
		GroupClass group = new GroupClass();
		group.setName(g.getGroupName());
		em.persist(group);
	}

	public static class SmallGroup implements Serializable {
		private Integer groupId;
		private String groupName;

		// Constructor
		public SmallGroup() {
		}

		public SmallGroup(Integer groupId, String groupName) {
			this.groupId = groupId;
			this.groupName = groupName;
		}

		public Integer getGroupId() {
			return groupId;
		}

		public void setGroupId(Integer groupId) {
			this.groupId = groupId;
		}

		public String getGroupName() {
			return groupName;
		}

		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}
	}

	/**
	 * List all the groups
	 * 
	 * @param Cookie
	 */
	@GET
	@Path("/listgroups")
	@Produces({ "application/json" })
	public Collection<SmallGroup> listGroups(@HeaderParam("cookie") String cookie) {
		System.out.println("Cookie : " + cookie);
		String[] cookieParts = cookie.split("=", 2);
		String cookieValue = cookieParts[1];
		// check if the user is logged in
		TypedQuery<ConnexionToken> req = em.createQuery("select c from ConnexionToken c where c.token = :token",
				ConnexionToken.class);
		req.setParameter("token", cookieValue);
		try {
			ConnexionToken token = req.getSingleResult();
			if (token != null) {
				List<SmallGroup> groups = new ArrayList<>();
				// get all the groups from the DB
				TypedQuery<GroupClass> reqGroups = em.createQuery("select g from GroupClass g", GroupClass.class);
				// return the list of groups as a json object that we build as a string
				// beforehand, and then put it in the groups header
				for (GroupClass group : reqGroups.getResultList()) {
					System.out.println(group.getName());
					groups.add(new SmallGroup(group.getId(), group.getName()));
				}
				return groups;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("User not found");
			return Collections.emptyList();
		}
		System.out.println("Password incorrect");
		return Collections.emptyList();
	}

	public static class SmallUser implements Serializable {
		private Integer userId;
		private String username;
		private String firstName;
		private String lastName;
		private String hashedPassword;
		private boolean isAdmin;

		public SmallUser() {
		}

		public SmallUser(Integer userId, String username) {
			this.userId = userId;
			this.username = username;
		}

		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getHashedPassword() {
			return hashedPassword;
		}

		public void setHashedPassword(String hashedPassword) {
			this.hashedPassword = hashedPassword;
		}

		public boolean isAdmin() {
			return isAdmin;
		}

		public void setAdmin(boolean isAdmin) {
			this.isAdmin = isAdmin;
		}
	}

	/**
	 * List all the users
	 * 
	 * @param cookie
	 */
	@GET
	@Path("/listusers")
	@Produces({ "application/json" })
	public Collection<SmallUser> listUsers(@HeaderParam("cookie") String cookie) {
		System.out.println("Cookie : " + cookie);
		String[] cookieParts = cookie.split("=", 2);
		String cookieValue = cookieParts[1];
		// check if the user is logged in
		TypedQuery<ConnexionToken> req = em.createQuery("select c from ConnexionToken c where c.token = :token",
				ConnexionToken.class);
		req.setParameter("token", cookieValue);
		try {
			ConnexionToken token = req.getSingleResult();
			if (token != null) {
				List<SmallUser> users = new ArrayList<>();
				// get all the groups from the DB
				TypedQuery<User> reqUsers = em.createQuery("select u from User u", User.class);
				// return the list of groups as a json object that we build as a string
				// beforehand, and then put it in the groups header
				for (User user : reqUsers.getResultList()) {
					System.out.println(user.getUsername());
					users.add(new SmallUser(user.getId(), user.getUsername()));
				}
				return users;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No users found");
			return Collections.emptyList();
		}
		System.out.println("Not logged in");
		return Collections.emptyList();
	}

	public static class AssociationGUID {
		private Integer selectedGroup;
		private Collection<Integer> selectedUsers;

		// Constructor
		public AssociationGUID() {
		}

		public AssociationGUID(Integer selectedGroup, Collection<Integer> selectedUsers) {
			this.selectedGroup = selectedGroup;
			this.selectedUsers = selectedUsers;
		}

		public Integer getSelectedGroup() {
			return selectedGroup;
		}

		public void setSelectedGroup(Integer selectedGroup) {
			this.selectedGroup = selectedGroup;
		}

		public Collection<Integer> getSelectedUsers() {
			return selectedUsers;
		}

		public void setSelectedUsers(Collection<Integer> selectedUsers) {
			this.selectedUsers = selectedUsers;
		}
	}

	/**
	 * Add users to the group
	 *
	 * @param usergroupID
	 */
	@POST
	@Path("/addusergroup")
	@Consumes({ "application/json" })
	public void addUserToGroup(AssociationGUID usergroupID) {
		GroupClass group = em.find(GroupClass.class, usergroupID.getSelectedGroup());
		for (Integer userId : usergroupID.getSelectedUsers()) {
			User user = em.find(User.class, userId);
			group.addUser(user);
		}
	}

	public static class EventSlotsData {
		public String eventName;
		public Collection<Integer> participants;
		public Collection<Long> startDates;
		public Collection<Long> endDates;
		public Collection<Integer> capacities;

		public String getEventName() {
			return eventName;
		}

		public void setEventName(String eventName) {
			this.eventName = eventName;
		}

		public Collection<Integer> getParticipants() {
			return participants;
		}

		public void setParticipants(Collection<Integer> participants) {
			this.participants = participants;
		}

		public Collection<Long> getStartDates() {
			return startDates;
		}

		public void setStartDates(Collection<Long> startDates) {
			this.startDates = startDates;
		}

		public Collection<Long> getEndDates() {
			return endDates;
		}

		public void setEndDates(Collection<Long> endDates) {
			this.endDates = endDates;
		}

		public Collection<Integer> getCapacities() {
			return capacities;
		}

		public void setCapacities(Collection<Integer> capacities) {
			this.capacities = capacities;
		}

	}

	/**
	 * Create an event and its associated slots
	 * 
	 * @param EventSlotsData
	 */
	@POST
	@Path("/createevent")
	@Consumes({ "application/json" })
	public void createEvent(@HeaderParam("Cookie") String rawAuthToken, EventSlotsData eventSlotsData) {
		String[] cookieParts = rawAuthToken.split("=", 2);
		String cookie = cookieParts[1];
		// check if the user is logged in
		TypedQuery<ConnexionToken> req = em.createQuery("select c from ConnexionToken c where c.token = :token",
				ConnexionToken.class);
		req.setParameter("token", cookie);
		try {
			ConnexionToken token = req.getSingleResult();
			if (token != null) {
				System.out.println("login post success !!");
				// create the event
				Event event = new Event(eventSlotsData.eventName);
				em.persist(event);
				// add the participants
				// initialize the guests list
				event.setGuests(new ArrayList<User>());
				event.setSlots(new ArrayList<Slot>());
				for (Integer userId : eventSlotsData.participants) {
					User user = em.find(User.class, userId);
					event.getGuests().add(user);
				}
				// create the slots
				for (int i = 0; i < eventSlotsData.startDates.size(); i++) {
					int capacity = eventSlotsData.capacities.toArray(new Integer[0])[i];
					long startDate = eventSlotsData.startDates.toArray(new Long[0])[i];
					long endDate = eventSlotsData.endDates.toArray(new Long[0])[i];

					Place location = new Place("Location " + i, capacity);
					em.persist(location);

					Slot slot = new Slot(capacity, startDate, endDate);
					em.persist(slot);
					slot.setLocation(location);
					slot.setEvent(event);
				}
				System.out.println("Event and slots created");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error creating event");
		}
	}

	public static class SmallEvent {
		public Integer eventId;
		public String eventName;
		public Collection<Integer> slotIds;
		public Collection<Long> startDates;
		public Collection<Long> endDates;
		public Collection<Integer> capacities;

		// Constructor
		public SmallEvent() {
		}

		public Integer getEventId() {
			return eventId;
		}

		public void setEventId(Integer eventId) {
			this.eventId = eventId;
		}

		public String getEventName() {
			return eventName;
		}

		public void setEventName(String eventName) {
			this.eventName = eventName;
		}

		public Collection<Integer> getSlotIds() {
			return slotIds;
		}

		public void setSlotIds(Collection<Integer> slotIds) {
			this.slotIds = slotIds;
		}

		public Collection<Long> getStartDates() {
			return startDates;
		}

		public void setStartDates(Collection<Long> startDates) {
			this.startDates = startDates;
		}

		public Collection<Long> getEndDates() {
			return endDates;
		}

		public void setEndDates(Collection<Long> endDates) {
			this.endDates = endDates;
		}

		public Collection<Integer> getCapacities() {
			return capacities;
		}

		public void setCapacities(Collection<Integer> capacities) {
			this.capacities = capacities;
		}

	}

	/*
	 * Retrieve an event
	 * 
	 * @param cookie
	 * 
	 * @param eventId
	 */
	@GET
	@Path("/getevent")
	@Produces({ "application/json" })
	public SmallEvent getEvent(@HeaderParam("Cookie") String rawAuthToken, @HeaderParam("eventId") String rawEventId) {
		String[] cookieParts = rawAuthToken.split("=", 2);
		String cookie = cookieParts[1];
		System.out.println("Raw event id : " + rawEventId);
		int eventId = Integer.parseInt(rawEventId);
		// check if the user is logged in
		TypedQuery<ConnexionToken> req = em.createQuery("select c from ConnexionToken c where c.token = :token",
				ConnexionToken.class);
		req.setParameter("token", cookie);
		try {
			ConnexionToken token = req.getSingleResult();
			if (token != null) {
				System.out.println("login success !!");
				Event event = em.find(Event.class, eventId);
				System.out.println("Event found : " + event.getName());

				// check if the user is a guest of the event
				boolean isGuest = false;
				for (User guest : event.getGuests()) {
					if (guest.getId() == token.getUserToken().getId()) {
						isGuest = true;
						System.out.println("User is a guest of the event");
						break;
					}
				}
				if (!isGuest) {
					System.out.println("User is not a guest of the event");
					return null;
				}

				SmallEvent smallEvent = new SmallEvent();
				smallEvent.setEventId(event.getId());
				smallEvent.setEventName(event.getName());
				smallEvent.setSlotIds(new ArrayList<>());
				smallEvent.setStartDates(new ArrayList<>());
				smallEvent.setEndDates(new ArrayList<>());
				smallEvent.setCapacities(new ArrayList<>());
				// get the slots
				TypedQuery<Slot> reqSlots = em.createQuery("select s from Slot s where s.event_slot = :event",
						Slot.class);
				reqSlots.setParameter("event", event);
				Collection<Slot> slots = reqSlots.getResultList();
				System.out.println("Slots : " + slots.size());
				for (Slot slot : slots) {
					if (slot.getCapacity() != 0) {

						smallEvent.getSlotIds().add(slot.getId());
						smallEvent.getStartDates().add(slot.getStartDate());
						smallEvent.getEndDates().add(slot.getEndDate());
						smallEvent.getCapacities().add(slot.getCapacity());
					}
				}
				return smallEvent;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error getting event");
		}
		return null;
	}

	public static class SlotId {
		public Integer slotId;

		public SlotId() {
		}

		public Integer getSlotId() {
			return slotId;
		}

		public void setSlotId(Integer slotId) {
			this.slotId = slotId;
		}
	}

	/**
	 * Associate a user to a slot in an event
	 * 
	 * @param SlotId
	 */
	@POST
	@Path("/addusertoslot")
	@Consumes({ "application/json" })
	public void addUserToSlot(@HeaderParam("Cookie") String rawAuthToken, SlotId slotId) {
		String[] cookieParts = rawAuthToken.split("=", 2);
		String cookie = cookieParts[1];
		// check if the user is logged in
		TypedQuery<ConnexionToken> req = em.createQuery("select c from ConnexionToken c where c.token = :token",
				ConnexionToken.class);
		req.setParameter("token", cookie);
		try {
			ConnexionToken token = req.getSingleResult();
			if (token != null) {
				System.out.println("login success !!");
				Slot slot = em.find(Slot.class, slotId.getSlotId());
				User user = token.getUserToken();
				slot.getParticipants().add(user);
				System.out.println("User added to slot");
				slot.getEvent().getGuests().remove(user);
				int newCapacity = slot.getCapacity() - 1;

				slot.setCapacity(newCapacity);
				System.out.println("Slot capacity updated");

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error adding user to slot");
		}
	}

	//
	//
	// /**
	// * Add a task to the DB
	// *
	// * @param name
	// * @param deadline
	// */
	//
	// @POST
	// @Path("/addtask")
	// @Consumes({ "application/json" })
	// public void addTask(Task task) {
	// em.persist(task);
	// }
	//
	//
	// /**
	// * Add a time slot to the DB
	// *
	// * @param capacity
	// * @param startDate
	// * @param endDate
	// * @param location
	// */
	// @POST
	// @Path("/addslot")
	// @Consumes({ "application/json" })
	// public void addSlot(Slot slot) {
	//
	// em.persist(slot);
	// }
	//
	//
	// /**
	// * Add a location to the DB
	// *
	// * @param name
	// * @param capacity
	// */
	// @POST
	// @Path("/addslot")
	// @Consumes({ "application/json" })
	// public void addLocation(Place location) {
	// em.persist(location);
	// }
	//

	/**
	 * Add an agenda to the DB
	 *
	 * @param name
	 * @param tasks
	 * @param slots
	 */
	@POST
	@Path("/addagenda")
	@Consumes({ "application/json" })
	public void addAgenda(Agenda agenda) {
		em.persist(agenda);
	}

	/**
	 * Add an event to the DB
	 *
	 * @param name
	 * @param guests
	 * @param organisers
	 */
	@POST
	@Path("/addevent")
	@Consumes({ "application/json" })
	public void addEvent(Event event) {
		em.persist(event);
	}

	//
	//
	//
	//
	/**
	 * Add a guest to an event
	 *
	 * @param guestId
	 * @param eventId
	 */
	@POST
	@Path("/addguestevent")
	@Consumes({ "application/json" })
	public void addGuestInEvent(Association guesteventID) {
		User guest = em.find(User.class, guesteventID.getFirstId());
		Event event = em.find(Event.class, guesteventID.getSecondId());
		event.addGuest(guest);
	}

	//
	//
	/**
	 * Add an organiser to an event
	 *
	 * @param orgaId
	 * @param eventId
	 */
	/*
	 * @POST
	 * 
	 * @Path("/addorgaevent")
	 * 
	 * @Consumes({ "application/json" })
	 * public void addOrganiserInEvent(Association orgaeventID) {
	 * User organiser = em.find(User.class, orgaeventID.getFirstId());
	 * Event event = em.find(Event.class, orgaeventID.getSecondId());
	 * event.addOrganiser(organiser);
	 * }
	 */

	//
	//
	/**
	 * Get all the existing location
	 *
	 * @return Collection<Place>
	 */
	@GET
	@Path("/getplace")
	@Consumes({ "application/json" })
	public Collection<Place> getPlace() {
		TypedQuery<Place> req = em.createQuery("select l from Place l", Place.class);
		return req.getResultList();
	}

	@POST
	@Path("/addplace")
	@Consumes({ "application/json" })
	public void addPlace(Place place) {
		em.persist(place);
	}

	/**
	 * @param cookie
	 * @return User
	 */
	@GET
	@Path("/getwcookie")
	@Produces({ "application/json" })
	public User getUserWithCookie(@HeaderParam("cookie") String cookie) {
		System.out.println("Cookie : " + cookie);
		TypedQuery<ConnexionToken> req = em.createQuery("select c from ConnexionToken c where c.token = :token",
				ConnexionToken.class);
		req.setParameter("token", cookie);
		ConnexionToken token = req.getSingleResult();
		return token.getUserToken();
	}

	@POST
	@Path("/slottoagenda")
	@Consumes({ "application/json" })
	public void ajoutSlotAgenda(Slot slot, Agenda agenda) {
		Collection<Slot> slots = agenda.getSlots();
		slots.add(slot);
		agenda.setSlots(slots);
	}

	@POST
	@Path("/tasktoagenda")
	@Consumes({ "application/json" })
	public void ajoutTaskAgenda(Task task, Agenda agenda) {

	}

	@POST
	@Path("/initdb")
	@Consumes({ "application/json" })
	public void initTestDB() {
		GroupName g = new GroupName("Acide");
		createGroup(g);
		GroupName g2 = new GroupName("ab");
		createGroup(g2);
		String password = "a";
		String hashedPassword = LoginInfo.hashPassword(password);
		// "ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb"
		User u = new User("kirby", "Jean-Michel", "Paltan", null, null, false);
		u.setHashedPassword(hashedPassword);
		addUser(u);
		User u2 = new User("ness", "Anabelle", "Praissé", null, null, false);
		u2.setHashedPassword(hashedPassword);
		addUser(u2);
		User u3 = new User("a", "a", "a", null, null, false);
		u3.setHashedPassword(hashedPassword);
		addUser(u3);

		Event e = new Event("fete des agrumes");
		addEvent(e);
		System.out.println("Création de l'évènement fete des agrumes");
		// addOrganiserInEvent(new Association(u.getId(), e.getId()));
		System.out.println("Ajout de l'organisateur Myrtille à l'évènement fete des agrumes");
		// addGuestInEvent(new Association(u2.getId(), e.getId()));
		System.out.println("Ajout de l'invité Tron à l'évènement fete des agrumes");

		Agenda a = new Agenda("Déroulé de la fete", null, null);
		addAgenda(a);
		System.out.println("Création de l'agenda Déroulé de la fete");

		Place p = new Place("Salle des citrons", 100);
		addPlace(p);
		System.out.println("Création de la salle des citrons");
	}

	//
	//
	// /**
	// * Add a participant to a slot
	// *
	// * @param slotId
	// * @param userId
	// */
	// @POST
	// @Path("/adduserslot")
	// @Consumes({ "application/json" })
	// public void addParticipantToSlot(Association userslotID) {
	// User participant = em.find(User.class, userslotID.getFirstId());
	// Slot slot = em.find(Slot.class, userslotID.getSecondId());
	// slot.addParticipant(participant);
	// }
	//
	//
	// /**
	// * Remove an user of the group
	// *
	// * @param groupId
	// * @param userId
	// */
	// @POST
	// @Path("/rmusergroup")
	// @Consumes({ "application/json" })
	// public void removeUserToGroup(Association usergroupID) {
	// User user = em.find(User.class, usergroupID.getFirstId());
	// GroupClass group = em.find(GroupClass.class, usergroupID.getSecondId());
	// group.removeUser(user);
	// }
	//

	/*******************************************************
	 * 
	 * Pour tester le js
	 * 
	 ******************************************************/

	@POST
	@Path("/addperson")
	@Consumes({ "application/json" })
	public void ajoutPersonne(Personne personne) {
		em.persist(personne);
	}

	/**
	 * @param adresse
	 */
	@POST
	@Path("/addaddress")
	@Consumes({ "application/json" })
	public void ajoutAdresse(Adresse adresse) {
		em.persist(adresse);
	}

	/**
	 * @return Collection<Personne>
	 */
	@GET
	@Path("/listpersons")
	@Produces({ "application/json" })
	public Collection<Personne> listePersonnes() {
		TypedQuery<Personne> req = em.createQuery("select c from Personne c", Personne.class);
		return req.getResultList();
	}

	/**
	 * @return Collection<Adresse>
	 */
	@GET
	@Path("/listaddresses")
	@Produces({ "application/json" })
	public Collection<Adresse> listeAdresses() {
		TypedQuery<Adresse> req = em.createQuery("select c from Adresse c", Adresse.class);
		return req.getResultList();
	}

	/**
	 * @param association
	 */
	@POST
	@Path("/associate")
	@Consumes({ "application/json" })
	public void associer(Association association) {
		System.out.println("On a l'association : " + association.getFirstId() + ", " + association.getSecondId());
		Adresse addr = em.find(Adresse.class, association.getFirstId());
		Personne pers = em.find(Personne.class, association.getSecondId());
		addr.setPersonne(pers); // OneToMany
		// addr.getPersonnes().add(pers); // ManyToMany
		// addr.setPersonne(pers); // OneToOne
	}
}
