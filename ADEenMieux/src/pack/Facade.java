package pack;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Collections;

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

				return encodedhash.toString();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
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
	public void createGroup(GroupName groupName) {
		GroupClass group = new GroupClass();
		group.setName(groupName.getGroupName());
		em.persist(group);
	}

	/**
	 * List all the groups
	 * 
	 * @param Cookie
	 */
	@GET
	@Path("/listgroups")
	@Produces({ "application/json" })
	public Collection<GroupClass> listGroups(@HeaderParam("cookie") String cookie) {
		// check if the user is logged in
		TypedQuery<ConnexionToken> req = em.createQuery("select c from ConnexionToken c where c.token = :token",
				ConnexionToken.class);
		req.setParameter("token", cookie);
		try {
			ConnexionToken token = req.getSingleResult();
			if (token != null) {
				// get all the groups from the DB
				TypedQuery<GroupClass> reqGroups = em.createQuery("select g from GroupClass g", GroupClass.class);
				// return the list of groups as a json object that we build as a string
				// beforehand, and then put it in the groups header
				return reqGroups.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("User not found");
			TypedQuery<GroupClass> reqGroups = em.createQuery("select g from GroupClass g", GroupClass.class);
			// return the list of groups as a json object that we build as a string
			// beforehand, and then put it in the groups header
			return reqGroups.getResultList();
			// return Collections.emptyList();
		}
		System.out.println("Password incorrect");
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
	//
	// /**
	// * Add a group to the DB
	// *
	// * @param name
	// * @param users
	// */
	//
	// @POST
	// @Path("/addgroup")
	// @Consumes({ "application/json" })
	// public void addGroup(GroupClass group) {
	// em.persist(group);
	// }
	//
	//
	// /**
	// * Add an agenda to the DB
	// *
	// * @param name
	// * @param tasks
	// * @param slots
	// */
	// @POST
	// @Path("/addagenda")
	// @Consumes({ "application/json" })
	// public void addAgenda(Agenda agenda) {
	// em.persist(agenda);
	// }
	//
	// /**
	// * Add an event to the DB
	// *
	// * @param name
	// * @param guests
	// * @param organisers
	// */
	// @POST
	// @Path("/addevent")
	// @Consumes({ "application/json" })
	// public void addEvent(Event event) {
	// em.persist(event);
	// }
	//
	//
	//
	//
	// /**
	// * Add a guest to an event
	// *
	// * @param guestId
	// * @param eventId
	// */
	// @POST
	// @Path("/addguestevent")
	// @Consumes({ "application/json" })
	// public void addGuestInEvent(Association guesteventID) {
	// User guest = em.find(User.class, guesteventID.getFirstId());
	// Event event = em.find(Event.class, guesteventID.getSecondId());
	// event.addGuest(guest);
	// }
	//
	//
	// /**
	// * Add an organiser to an event
	// *
	// * @param orgaId
	// * @param eventId
	// */
	// @POST
	// @Path("/addorgaevent")
	// @Consumes({ "application/json" })
	// public void addOrganiserInEvent(Association orgaeventID) {
	// User organiser = em.find(User.class, orgaeventID.getFirstId());
	// Event event = em.find(Event.class, orgaeventID.getSecondId());
	// event.addOrganiser(organiser);
	// }
	//
	//
	// /**
	// * Get all the existing location
	// *
	// * @return Collection<Location>
	// */
	//
	// public Collection<Location> getLocations() {
	// TypedQuery<Location> req =
	// em.createQuery("select l from Location l", Location.class); return
	// req.getResultList();
	// }
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
	// * Add an user to the group
	// *
	// * @param groupId
	// * @param userId
	// */
	// @POST
	// @Path("/addusergroup")
	// @Consumes({ "application/json" })
	// public void addUserToGroup(Association usergroupID) {
	// User user = em.find(User.class, usergroupID.getFirstId());
	// GroupClass group = em.find(GroupClass.class, usergroupID.getSecondId());
	// group.addUser(user);
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
