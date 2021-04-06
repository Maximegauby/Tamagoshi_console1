package tamagoshi.tamagoshis;

public class GrosMangeur extends Tamagoshi {

	public GrosMangeur(String name) {
		super(name);
	}

	@Override
	public boolean consommeFun() {
		energy--;
		return super.consommeFun();
	}
}
