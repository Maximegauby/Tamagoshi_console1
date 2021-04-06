//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package tamagoshi.tamagoshis;

public class GrosJoueur extends Tamagoshi {
	public GrosJoueur(String name) {
		super(name);
	}

	public boolean consommeEnergie() {
		--this.fun;
		return super.consommeEnergie();
	}
}
