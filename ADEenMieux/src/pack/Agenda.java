import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Agenda {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

    private String name;
    Collection<Task> tasks;
    Collection<Slots> slots;

    public Agenda(String name, Collection<Task> tasks, Collection<Slots> slots) {
        this.name = name;
        this.tasks = tasks;
        this.slots = slots;
    }

    public Agenda(String name) {
        this(name, new Collection<Task>(), new Collection<Slots>());
    }

}
