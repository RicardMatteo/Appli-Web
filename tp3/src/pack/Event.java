import java.util.Collection;

@Entity
public class Event {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

    private string name;
    Collection<Users> guests;
    Collection<Users> organisers;

    
}
