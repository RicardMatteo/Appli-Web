import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Group {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private string name;

    Collection<User> users;

}
