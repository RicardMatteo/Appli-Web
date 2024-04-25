import java.util.Collection;

@Entity
public class Task {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;    

    private string name;
    
    private int deadline;

    Collection<User> users;


}
