package pack;

import java.io.Serializable;

public class Association implements Serializable {
	
	int firstId;
	int secondId;
	
	public Association() {}
	public Association(int secondId, int firstId) {
		this.firstId = firstId;
		this.secondId = secondId;
	}
	
	public int getSecondId() {
		return secondId;
	}
	
	public void setSecondId(int secondId) {
		this.secondId = secondId;
	}
	
	public int getFirstId() {
		return firstId;
	}
	
	public void setFirstId(int firstId) {
		this.firstId = firstId;
	}
}
