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

/**
 * The Facade class is responsible for handling user-related operations and serving as an interface between the client and the database.
 * It provides methods for adding users, logging in, retrieving user information, creating groups, and listing users and groups.
 */
/**
 * The Facade class is responsible for handling various operations related to user management and authentication.
 * It provides methods for adding a user to the database, logging in a user, retrieving user information, creating groups, and listing users and groups.
 * This class is annotated with @Singleton to ensure that only one instance of the class is created and shared across multiple requests.
 * The class also defines several inner classes to represent different data structures used in the operations.
 */
/**
 * The Facade class is responsible for handling user-related operations and
 * serving as an interface between the client and the database.
 * It provides methods for adding users, logging in, retrieving user
 * information, creating groups, and listing users and groups.
 */
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

		/**
		 * Default constructor for LoginInfo.
		 */
		public LoginInfo() {
		}

		/**
		 * Constructor for LoginInfo with username and hashed password.
		 *
		 * @param username       the username
		 * @param hashedPassword the hashed password
		 */
		public LoginInfo(String username, String hashedPassword) {
			this.username = username;
			this.hashedPassword = hashedPassword;
		}

		/**
		 * Get the username.
		 *
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}

		/**
		 * Set the username.
		 *
		 * @param username the username to set
		 */
		public void setUsername(String username) {
			this.username = username;
		}

		/**
		 * Hashes the given password using SHA-256 algorithm.
		 *
		 * @param password the password to hash
		 * @return the hashed password
		 */
		public static String hashPassword(String password) {
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance("SHA-256");
				byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
				String encodedHashString = bytesToHex(encodedhash);
				return encodedHashString;
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return "";
		}

		/**
		 * Converts a byte array to a hexadecimal string.
		 *
		 * @param encodedhash the byte array to convert
		 * @return the hexadecimal string
		 */
		private static String bytesToHex(byte[] encodedhash) {
			StringBuilder sb = new StringBuilder();
			for (byte b : encodedhash) {
				sb.append(String.format("%02x", b));
			}
			String result = sb.toString();
			return result;
		}

		/**
		 * Get the hashed password.
		 *
		 * @return the hashed password
		 */
		public String getHashedPassword() {
			return hashedPassword;
		}

		/**
		 * Set the hashed password.
		 *
		 * @param hashedPassword the hashed password to set
		 */
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

		/**
		 * Constructs a new Cookie object.
		 */
		public Cookie() {
		}

		/**
		 * Constructs a new Cookie object with the specified cookie value.
		 *
		 * @param cookie the cookie value
		 */
		public Cookie(String cookie) {
			this.cookie = cookie;
		}

		/**
		 * Returns the cookie value.
		 *
		 * @return the cookie value
		 */
		public String getCookie() {
			return cookie;
		}

		/**
		 * Sets the cookie value.
		 *
		 * @param cookie the cookie value to set
		 */
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

		/**
		 * Constructs a new GroupName object.
		 */
		public GroupName() {
		}

		/**
		 * Constructs a new GroupName object with the specified group name.
		 *
		 * @param groupName the group name
		 */
		public GroupName(String groupName) {
			this.groupName = groupName;
		}

		/**
		 * Returns the group name.
		 *
		 * @return the group name
		 */
		public String getGroupName() {
			return groupName;
		}

		/**
		 * Sets the group name.
		 *
		 * @param groupName the group name to set
		 */
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

		/**
		 * Default constructor for the SmallGroup class.
		 */
		public SmallGroup() {
		}

		/**
		 * Constructor for the SmallGroup class.
		 * 
		 * @param groupId   the ID of the group
		 * @param groupName the name of the group
		 */
		public SmallGroup(Integer groupId, String groupName) {
			this.groupId = groupId;
			this.groupName = groupName;
		}

		/**
		 * Get the ID of the group.
		 * 
		 * @return the group ID
		 */
		public Integer getGroupId() {
			return groupId;
		}

		/**
		 * Set the ID of the group.
		 * 
		 * @param groupId the group ID to set
		 */
		public void setGroupId(Integer groupId) {
			this.groupId = groupId;
		}

		/**
		 * Get the name of the group.
		 * 
		 * @return the group name
		 */
		public String getGroupName() {
			return groupName;
		}

		/**
		 * Set the name of the group.
		 * 
		 * @param groupName the group name to set
		 */
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

		/**
		 * Default constructor for the SmallUser class.
		 */
		public SmallUser() {
		}

		/**
		 * Constructor for the SmallUser class with userId and username parameters.
		 *
		 * @param userId   the ID of the user
		 * @param username the username of the user
		 */
		public SmallUser(Integer userId, String username) {
			this.userId = userId;
			this.username = username;
		}

		/**
		 * Get the ID of the user.
		 *
		 * @return the ID of the user
		 */
		public Integer getUserId() {
			return userId;
		}

		/**
		 * Set the ID of the user.
		 *
		 * @param userId the ID of the user
		 */
		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		/**
		 * Get the username of the user.
		 *
		 * @return the username of the user
		 */
		public String getUsername() {
			return username;
		}

		/**
		 * Set the username of the user.
		 *
		 * @param username the username of the user
		 */
		public void setUsername(String username) {
			this.username = username;
		}

		/**
		 * Get the first name of the user.
		 *
		 * @return the first name of the user
		 */
		public String getFirstName() {
			return firstName;
		}

		/**
		 * Set the first name of the user.
		 *
		 * @param firstName the first name of the user
		 */
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		/**
		 * Get the last name of the user.
		 *
		 * @return the last name of the user
		 */
		public String getLastName() {
			return lastName;
		}

		/**
		 * Set the last name of the user.
		 *
		 * @param lastName the last name of the user
		 */
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		/**
		 * Get the hashed password of the user.
		 *
		 * @return the hashed password of the user
		 */
		public String getHashedPassword() {
			return hashedPassword;
		}

		/**
		 * Set the hashed password of the user.
		 *
		 * @param hashedPassword the hashed password of the user
		 */
		public void setHashedPassword(String hashedPassword) {
			this.hashedPassword = hashedPassword;
		}

		/**
		 * Check if the user is an admin.
		 *
		 * @return true if the user is an admin, false otherwise
		 */
		public boolean isAdmin() {
			return isAdmin;
		}

		/**
		 * Set the admin status of the user.
		 *
		 * @param isAdmin true if the user is an admin, false otherwise
		 */
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

	/**
	 * Represents an association between a selected group and a collection of
	 * selected users.
	 */
	public static class AssociationGUID {
		private Integer selectedGroup;
		private Collection<Integer> selectedUsers;

		/**
		 * Default constructor for the AssociationGUID class.
		 */
		public AssociationGUID() {
		}

		/**
		 * Constructor for the AssociationGUID class.
		 * 
		 * @param selectedGroup The selected group.
		 * @param selectedUsers The collection of selected users.
		 */
		public AssociationGUID(Integer selectedGroup, Collection<Integer> selectedUsers) {
			this.selectedGroup = selectedGroup;
			this.selectedUsers = selectedUsers;
		}

		/**
		 * Get the selected group.
		 * 
		 * @return The selected group.
		 */
		public Integer getSelectedGroup() {
			return selectedGroup;
		}

		/**
		 * Set the selected group.
		 * 
		 * @param selectedGroup The selected group.
		 */
		public void setSelectedGroup(Integer selectedGroup) {
			this.selectedGroup = selectedGroup;
		}

		/**
		 * Get the collection of selected users.
		 * 
		 * @return The collection of selected users.
		 */
		public Collection<Integer> getSelectedUsers() {
			return selectedUsers;
		}

		/**
		 * Set the collection of selected users.
		 * 
		 * @param selectedUsers The collection of selected users.
		 */
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

	/**
	 * Represents the data for event slots.
	 */
	public static class EventSlotsData {
		public String eventName;
		public Collection<Integer> participants;
		public Collection<Long> startDates;
		public Collection<Long> endDates;
		public Collection<Integer> capacities;
		public Collection<Integer> places;

		/**
		 * Gets the event name.
		 * 
		 * @return The event name.
		 */
		public String getEventName() {
			return eventName;
		}

		/**
		 * Sets the event name.
		 * 
		 * @param eventName The event name to set.
		 */
		public void setEventName(String eventName) {
			this.eventName = eventName;
		}

		/**
		 * Gets the participants.
		 * 
		 * @return The participants.
		 */
		public Collection<Integer> getParticipants() {
			return participants;
		}

		/**
		 * Sets the participants.
		 * 
		 * @param participants The participants to set.
		 */
		public void setParticipants(Collection<Integer> participants) {
			this.participants = participants;
		}

		/**
		 * Gets the start dates.
		 * 
		 * @return The start dates.
		 */
		public Collection<Long> getStartDates() {
			return startDates;
		}

		/**
		 * Sets the start dates.
		 * 
		 * @param startDates The start dates to set.
		 */
		public void setStartDates(Collection<Long> startDates) {
			this.startDates = startDates;
		}

		/**
		 * Gets the end dates.
		 * 
		 * @return The end dates.
		 */
		public Collection<Long> getEndDates() {
			return endDates;
		}

		/**
		 * Sets the end dates.
		 * 
		 * @param endDates The end dates to set.
		 */
		public void setEndDates(Collection<Long> endDates) {
			this.endDates = endDates;
		}

		/**
		 * Gets the capacities.
		 * 
		 * @return The capacities.
		 */
		public Collection<Integer> getCapacities() {
			return capacities;
		}

		/**
		 * Sets the capacities.
		 * 
		 * @param capacities The capacities to set.
		 */
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

					System.out.println("id place :" + eventSlotsData.places.toArray(new Integer[0])[i]);
					// Place location = new Place("Location " + i, capacity);
					// em.persist(location);

					Place location = em.find(Place.class, eventSlotsData.places.toArray(new Integer[0])[i]);

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
		/**
		 * The ID of the event.
		 */
		public Integer eventId;

		/**
		 * The name of the event.
		 */
		public String eventName;

		/**
		 * The IDs of the slots associated with the event.
		 */
		public Collection<Integer> slotIds;

		/**
		 * The start dates of the event.
		 */
		public Collection<Long> startDates;

		/**
		 * The end dates of the event.
		 */
		public Collection<Long> endDates;

		/**
		 * The capacities of the event.
		 */
		public Collection<Integer> capacities;

		/**
		 * Constructs a new instance of the SmallEvent class.
		 */
		public SmallEvent() {
		}

		/**
		 * Gets the ID of the event.
		 *
		 * @return The ID of the event.
		 */
		public Integer getEventId() {
			return eventId;
		}

		/**
		 * Sets the ID of the event.
		 *
		 * @param eventId The ID of the event.
		 */
		public void setEventId(Integer eventId) {
			this.eventId = eventId;
		}

		/**
		 * Gets the name of the event.
		 *
		 * @return The name of the event.
		 */
		public String getEventName() {
			return eventName;
		}

		/**
		 * Sets the name of the event.
		 *
		 * @param eventName The name of the event.
		 */
		public void setEventName(String eventName) {
			this.eventName = eventName;
		}

		/**
		 * Gets the IDs of the slots associated with the event.
		 *
		 * @return The IDs of the slots associated with the event.
		 */
		public Collection<Integer> getSlotIds() {
			return slotIds;
		}

		/**
		 * Sets the IDs of the slots associated with the event.
		 *
		 * @param slotIds The IDs of the slots associated with the event.
		 */
		public void setSlotIds(Collection<Integer> slotIds) {
			this.slotIds = slotIds;
		}

		/**
		 * Gets the start dates of the event.
		 *
		 * @return The start dates of the event.
		 */
		public Collection<Long> getStartDates() {
			return startDates;
		}

		/**
		 * Sets the start dates of the event.
		 *
		 * @param startDates The start dates of the event.
		 */
		public void setStartDates(Collection<Long> startDates) {
			this.startDates = startDates;
		}

		/**
		 * Gets the end dates of the event.
		 *
		 * @return The end dates of the event.
		 */
		public Collection<Long> getEndDates() {
			return endDates;
		}

		/**
		 * Sets the end dates of the event.
		 *
		 * @param endDates The end dates of the event.
		 */
		public void setEndDates(Collection<Long> endDates) {
			this.endDates = endDates;
		}

		/**
		 * Gets the capacities of the event.
		 *
		 * @return The capacities of the event.
		 */
		public Collection<Integer> getCapacities() {
			return capacities;
		}

		/**
		 * Sets the capacities of the event.
		 *
		 * @param capacities The capacities of the event.
		 */
		public void setCapacities(Collection<Integer> capacities) {
			this.capacities = capacities;
		}
	}

	/**
	 * @param rawAuthToken
	 * @param rawEventId
	 * @return SmallEvent
	 */
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

	/**
	 * Represents a Slot ID.
	 */
	public static class SlotId {
		public Integer slotId;

		public SlotId() {
		}

		/**
		 * Gets the slot ID.
		 * 
		 * @return The slot ID.
		 */
		public Integer getSlotId() {
			return slotId;
		}

		/**
		 * Sets the slot ID.
		 * 
		 * @param slotId The slot ID to set.
		 */
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

	/**
	 * Represents a small place object with its name, capacity, and ID.
	 */
	public static class SmallPlaceGet implements Serializable {
		private int id;
		private String name;
		private int capacity;

		/**
		 * Default constructor for the SmallPlaceGet class.
		 */
		public SmallPlaceGet() {
		}

		/**
		 * Constructor for the SmallPlaceGet class with parameters.
		 *
		 * @param name     the name of the small place
		 * @param capacity the capacity of the small place
		 * @param id       the ID of the small place
		 */
		public SmallPlaceGet(String name, int capacity, int id) {
			this.name = name;
			this.capacity = capacity;
			this.id = id;
		}

		/**
		 * Gets the name of the small place.
		 *
		 * @return the name of the small place
		 */
		public String getName() {
			return name;
		}

		/**
		 * Sets the name of the small place.
		 *
		 * @param name the name of the small place
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * Gets the capacity of the small place.
		 *
		 * @return the capacity of the small place
		 */
		public int getCapacity() {
			return capacity;
		}

		/**
		 * Sets the capacity of the small place.
		 *
		 * @param capacity the capacity of the small place
		 */
		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}

		/**
		 * Gets the ID of the small place.
		 *
		 * @return the ID of the small place
		 */
		public int getId() {
			return id;
		}

		/**
		 * Sets the ID of the small place.
		 *
		 * @param id the ID of the small place
		 */
		public void setId(int id) {
			this.id = id;
		}
	}

	/**
	 * Get all the existing location
	 *
	 * @return Collection<Place>
	 */
	@GET
	@Path("/getplacesid")
	@Produces({ "application/json" })
	public Collection<SmallPlaceGet> getPlaceId(@HeaderParam("Cookie") String rawAuthToken) {
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
				TypedQuery<Place> reqPlaces = em.createQuery("select p from Place p", Place.class);
				List<SmallPlaceGet> places = new ArrayList<>();
				for (Place place : reqPlaces.getResultList()) {
					System.out.println("Place : " + place.getName() + " " + place.getCapacity() + " " + place.getId());
					places.add(new SmallPlaceGet(place.getName(), place.getCapacity(), place.getId())); // Added closing
																										// parenthesis
				}
				return places;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error getting places");
		}
		return Collections.emptyList();
	}

	public static class SmallSlot implements Serializable {
		private String eventName;
		private long startDate;
		private long endDate;
		private String location;

		/**
		 * Default constructor for the SmallSlot class.
		 */
		public SmallSlot() {
		}

		/**
		 * Constructor for the SmallSlot class.
		 * 
		 * @param eventName The name of the event.
		 * @param startDate The start date of the event.
		 * @param endDate   The end date of the event.
		 * @param location  The location of the event.
		 */
		public SmallSlot(String eventName, long startDate, long endDate, String location) {
			this.eventName = eventName;
			this.startDate = startDate;
			this.endDate = endDate;
			this.location = location;
		}

		/**
		 * Get the name of the event.
		 * 
		 * @return The name of the event.
		 */
		public String getEventName() {
			return eventName;
		}

		/**
		 * Set the name of the event.
		 * 
		 * @param eventName The name of the event.
		 */
		public void setEventName(String eventName) {
			this.eventName = eventName;
		}

		/**
		 * Get the start date of the event.
		 * 
		 * @return The start date of the event.
		 */
		public long getStartDate() {
			return startDate;
		}

		/**
		 * Set the start date of the event.
		 * 
		 * @param startDate The start date of the event.
		 */
		public void setStartDate(long startDate) {
			this.startDate = startDate;
		}

		/**
		 * Get the end date of the event.
		 * 
		 * @return The end date of the event.
		 */
		public long getEndDate() {
			return endDate;
		}

		/**
		 * Set the end date of the event.
		 * 
		 * @param endDate The end date of the event.
		 */
		public void setEndDate(long endDate) {
			this.endDate = endDate;
		}

		/**
		 * Get the location of the event.
		 * 
		 * @return The location of the event.
		 */
		public String getLocation() {
			return location;
		}

		/**
		 * Set the location of the event.
		 * 
		 * @param location The location of the event.
		 */
		public void setLocation(String location) {
			this.location = location;
		}
	}

	/**
	 * @param rawAuthToken
	 * @return Collection<SmallSlot>
	 */
	/*
	 * Get all the slots of a user
	 * 
	 * @param cookie
	 * 
	 * @return Collection<SmallSlot>
	 */
	@GET
	@Path("/getuserslots")
	@Produces({ "application/json" })
	public Collection<SmallSlot> getUserSlots(@HeaderParam("Cookie") String rawAuthToken) {
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
				User user = token.getUserToken();
				TypedQuery<Slot> reqSlots = em.createQuery("select s from Slot s where :user member of s.participants",
						Slot.class);
				reqSlots.setParameter("user", user);
				List<SmallSlot> slots = new ArrayList<>();
				for (Slot slot : reqSlots.getResultList()) {
					slots.add(new SmallSlot(slot.getEvent().getName(), slot.getStartDate(), slot.getEndDate(),
							slot.getLocation().getName()));
				}
				return slots;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error getting slots");
		}
		return Collections.emptyList();
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

	public static class SmallPlace implements Serializable {
		private String name;
		private int capacity;

		/**
		 * Default constructor for the SmallPlace class.
		 */
		public SmallPlace() {
		}

		/**
		 * Constructor for the SmallPlace class.
		 * 
		 * @param name     the name of the small place
		 * @param capacity the capacity of the small place
		 */
		public SmallPlace(String name, int capacity) {
			this.name = name;
			this.capacity = capacity;
		}

		/**
		 * Get the name of the small place.
		 * 
		 * @return the name of the small place
		 */
		public String getName() {
			return name;
		}

		/**
		 * Set the name of the small place.
		 * 
		 * @param name the name of the small place
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * Get the capacity of the small place.
		 * 
		 * @return the capacity of the small place
		 */
		public int getCapacity() {
			return capacity;
		}

		/**
		 * Set the capacity of the small place.
		 * 
		 * @param capacity the capacity of the small place
		 */
		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}
	}

	/**
	 * Get all the existing location
	 *
	 * @return Collection<Place>
	 */
	@GET
	@Path("/getplaces")
	@Produces({ "application/json" })
	public Collection<SmallPlace> getPlace(@HeaderParam("Cookie") String rawAuthToken) {
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
				TypedQuery<Place> reqPlaces = em.createQuery("select p from Place p", Place.class);
				List<SmallPlace> places = new ArrayList<>();
				for (Place place : reqPlaces.getResultList()) {
					places.add(new SmallPlace(place.getName(), place.getCapacity()));
				}
				return places;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error getting places");
		}
		return Collections.emptyList();
	}

	/**
	 * @param place
	 */
	@POST
	@Path("/addplace")
	@Consumes({ "application/json" })
	public void addPlace(SmallPlace place) {
		Place p = new Place(place.getName(), place.getCapacity());
		em.persist(p);
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

	/**
	 * @param slot
	 * @param agenda
	 */
	@POST
	@Path("/slottoagenda")
	@Consumes({ "application/json" })
	public void ajoutSlotAgenda(Slot slot, Agenda agenda) {
		Collection<Slot> slots = agenda.getSlots();
		slots.add(slot);
		agenda.setSlots(slots);
	}

	/**
	 * @param task
	 * @param agenda
	 */
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

		SmallPlace p = new SmallPlace("Salle des citrons", 100);
		addPlace(p);

		SmallPlace p2 = new SmallPlace("Salle des oranges", 100);
		addPlace(p2);
		System.out.println("Création de la salle des citrons");
	}

	public static class SmallInvitedEvents {
		private Integer eventId;
		private String eventName;

		/**
		 * Default constructor for the SmallInvitedEvents class.
		 */
		public SmallInvitedEvents() {
		}

		/**
		 * Get the event ID.
		 *
		 * @return The event ID.
		 */
		public Integer getEventId() {
			return eventId;
		}

		/**
		 * Set the event ID.
		 *
		 * @param eventId The event ID to set.
		 */
		public void setEventId(Integer eventId) {
			this.eventId = eventId;
		}

		/**
		 * Get the event name.
		 *
		 * @return The event name.
		 */
		public String getEventName() {
			return eventName;
		}

		/**
		 * Set the event name.
		 *
		 * @param eventName The event name to set.
		 */
		public void setEventName(String eventName) {
			this.eventName = eventName;
		}
	}

	/**
	 * Get the events the user is invited to
	 * 
	 * @param cookie
	 * @return SmallInvitedEvents
	 */
	@GET
	@Path("/getinvitedevents")
	@Produces({ "application/json" })
	public Collection<SmallInvitedEvents> getInvitedEvents(@HeaderParam("cookie") String cookie) {
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
				System.out.println("login success !!");
				User user = token.getUserToken();
				ArrayList<SmallInvitedEvents> invitedEvents = new ArrayList<>();
				// get all the events from the DB
				TypedQuery<Event> reqEvents = em.createQuery("select e from Event e", Event.class);
				// return the list of groups as a json object that we build as a string
				// beforehand, and then put it in the groups header
				for (Event event : reqEvents.getResultList()) {
					for (User guest : event.getGuests()) {
						if (guest.getId() == user.getId()) {
							SmallInvitedEvents smallEvent = new SmallInvitedEvents();
							smallEvent.setEventId(event.getId());
							smallEvent.setEventName(event.getName());
							invitedEvents.add(smallEvent);
						}
					}
				}
				return invitedEvents;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No events found");
			return null;
		}
		System.out.println("Not logged in");
		return null;
	}
}
