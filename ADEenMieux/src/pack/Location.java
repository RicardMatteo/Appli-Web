
@Entity
public class Location {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

    private string name;
    private int capacity;
    private Agenda agenda;
}
