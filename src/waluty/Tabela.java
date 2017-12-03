package waluty;

import java.time.LocalDate;
import java.util.Collection;
import java.util.TreeMap;
import java.util.Map;

public class Tabela {
	private final String nazwaTabeli;
	private final String numerTabeli;
	private final LocalDate data;
	private final Map<String, Waluta> waluty = new TreeMap<>();
	
	public Tabela() {
		nazwaTabeli = null;
		numerTabeli = null;
		data = null;
	}

	public Tabela(String nazwaTabeli, String numerTabeli, LocalDate data) {
		this.nazwaTabeli = nazwaTabeli;
		this.numerTabeli = numerTabeli;
		this.data = data;
	}

	public String getNazwaTabeli() {
		return nazwaTabeli;
	}

	public String getNumerTabeli() {
		return numerTabeli;
	}

	public LocalDate getData() {
		return data;
	}

	@Override
	public String toString() {
		return "Tabela " + nazwaTabeli + " nr " + numerTabeli
				+ " z dnia " + data + " (" + waluty.size() + " walut)";
	}
	
	public void dodaj(Waluta waluta) {
		waluty.put(waluta.getKod(), waluta);
	}
	
	public Waluta znajdz(String kod) {
		return waluty.get(kod);
	}
	
	public Collection<Waluta> getWszystkieWaluty() {
		return waluty.values();
	}
	
	private static final String[] PUSTA_TABLICA = new String[0];

	public String[] getKodyWalut() {
		return waluty.keySet().toArray(PUSTA_TABLICA);
	}
	
}
