package bdd;

public class BuyedBook {
	private int id_book;
	private int id_client;

	public BuyedBook(int id_book, int id_client) {
		this.id_book = id_book;
		this.id_client = id_client;
	}

	// Getters

	public int getId_book() {
		return id_book;
	}

	public int getId_client() {
		return id_client;
	}

}
