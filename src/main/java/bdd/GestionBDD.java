package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestionBDD {
	private GestionBDD() {
	}

	private static String url = "jdbc:postgresql://localhost:5432/formation-SQL-DTA";
	private static String username = "postgres";
	private static String password = "postgres";

	/*
	 * Pour la création des tables
	 */
	public static boolean creationTables() {
		boolean opReussie = false;

		try (Connection conn = DriverManager.getConnection(url, username, password);
				Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(createBook());
			stmt.executeUpdate(createClient());
			stmt.executeUpdate(createBuyed());
			opReussie = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return opReussie;
	}

	/*
	 * Pour supprimer les tables DANGER !!!
	 */
	public static boolean dropAllTables() {
		boolean opReussie = false;

		try (Connection conn = DriverManager.getConnection(url, username, password);
				Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("DROP TABLE Book CASCADE");
			stmt.executeUpdate("DROP TABLE Client CASCADE");
			stmt.executeUpdate("DROP TABLE Buyed CASCADE");
			opReussie = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return opReussie;
	}

	/*
	 * Pour tronc les tables et repartir à zéro au niveau des données
	 */
	public static boolean truncateAllTables() {
		boolean opReussie = false;

		try (Connection conn = DriverManager.getConnection(url, username, password);
				Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("TRUNCATE Buyed CASCADE");
			stmt.executeUpdate("TRUNCATE Book CASCADE");
			stmt.executeUpdate("TRUNCATE Client CASCADE");
			opReussie = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return opReussie;
	}

	/*
	 * Méthode pour qu'un client achete un livre
	 */
	public static boolean achatLivreClient(Book book, Client client) {
		boolean opReussie = false;

		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO Buyed(id_book, id_client) VALUES(?, ?);")) {

			stmt.setInt(1, book.getId());
			stmt.setInt(2, client.getId());

			stmt.executeUpdate();

			opReussie = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return opReussie;
	}

	/*
	 * Méthode pour avoir les livres achetés par un client
	 */
	public static List<Book> livresAchetesPar(Client client) {
		List<Book> list = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement stmt = conn.prepareStatement(
						"SELECT Book.*  FROM Book JOIN Buyed ON Book.id = Buyed.id_book WHERE Buyed.id_client = ?")) {

			stmt.setInt(1, client.getId());

			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				list.add(new Book(result.getInt("id"), result.getString("title"), result.getString("author")));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return list;
	}

	/*
	 * Méthode pour avoir les clients qui ont acheté un certain livre
	 */
	public static List<Client> clientsAyantAchete(Book book) {
		List<Client> list = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement stmt = conn.prepareStatement(
						"SELECT Client.* FROM Client JOIN Buyed ON Client.id = Buyed.id_client WHERE Buyed.id_book = ?")) {

			stmt.setInt(1, book.getId());

			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				list.add(new Client(result.getInt("id"), result.getString("lastname"), result.getString("firstname"),
						Gender.valueOf(result.getString("gender")), result.getInt("id_favourite_book")));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return list;
	}

	/*
	 * 
	 * Méthodes renvoyant des requetes sql
	 * 
	 */

	private static String createBook() {
		return "CREATE TABLE IF NOT EXISTS Book (" + "id BIGSERIAL NOT NULL," + "title VARCHAR(255) NOT NULL,"
				+ "author VARCHAR(255) NOT NULL," + "CONSTRAINT book_primary_key PRIMARY KEY (id)" + ");";
	}

	private static String createClient() {
		return "CREATE TABLE IF NOT EXISTS Client (" + "id BIGSERIAL NOT NULL," + "lastname VARCHAR(30) NOT NULL,"
				+ "firstname VARCHAR(30) NOT NULL," + "gender VARCHAR(1) NOT NULL,"
				+ "id_favourite_book INTEGER CONSTRAINT fk_id_fav_book REFERENCES Book(id),"
				+ "CONSTRAINT client_primary_key PRIMARY KEY (id)" + ");";
	}

	private static String createBuyed() {
		return "CREATE TABLE IF NOT EXISTS Buyed (" + "id_book INTEGER CONSTRAINT fk_id_book REFERENCES Book(id),"
				+ "id_client INTEGER CONSTRAINT fk_id_client REFERENCES Client(id)" + ");";
	}
}
