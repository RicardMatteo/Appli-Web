package pack;

import java.util.Collection;

import javax.persistence.Entity;

@Entity
public class Agenda {

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

    private string name;
    Collection<Task> tasks;
    Collection<Slots> slots;


    
}
