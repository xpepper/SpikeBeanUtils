
public class MyBean {
	private String name;
	private String surname;
	private String address;

	public MyBean(String string, String string2, String string3) {
		name = string;
		surname = string2;
		address = string3;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getSurname() {
		return surname;
	}

}