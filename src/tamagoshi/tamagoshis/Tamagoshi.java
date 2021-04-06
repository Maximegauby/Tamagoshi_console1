//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package tamagoshi.tamagoshis;

import java.util.Random;
import java.util.logging.Logger;

public class Tamagoshi implements Runnable {
	private String name;
	private Random generateur;
	private int age;
	private int maxEnergy;
	private int maxFun;
	protected int fun;
	protected int energy;
	private static int lifeTime = 10;
	private int pause = 1000;

	private static final Logger logger = Logger.getLogger(Tamagoshi.class.toString());

	public Tamagoshi(String name) {
		this.name = name;
		this.generateur = new Random();
		this.age = 0;
		this.maxEnergy = this.generateur.nextInt(5) + 5;
		this.maxFun = this.generateur.nextInt(5) + 5;
		this.energy = this.generateur.nextInt(5) + 3;
		this.fun = this.generateur.nextInt(5) + 3;
	}

	public boolean parle() {
		String s = "";
		if (this.energy < 5) {
			s = s + "je suis affamé";
		}

		if (this.fun < 5) {
			if (!s.equals("")) {
				s = s + " et ";
			}

			s = s + "je m'ennuie à mourrir";
		}

		if (s.equals("")) {
			this.parler("Tout va bien !", false);
			return true;
		} else {
			this.parler(s + " !", false);
			return false;
		}
	}

	private void parler(String phrase, boolean reponse) {
		System.out.println("\n\t" + this.name + " : \"" + phrase + "\"");
	}

	public boolean mange() {
		if (this.energy < this.maxEnergy) {
			this.energy += this.generateur.nextInt(3) + 1;
			this.parler("Merci !", true);
			return true;
		} else {
			this.parler("je n'ai pas faim !!", true);
			return false;
		}
	}

	public boolean vieillit() {
		++this.age;
		return this.age == getLifeTime();
	}

	public boolean consommeEnergie() {
		--this.energy;
		if (this.energy <= 0) {
			this.parler("je suis KO: Arrrggh !", false);
			return false;
		} else {
			return true;
		}
	}

	public boolean consommeFun() {
		--this.fun;
		if (this.fun <= 0) {
			this.parler("snif : je fais une dépression, ciao!", false);
			return false;
		} else {
			return true;
		}
	}

	public int getAge() {
		return this.age;
	}

	public String getName() {
		return this.name;
	}

	public static void setLifeTime(int lifeTime) {
		Tamagoshi.lifeTime = lifeTime;
	}

	public static int getLifeTime() {
		return lifeTime;
	}

	public boolean joue() {
		if (this.fun < this.maxFun) {
			this.fun += this.generateur.nextInt(3) + 1;
			this.parler("On se marre !", true);
			return true;
		} else {
			this.parler("laisse-moi tranquille, je bouquine !!", true);
			return false;
		}
	}

	public String toString() {
		return this.name + " : energy=" + this.energy + ", fun=" + this.fun;
	}

	public boolean isAlive() {
		return this.fun > 0 && this.energy > 0;
	}

	public void run() {
		this.parle();

		do {
			try {
				Thread.sleep((long)this.pause);
				this.vieillit();
				this.parle();
				this.pause -= this.generateur.nextInt(50);
			} catch (InterruptedException var2) {
			}
		} while(this.consommeEnergie() && this.consommeFun());


	}

	public void setInitialPause(int i) {
		this.pause = i;
	}

	public void decreasePause(int i) {
		this.pause -= i;
	}

	public static void main(String[] args) {
		for(int i = 0; i < 5; ++i) {
			Thread t = new Thread(new Tamagoshi("test" + i));
			t.start();
		}

	}
}
