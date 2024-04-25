import java.util.Collection;

@Entity
public class Group {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private string name;

    Collection<User> users;

}
