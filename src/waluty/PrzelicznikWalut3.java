package waluty;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingWorker;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;

public class PrzelicznikWalut3 {
	
	private static final Font FONT_LABEL = new Font("Dialog", Font.PLAIN, 24);
	private static final Font FONT_WARTOSC = new Font("Dialog", Font.BOLD, 24);
	private static final Font FONT_TXT = new Font("Dialog", Font.BOLD, 32);
	private static final Font FONT_WYNIK = new Font("Dialog", Font.BOLD, 32);	
	private static final Font FONT_BUTTON = new Font("Dialog", Font.BOLD, 28);

	private JFrame frmPrzelicznikWalut;
	private JTextField txtKwota;
	private JComboBox<String> comboBox_Waluta;
	private JLabel lbl_KodWaluty;
	private JLabel lbl_NazwaWaluty;
	private JLabel lbl_KursWaluty;
	private JRadioButton rdbtnWalutaNaZlote;
	private JRadioButton rdbtnZloteNaWalute;
	private JButton btnPrzelicz;
	private JLabel lblPrawdziwyWynik;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private Tabela tabela = null;
	private JPanel panel_1;
	private JLabel lblTabela;
	private JLabel lbl_NumerTabeli;
	private JLabel lblData;
	private JLabel lbl_DataTabeli;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrzelicznikWalut3 window = new PrzelicznikWalut3();
					window.frmPrzelicznikWalut.setVisible(true);
					window.pobierzAktualneKursy();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PrzelicznikWalut3() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPrzelicznikWalut = new JFrame();
		frmPrzelicznikWalut.setTitle("Przelicznik Walut");
		frmPrzelicznikWalut.setBounds(100, 100, 480, 600);
		frmPrzelicznikWalut.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblWybierzWalut = new JLabel("Wybierz walutę");
		lblWybierzWalut.setFont(FONT_LABEL);
		
		comboBox_Waluta = new JComboBox<>();
		comboBox_Waluta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				wyswietlDaneWaluty();
			}
		});
		comboBox_Waluta.setModel(
				new DefaultComboBoxModel(new String[] {"--"}));
		
		comboBox_Waluta.setFont(FONT_WARTOSC);
		
		JPanel panel = new JPanel();
		
		txtKwota = new JTextField();
		txtKwota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				przelicz();
			}
		});
		txtKwota.setText("100");
		txtKwota.setColumns(10);
		txtKwota.setFont(FONT_TXT);
		
		JLabel lblPodajKwot = new JLabel("Podaj kwotę");
		lblPodajKwot.setFont(FONT_LABEL);
		
		rdbtnWalutaNaZlote = new JRadioButton("waluta na złote");
		rdbtnWalutaNaZlote.setFont(FONT_LABEL);
		buttonGroup.add(rdbtnWalutaNaZlote);
		
		rdbtnZloteNaWalute = new JRadioButton("złote na walute");
		rdbtnZloteNaWalute.setFont(FONT_LABEL);
		buttonGroup.add(rdbtnZloteNaWalute);
		
		btnPrzelicz = new JButton("Przelicz");
		btnPrzelicz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				przelicz();
			}
		});
		btnPrzelicz.setFont(FONT_BUTTON);
		
		JLabel lblWynik = new JLabel("Wynik");
		lblWynik.setFont(FONT_LABEL);
		
		lblPrawdziwyWynik = new JLabel("0.00");
		lblPrawdziwyWynik.setFont(FONT_WYNIK);
		lblPrawdziwyWynik.setForeground(Color.GRAY);
		
		panel_1 = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frmPrzelicznikWalut.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
						.addComponent(btnPrzelicz, GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(rdbtnWalutaNaZlote)
							.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
							.addComponent(rdbtnZloteNaWalute))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblPodajKwot)
							.addPreferredGap(ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
							.addComponent(txtKwota, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblWybierzWalut)
							.addGap(89)
							.addComponent(comboBox_Waluta, 0, 148, Short.MAX_VALUE))
						.addComponent(lblWynik)
						.addComponent(lblPrawdziwyWynik))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox_Waluta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblWybierzWalut))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtKwota, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPodajKwot))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnWalutaNaZlote)
						.addComponent(rdbtnZloteNaWalute))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPrzelicz)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblWynik)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblPrawdziwyWynik)
					.addGap(33))
		);
		
		lblTabela = new JLabel("Tabela");
		lblTabela.setFont(new Font("Dialog", Font.PLAIN, 24));
		
		lbl_NumerTabeli = new JLabel("?");
		lbl_NumerTabeli.setFont(new Font("Dialog", Font.BOLD, 24));
		
		lblData = new JLabel("Data:");
		lblData.setFont(new Font("Dialog", Font.PLAIN, 24));
		
		lbl_DataTabeli = new JLabel("?");
		lbl_DataTabeli.setFont(new Font("Dialog", Font.BOLD, 24));
		
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblTabela, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lbl_NumerTabeli, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblData, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lbl_DataTabeli, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTabela, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl_NumerTabeli, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblData, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl_DataTabeli, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblKod = new JLabel("Kod:");
		lblKod.setFont(FONT_LABEL);
		
		JLabel lblNazwa = new JLabel("Nazwa:");
		lblNazwa.setFont(FONT_LABEL);
		
		JLabel lblKurs = new JLabel("Kurs:");
		lblKurs.setFont(FONT_LABEL);
		
		lbl_KodWaluty = new JLabel("EUR");
		lbl_KodWaluty.setFont(FONT_WARTOSC);
		
		lbl_NazwaWaluty = new JLabel("euro");
		lbl_NazwaWaluty.setFont(FONT_WARTOSC);
		
		lbl_KursWaluty = new JLabel("4.0000");
		lbl_KursWaluty.setFont(FONT_WARTOSC);

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblKurs, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNazwa, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblKod, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED, 18, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_KodWaluty, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
						.addComponent(lbl_NazwaWaluty, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
						.addComponent(lbl_KursWaluty, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKod)
						.addComponent(lbl_KodWaluty))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNazwa)
						.addComponent(lbl_NazwaWaluty))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKurs)
						.addComponent(lbl_KursWaluty))
					.addContainerGap(61, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		frmPrzelicznikWalut.getContentPane().setLayout(groupLayout);
	}
	
	protected void przelicz() {
		try {
			BigDecimal kwota = new BigDecimal(txtKwota.getText());
			String kod = "" + comboBox_Waluta.getSelectedItem();
			Waluta waluta = tabela.znajdz(kod);
			BigDecimal wynik = null;
			if(rdbtnWalutaNaZlote.isSelected()) {
				wynik = waluta.przeliczWaluteNaZlote(kwota);
			}
			if(rdbtnZloteNaWalute.isSelected()) {
				wynik = waluta.przeliczZloteNaWalute(kwota)
;
			}
			lblPrawdziwyWynik.setText("" + wynik);
			lblPrawdziwyWynik.setForeground(Color.BLUE);
		} catch (Exception e) {
			lblPrawdziwyWynik.setText("błąd");
			lblPrawdziwyWynik.setForeground(Color.RED);
		}		
	}

	protected void wyswietlDaneWaluty() {
		String kod = "" + comboBox_Waluta.getSelectedItem();
		Waluta waluta = tabela.znajdz(kod);
		if(waluta != null) {
			lbl_KodWaluty.setText(waluta.getKod());
			lbl_NazwaWaluty.setText(waluta.getNazwa());
			lbl_KursWaluty.setText("" + waluta.getKurs());
		}
		lblPrawdziwyWynik.setText("0.00");
		lblPrawdziwyWynik.setForeground(Color.GRAY);
	}

	private void pobierzAktualneKursy() {
		// Pobieranie aktualnych kursów w tle (to znaczy w innym wątku)
		// Aby wykonać operację w tle, w Swingu najlepiej użyć klasy SwingWorker
		
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			protected Void doInBackground() throws Exception {
				tabela = ObslugaNBP.pobierzAktualnaTabele();
				return null;
			}
			
			protected void done() {
				// tu piszemy "co ma zrobić okno, gdy operacja jest zakończona"
				odswiezDaneTabeli();
				wyswietlDaneWaluty();
			}
		};
		
		worker.execute();
	}

	private void odswiezDaneTabeli() {
		lbl_NumerTabeli.setText(tabela.getNumerTabeli());
		lbl_DataTabeli.setText("" + tabela.getData());
		comboBox_Waluta.setModel(new DefaultComboBoxModel<>(tabela.getKodyWalut()));
	}
	
}
