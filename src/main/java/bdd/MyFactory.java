package bdd;

import java.util.ArrayList;
import java.util.List;

public class MyFactory {
	private static Prenom[] prenoms = Prenom.values();
	private static Nom[] noms = Nom.values();
	private static Gender[] genres = Gender.values();
	private static Titres[] titres = Titres.values();

	private MyFactory() {
	}

	public static List<Client> randomClientList() {
		return randomClientList(30);
	}

	public static List<Client> randomClientList(int n) {
		List<Client> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			list.add(randomClient());
		}
		return list;
	}

	public static List<Book> randomBookList() {
		return randomBookList(10);
	}

	public static List<Book> randomBookList(int n) {
		List<Book> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			list.add(randomBook());
		}
		return list;
	}

	public static Client randomClient() {
		return new Client(randomNom(), randomPrenom(), randomGender());
	}

	public static Book randomBook() {
		return new Book(randomTitre(), randomAuthor());
	}

	// RANDOMS

	private static String randomTitre() {
		int l = titres.length;

		return titres[randInt(0, l)].toString();
	}

	private static String randomAuthor() {
		return randomNom() + " " + randomPrenom();
	}

	private static String randomPrenom() {
		int l = prenoms.length;

		return prenoms[randInt(0, l)].toString();
	}

	private static String randomNom() {
		int l = noms.length;

		return noms[randInt(0, l)].toString();
	}

	private static Gender randomGender() {
		int l = genres.length;

		return genres[randInt(0, l)];
	}

	private static int randInt(int min, int max) {
		return min + (int) (Math.random() * (max - min));
	}
}
