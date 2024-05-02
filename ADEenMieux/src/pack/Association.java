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
	
	
	/** 
	 * @return int
	 */
	public int getSecondId() {
		return secondId;
	}
	
	
	/** 
	 * @param secondId
	 */
	public void setSecondId(int secondId) {
		this.secondId = secondId;
	}
	
	
	/** 
	 * @return int
	 */
	public int getFirstId() {
		return firstId;
	}
	
	
	/** 
	 * @param firstId
	 */
	public void setFirstId(int firstId) {
		this.firstId = firstId;
	}
}
