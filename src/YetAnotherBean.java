
public class YetAnotherBean {
	private String name;
	private String surname;
	private String codFiscale;

	public YetAnotherBean(String string, String string2, String string3) {
		name = string;
		surname = string2;
		codFiscale = string3;
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
	
	public void setCodFiscale(String aString) {
		this.codFiscale = aString;
	}

	public String getCodFiscale() {
		return codFiscale;
	}

}