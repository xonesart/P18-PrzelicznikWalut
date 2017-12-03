package waluty;

public class WypiszDzisiejszeKursy {

	public static void main(String[] args) {
		Tabela tabela = ObslugaNBP.pobierzAktualnaTabele();
		
		for(Waluta waluta : tabela.getWszystkieWaluty()) {
			System.out.println(waluta);
		}
	}
}
