package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ClientDAO {
	private static String url = "jdbc:postgresql://localhost:5432/formation-SQL-DTA";
	private static String username = "postgres";
	private static String password = "postgres";

	private ClientDAO() {
	}

	public static boolean ajoutClientBDD(Client client) {
		boolean opReussie = false;

		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement stmt = conn.prepareStatement(
						"INSERT INTO Client(lastname, firstname, gender) VALUES(?, ?, ?);",
						Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, client.getLastname());
			stmt.setString(2, client.getFirstname());
			stmt.setString(3, client.getGender().toString());

			stmt.executeUpdate();

			ResultSet result = stmt.getGeneratedKeys();

			while (result.next()) {
				client.setId(result.getInt("id"));
			}

			opReussie = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return opReussie;
	}

	public static void ajoutClientListBDD(List<Client> clients) {
		for (Client c : clients) {
			ajoutClientBDD(c);
		}
	}

	public static boolean addFavouriteBook(Client client, Book book) {
		boolean opReussie = false;
		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement stmt = conn
						.prepareStatement("UPDATE client SET id_favourite_book = ? WHERE id = ?")) {
			stmt.setInt(1, book.getId());
			stmt.setInt(2, client.getId());

			stmt.executeUpdate();

			client.setFav_book(book.getId());
			opReussie = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return opReussie;
	}

	public static boolean update(Client client) {
		boolean opReussie = false;
		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement stmt = conn.prepareStatement(
						"UPDATE client SET lastname = ?, firstname = ?, gender = ?, id_favourite_book = ? WHERE id = ?")) {
			stmt.setString(1, client.getLastname());
			stmt.setString(2, client.getFirstname());
			stmt.setString(3, client.getGender().toString());
			stmt.setInt(4, client.getFav_book());
			stmt.setInt(5, client.getId());

			stmt.executeUpdate();
			opReussie = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return opReussie;
	}

}
