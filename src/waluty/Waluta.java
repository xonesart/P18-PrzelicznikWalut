package waluty;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Waluta {
	private final String kod;
	private final String nazwa;
	private final BigDecimal kurs;
	
	public Waluta() {
		// Konstruktor bezargumentowy ("domyslny") jest wymagany przez różne technologie, np. Spring, Hibernate, JSP, JAXB, beans binding - warto tworzyć go we własnych klasach
		this(null, null, BigDecimal.ONE);
	}
	
	public Waluta(String kod, String nazwa, BigDecimal kurs) {
		this.kod = kod;
		this.nazwa = nazwa;
		this.kurs = kurs;
	}

	public Waluta(String kod, String nazwa, String kurs) {
		this(kod, nazwa, new BigDecimal(kurs));
	}

	public String getKod() {
		return kod;
	}

	public String getNazwa() {
		return nazwa;
	}

	public BigDecimal getKurs() {
		return kurs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kod == null) ? 0 : kod.hashCode());
		result = prime * result + ((kurs == null) ? 0 : kurs.hashCode());
		result = prime * result + ((nazwa == null) ? 0 : nazwa.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Waluta other = (Waluta) obj;
		if (kod == null) {
			if (other.kod != null)
				return false;
		} else if (!kod.equals(other.kod))
			return false;
		if (kurs == null) {
			if (other.kurs != null)
				return false;
		} else if (!kurs.equals(other.kurs))
			return false;
		if (nazwa == null) {
			if (other.nazwa != null)
				return false;
		} else if (!nazwa.equals(other.nazwa))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return kod + " (" + nazwa + ") -  " + kurs;
	}

	public BigDecimal przeliczZloteNaWalute(BigDecimal kwota) {		
		return kwota.divide(kurs, 2, RoundingMode.HALF_UP);
	}

	public BigDecimal przeliczWaluteNaZlote(BigDecimal kwota) {
		return kwota.multiply(kurs).setScale(2, RoundingMode.HALF_UP);
	}
}
