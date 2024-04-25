import java.util.Collection;

@Entity
public class Slot {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

    private int capacity;
    private Location location;
    private int startDate;
    private int endDate;
    private Event event;

    Collection<Users> participants;

}
