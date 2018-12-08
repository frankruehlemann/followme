import java.lang.IllegalArgumentException;

public class kalibrierung {

private static kalibrierung instance;
	private kalibrierung () {}

	public static synchronized kalibrierung getInstance () {
		if (kalibrierung.instance == null) {
			kalibrierung.instance = new kalibrierung ();
		}
		return kalibrierung.instance;
	}
}
