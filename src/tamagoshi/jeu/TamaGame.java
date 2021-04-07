package tamagoshi.jeu;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import tamagoshi.tamagoshis.GrosJoueur;
import tamagoshi.tamagoshis.GrosMangeur;
import tamagoshi.tamagoshis.Tamagoshi;
import tamagoshi.util.Utilisateur;

/**
 * un petit jeu avec des tamagoshis
 */
public class TamaGame {

	/**
	 * Initialisation du logger
	 */
	private static final Logger logger = Logger.getLogger(TamaGame.class.toString());

	/**
	 * contains all the initial tamagoshis
	 */
	private List<Tamagoshi> allTamagoshis;
	/**
	 * contains only alive tamagoshis
	 */
	private List<Tamagoshi> aliveTamagoshis;

	/** build the game */
	private TamaGame() {
		allTamagoshis = new ArrayList<Tamagoshi>();
		logger.setLevel(Level.ALL);
		aliveTamagoshis = new ArrayList<Tamagoshi>();
		initialisation();
	}

	@SuppressWarnings("unchecked")
	private void initialisation() {
		System.out.println("Entrez le nombre de tamagoshis désiré !");
		int n = 0;
		while (n < 1) {
			System.out.println("Saisisez un nombre > 0 :");
			try {
				n = Integer.parseInt(Utilisateur.saisieClavier());
				logger.info("L'utilisateur a saisi : " +n);
			} catch (NumberFormatException e) {
				System.out.println("Il faut saisir un nombre !");
				logger.warning("L'utilisateur n'a rien saisi.");
			}
		}
		for (int i = 0; i < n; i++) {
			System.out.println("Entrez le nom du tamagoshi numéro " + i + " : ");
			String saisie = Utilisateur.saisieClavier();

			if (Math.random() < .5) {
				allTamagoshis.add(new GrosJoueur(saisie));
				logger.info("L'utilisateur a saisi le nom: " + saisie + " qui sera un gros joueur.");
			} else {
				allTamagoshis.add(new GrosMangeur(Utilisateur.saisieClavier()));
				logger.info("L'utilisateur a saisi le nom: " + saisie + " qui sera un gros mangeur.");
			}

		}

		 aliveTamagoshis = new ArrayList<Tamagoshi>(allTamagoshis);
	}

	/**
	 * returns the selected tamagoshi
	 * 
	 * @param question the question to ask to the user
	 * @return the selected instance
	 */

	private Tamagoshi tamaSelection(String question) {
		System.out.println(question);
		int index = 0;
		for (ListIterator<Tamagoshi> iterator = aliveTamagoshis.listIterator(); iterator.hasNext();) {
			System.out.print("\t(" + (iterator.nextIndex()) + ") " + iterator.next().getName() + "  ");
		}
		System.out.print("\n\tEntrez un choix : ");
		try {
			index = Integer.parseInt(Utilisateur.saisieClavier());
			logger.info("L'utilisateur a saisi le tama "+ index);
		} catch (NumberFormatException e) {
			System.out.println("Il faut saisir un nombre !");
			logger.warning("L'utilisateur n'a rien saisi.");
			return tamaSelection(question);
		}
		if (index < 0 || index >= aliveTamagoshis.size()) {
			System.out.println("il n'y a pas de tamagoshi portant le numéro " + index);
			logger.warning("L'utilisateur a saisi un index de Tamagoshi inexistant.");
			return tamaSelection(question);
		}
		return aliveTamagoshis.get(index);
	}

	/**
	 * Starts the game
	 */
	public void play() {
		int cycle = 1;
		while (! aliveTamagoshis.isEmpty()) {
			System.out.println("\n------------Cycle n°" + (cycle++) + "-------------");
			for (Tamagoshi t : aliveTamagoshis)
				t.parle();
			tamaSelection("\n\tNourrir quel tamagoshi ?").mange();
			tamaSelection("\n\tJouer avec quel tamagoshi ?").joue();
			
			for (Iterator<Tamagoshi> iterator = aliveTamagoshis.iterator(); iterator.hasNext();) {
				Tamagoshi t = iterator.next();
				if (!t.consommeEnergie() || !t.consommeFun() || t.vieillit())
					iterator.remove();
			}
		}
		System.out.println("\n\t--------- fin de partie !! ----------------\n\n");
		resultat();
	}

	private double score() {
		int score = 0;
		for (Tamagoshi t : allTamagoshis)
			score += t.getAge();
		return score * 100 / (Tamagoshi.getLifeTime() * allTamagoshis.size());
	}

	private void resultat() {
		System.out.println("-------------bilan------------");
		for (Tamagoshi t : allTamagoshis) {
			String classe = t.getClass().getSimpleName();
			System.out.println(t.getName() + " qui était un " + classe + " "
					+ (t.getAge() == Tamagoshi.getLifeTime() ? " a survécu et vous remercie :)"
							: " n'est pas arrivé au bout et ne vous félicite pas :("));
		}
		System.out.println("\nniveau de difficulté : " + allTamagoshis.size() + ", score obtenu : " + score() + "%");
		logger.fine("fin de la partie");

		sauvegardeScores();
	}

	/**
	 * Sauvegarde le score de la partie dans un fichier prop externe
	 */
	private void sauvegardeScores() {
		String propertiesFileLocation = "scores.properties";
		Properties myProps = new Properties();

		double score = score();

		try {
			OutputStream out = new FileOutputStream((propertiesFileLocation));
			myProps.store(out, "L'utilisateur a obtenu" + score + " points.");
			logger.info("Sauvegarde du score.");
		} catch (IOException e) {
			logger.warning("La sauvegarde a échouée.");
		}

	}

	/** Launch a new instance of the game */
	public static void main(String[] args) throws IOException {
		FileHandler fh = new FileHandler("tama.log");
		logger.addHandler(fh);
		logger.setLevel(Level.ALL);

		TamaGame jeu = new TamaGame();
		jeu.play();
	}
	
	@Override
	public String toString() {
		return "tamagame";
	}

}
