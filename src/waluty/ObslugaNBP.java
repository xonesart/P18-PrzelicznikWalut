package waluty;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ObslugaNBP {
	
	public static Tabela pobierzAktualnaTabele() {
		Document doc = wczytajXmlZAdresu(
				"http://api.nbp.pl/api/exchangerates/tables/A?format=xml");
		
		return tabelaZXml(doc);
	}
	
	// TODO  dodac metode pobierzTabeleDlaDaty(data)
	
	
	private static Document wczytajXmlZAdresu(String adres) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			URL url = new URL(adres);
			try(InputStream in = url.openStream()) {
				return db.parse(in);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private static Tabela tabelaZXml(Document doc) {
		try {
			XPathFactory xpf = XPathFactory.newInstance();
			XPath xpath = xpf.newXPath();
			
//			String numer = xpath.evaluate(
//					"/ArrayOfExchangeRatesTable/ExchangeRatesTable/No", doc);

			String nazwaTabeli = xpath.evaluate("//Table", doc);
			String numerTabeli = xpath.evaluate("//No", doc);
			LocalDate data = LocalDate.parse(xpath.evaluate("//EffectiveDate", doc));
			
			Tabela tabela = new Tabela(nazwaTabeli, numerTabeli, data);
			
			NodeList rates = (NodeList) xpath.evaluate("//Rate", doc, XPathConstants.NODESET);
			final int n = rates.getLength();
			for(int i = 0; i < n; i++) {
				Node rate = rates.item(i);
				String kod = xpath.evaluate("Code", rate);
				String nazwa = xpath.evaluate("Currency", rate);
				String kurs = xpath.evaluate("Mid", rate);
				Waluta waluta = new Waluta(kod, nazwa, kurs);
				tabela.dodaj(waluta);
			}
			
			return tabela;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			return null;
		}
	}
}
