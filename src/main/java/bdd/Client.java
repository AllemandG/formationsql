package bdd;

public class Client {
	private int id;
	private String lastname;
	private String firstname;
	private Gender gender;
	private int fav_book;

	// Constructor
	public Client(int id, String lastname, String firstname, Gender gender, int fav_book) {
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.gender = gender;
		this.fav_book = fav_book;
	}

	public Client(String lastname, String firstname, Gender gender) {
		this(0, lastname, firstname, gender, 0);
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", " + lastname + " " + firstname + ", gender=" + gender + ", fav_book=" + fav_book
				+ "]";
	}

	// Getters and Setters

	public int getFav_book() {
		return fav_book;
	}

	public void setFav_book(int fav_book) {
		this.fav_book = fav_book;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public Gender getGender() {
		return gender;
	}

}
