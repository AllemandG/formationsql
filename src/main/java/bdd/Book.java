package bdd;

public class Book {
	private int id;
	private String title;
	private String author;

	// Constructor
	public Book(int id, String title, String author) {
		this.id = id;
		this.title = title;
		this.author = author;
	}

	public Book(String title, String author) {
		this(0, title, author);
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + "]";
	}

	// Getters and Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

}
