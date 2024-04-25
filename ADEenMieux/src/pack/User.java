import java.util.Collection;

import javax.annotation.processing.Generated;

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

    private bool isAdmin;
}