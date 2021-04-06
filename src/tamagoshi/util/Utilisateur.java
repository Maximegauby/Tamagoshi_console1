package tamagoshi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class Utilisateur {

	// Déclaration du logger
	private static final Logger logger = Logger.getLogger(Utilisateur.class.toString());

	public static String saisieClavier() {

		/*
		 * il faut gérer les exceptions car l'entrée standard peut ne pas être
		 * disponible : le constructeur de la classe InputStreamReader peut renvoyer une
		 * exception.
		 */
		try {
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
			return clavier.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0); // on arrête le programme sur une telle erreur
			return null;
		}
	}

	// une méthode main juste pour tester
	public static void main(String[] args) {
		String saisie = Utilisateur.saisieClavier();
		logger.info("la saisie est : " + saisie);
	}
}
