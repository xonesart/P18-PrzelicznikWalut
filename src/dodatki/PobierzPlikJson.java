package dodatki;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PobierzPlikJson {

	public static void main(String[] args) {
		
		try {
			// tworzenie obiektu URL - sprawdzana tylko składnia
			URL url = new URL("http://api.nbp.pl/api/exchangerates/tables/A");
			
			// nawiąwanie połączenia sieciowego
			
			try(InputStream dane = url.openStream()) {
				// pobranie danych - używam prostego zapisu dostepnego od Javy 7
				
				Files.copy(dane, Paths.get("waluty.json"),
						StandardCopyOption.REPLACE_EXISTING);
				
				System.out.println("Gotowe");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
