package waluty;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class PrzelicznikWalut2 {
	private static final Font FONT_PRZYCISKOW = new Font("Dialog", Font.BOLD, 40);
	private static final Color KOLOR_ZWYKLY = Color.BLACK;
	private static final Color KOLOR_BLAD = Color.RED;
	private static final Color KOLOR_ZMIANA = new Color(0x008800);
	
	private JFrame frmPrzelicznik;
	private JTextField textField_kwotaWWalucie;
	private JTextField textField_kwotaWZlotych;
	private Tabela tabela = null;
	private JComboBox<String> comboBox_Waluta;
	private Kierunek ostatnio;

	public static void main(String[] args) {
		PrzelicznikWalut2 window = new PrzelicznikWalut2();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.initialize();
					window.frmPrzelicznik.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Tabela tabelaTmp = ObslugaNBP.pobierzAktualnaTabele();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				window.ustawTabele(tabelaTmp);
			}
		});
		
	}

	private void ustawTabele(Tabela tabela) {
		this.tabela = tabela;
		
		comboBox_Waluta.setModel(
				new DefaultComboBoxModel<>(tabela.getKodyWalut()));
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPrzelicznik = new JFrame();
		frmPrzelicznik.setFont(new Font("Dialog", Font.PLAIN, 32));
		frmPrzelicznik.setTitle("Przelicznik walut");
		frmPrzelicznik.setBounds(100, 100, 794, 380);
		frmPrzelicznik.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblWybierzWalut = new JLabel("Wybierz walutę:");
		lblWybierzWalut.setFont(new Font("Dialog", Font.BOLD, 24));
		
		comboBox_Waluta = new JComboBox<>();
		comboBox_Waluta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if(ostatnio == Kierunek.PLN_NA_WALUTE) {
					przelicz(ostatnio, textField_kwotaWZlotych, textField_kwotaWWalucie);
				}
				if(ostatnio == Kierunek.WALUTA_NA_PLN) {
					przelicz(ostatnio, textField_kwotaWWalucie, textField_kwotaWZlotych);
				}
			}
		});
		comboBox_Waluta.setFont(new Font("Dialog", Font.BOLD, 22));
		comboBox_Waluta.setModel(new DefaultComboBoxModel<>(new String[] {"pobieranie..."}));
		
		JLabel lblKwotaWWalucie = new JLabel("Kwota w walucie");
		lblKwotaWWalucie.setFont(new Font("Dialog", Font.BOLD, 20));
		
		textField_kwotaWWalucie = new JTextField();
		textField_kwotaWWalucie.setFont(new Font("Dialog", Font.PLAIN, 24));
		textField_kwotaWWalucie.setColumns(10);
		
		JLabel lblKwotaWZotych = new JLabel("Kwota w złotych");
		lblKwotaWZotych.setFont(new Font("Dialog", Font.BOLD, 20));
		
		textField_kwotaWZlotych = new JTextField();
		textField_kwotaWZlotych.setFont(new Font("Dialog", Font.PLAIN, 24));
		textField_kwotaWZlotych.setColumns(10);
		
		JButton btn_PlnNaWalute = new JButton("\u2b06");// 2b06   ⬆
		btn_PlnNaWalute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				przelicz(Kierunek.PLN_NA_WALUTE, textField_kwotaWZlotych, textField_kwotaWWalucie);
			}
		});
		btn_PlnNaWalute.setFont(FONT_PRZYCISKOW);
		
		JButton btn_WalutaNaPln = new JButton("⬇");
		btn_WalutaNaPln.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				przelicz(Kierunek.WALUTA_NA_PLN, textField_kwotaWWalucie, textField_kwotaWZlotych);
			}
		});
		btn_WalutaNaPln.setFont(FONT_PRZYCISKOW);
		
		
		GroupLayout groupLayout = new GroupLayout(frmPrzelicznik.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(textField_kwotaWZlotych)
						.addComponent(lblKwotaWZotych, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(textField_kwotaWWalucie)
						.addComponent(lblKwotaWWalucie, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblWybierzWalut)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBox_Waluta, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_PlnNaWalute, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_WalutaNaPln, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(190, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWybierzWalut)
						.addComponent(comboBox_Waluta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblKwotaWWalucie)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btn_PlnNaWalute, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
							.addComponent(btn_WalutaNaPln, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textField_kwotaWWalucie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblKwotaWZotych)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_kwotaWZlotych, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(114, Short.MAX_VALUE))
		);
		frmPrzelicznik.getContentPane().setLayout(groupLayout);
	}
	
	private void przelicz(Kierunek kierunek, JTextField skad, JTextField dokad) {
		try {
			skad.setForeground(KOLOR_ZWYKLY);
			String kod = (String) comboBox_Waluta.getSelectedItem();
			Waluta waluta = tabela.znajdz(kod);
			
			String kwotaTxt = skad.getText();
			kwotaTxt = kwotaTxt.trim().replace(',', '.');
			BigDecimal kwota = new BigDecimal(kwotaTxt);
			
			BigDecimal wynik = null;
			if(kierunek == Kierunek.PLN_NA_WALUTE) {
				wynik = waluta.przeliczZloteNaWalute(kwota);
			} else {
				wynik = waluta.przeliczWaluteNaZlote(kwota);
			}
			
			dokad.setText(wynik.toString());
			dokad.setForeground(KOLOR_ZMIANA);
			ostatnio = kierunek;
		} catch (Exception e) {
			dokad.setText("błąd");
			dokad.setForeground(KOLOR_BLAD);
		}
	}
	
	
	private enum Kierunek {
		PLN_NA_WALUTE, WALUTA_NA_PLN;
	}
}
