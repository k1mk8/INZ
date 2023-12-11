package inz23.stolmel.dataTypeClasses;

public class Client {

	private int id;
	private String name;
	private String surname;
	private String number;
	private String email;
	private String hash;
	private boolean isAdmin;

	public Client() {
		this.id = -1;
		this.name = "";
		this.surname = "";
		this.number = "";
		this.email = "";
		this.hash = "";
		this.isAdmin = false;
	}

	public Client(int id, String name, String surname, String number, String email, String hash, boolean isAdmin) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.number = number;
		this.email = email;
		this.hash = hash;
		this.isAdmin = isAdmin;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getNumber() {
		return number;
	}

	public String getEmail() {
		return email;
	}

	public String getHash() {
		return hash;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	private void setId(int id) {
		this.id = id;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setSurname(String surname) {
		this.surname = surname;
	}

	private void setNumber(String number) {
		this.number = number;
	}

	private void setEmail(String email) {
		this.email = email;
	}

	private void setHash(String hash) {
		this.hash = hash;
	}

	private void isAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return String.format("""
				{"id":"%d", "name":"%s", "surname":"%s", "number":"%s", "email":"%s", "hash":"%s", "is_admin":"%b"}""",
				this.id, this.name, this.surname, this.number, this.email, this.hash, this.isAdmin);
	}

	@Override
	public boolean equals(Object obj) {
		Client otherClient = (Client) obj;

		return id == otherClient.getId() &&
				name == otherClient.getName() &&
				surname == otherClient.getSurname() &&
				number == otherClient.getNumber() &&
				email == otherClient.getEmail() &&
				hash == otherClient.getHash() &&
				isAdmin == otherClient.isAdmin();
	}
}