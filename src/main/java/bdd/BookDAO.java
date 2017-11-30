package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BookDAO {
	private static String url = "jdbc:postgresql://localhost:5432/formation-SQL-DTA";
	private static String username = "postgres";
	private static String password = "postgres";

	private BookDAO() {
	}

	public static boolean ajoutLivreBDD(Book book) {
		boolean opReussie = false;

		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO Book(title, author) VALUES(?, ?);",
						Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());

			stmt.executeUpdate();

			ResultSet result = stmt.getGeneratedKeys();

			while (result.next()) {
				book.setId(result.getInt("id"));
			}

			opReussie = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return opReussie;
	}

	public static void ajoutBookListBDD(List<Book> books) {
		for (Book b : books) {
			ajoutLivreBDD(b);
		}
	}

	public static boolean update(Book book) {
		boolean opReussie = false;
		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement stmt = conn.prepareStatement("UPDATE Book SET title = ?, author = ? WHERE id = ?")) {
			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());
			stmt.setInt(3, book.getId());

			stmt.executeUpdate();
			opReussie = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return opReussie;
	}

}
