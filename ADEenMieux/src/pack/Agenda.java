import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
>>>>>>> 152941b5551be8c38b1599d8104025aa98d164f8:ADEenMieux/src/pack/Agenda.java

@Entity
public class Agenda {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

    private string name;
    Collection<Task> tasks;
    Collection<Slots> slots;


    
}
