package pack;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String username;
    private String firstName;
    private String lastName;

    Collection<Group> groups;
    Collection<Agenda> agendas;

    private boolean isAdmin;
}