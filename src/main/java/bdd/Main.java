package bdd;

import java.util.List;

public class Main {

	public static void main(String[] args) {

		if (GestionBDD.dropAllTables()) {
			System.out.println("Tables dropées !");
		}

		if (GestionBDD.creationTables()) {
			System.out.println("Tables crées !");
		}

		if (GestionBDD.truncateAllTables()) {
			System.out.println("Tables tronquées !");
		}

		List<Client> listeClient = MyFactory.randomClientList(30);
		List<Book> listeLivres = MyFactory.randomBookList(6);

		ClientDAO.ajoutClientListBDD(listeClient);

		BookDAO.ajoutBookListBDD(listeLivres);

		for (int i = 0; i < listeClient.size(); i++) {
			for (int j = 0; j < randInt(1, listeLivres.size() - 1); j++) {
				GestionBDD.achatLivreClient(listeLivres.get(j), listeClient.get(i));
			}
		}

		List<Client> ayantAchete = GestionBDD.clientsAyantAchete(listeLivres.get(2));
		System.out.println("Le livre : " + listeLivres.get(2) + " a été acheté par :");
		for (Client c : ayantAchete) {
			System.out.println(c);
		}

		List<Book> achetesPar = GestionBDD.livresAchetesPar(listeClient.get(12));
		System.out.println("Le client : " + listeClient.get(12) + " a acheté :");
		for (Book b : achetesPar) {
			System.out.println(b);
		}
	}

	private static int randInt(int min, int max) {
		return min + (int) (Math.random() * (max - min));
	}

}
