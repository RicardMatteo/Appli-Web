package pack;

import java.io.Serializable;

public class Association implements Serializable {
	
	int personId;
	int addressId;
	
	public Association() {}
	public Association(int personId, int addressId) {
		this.addressId = addressId;
		this.personId = personId;
	}
	
	public int getPersonId() {
		return personId;
	}
	
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
	public int getAddressId() {
		return addressId;
	}
	
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
}
