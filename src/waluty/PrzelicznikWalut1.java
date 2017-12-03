package waluty;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.util.concurrent.ExecutionException;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PrzelicznikWalut1 {

	private static final Font FONT_LABEL = new Font("Dialog", Font.PLAIN, 24);
	private static final Font FONT_DANE = new Font("Dialog", Font.BOLD, 24);
	private JFrame frmPrzelicznikWalut;
	private JLabel lblNrTabeli;
	private JLabel lblDataTabeli;
	private JComboBox<String> comboBoxWaluta;
	private JLabel lblNazwaWaluty;
	private JLabel lblKurs_1;
	private Tabela tabela;
	private Waluta wybranaWaluta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrzelicznikWalut1 window = new PrzelicznikWalut1();
					window.frmPrzelicznikWalut.setVisible(true);
					window.pobierzTabele();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PrzelicznikWalut1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPrzelicznikWalut = new JFrame();
		frmPrzelicznikWalut.setTitle("Przelicznik Walut");
		frmPrzelicznikWalut.setBounds(100, 100, 678, 593);
		frmPrzelicznikWalut.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		JPanel panel_1 = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frmPrzelicznikWalut.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(271, Short.MAX_VALUE))
		);
		
		JLabel lblWybierzWalut = new JLabel("Wybierz walutę");
		lblWybierzWalut.setFont(new Font("Dialog", Font.PLAIN, 24));
		
		comboBoxWaluta = new JComboBox<>();
		comboBoxWaluta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				zmianaWaluty();
			}
		});
		comboBoxWaluta.setModel(new DefaultComboBoxModel<>(new String[] {"EUR", "USD", "CHF"}));
		comboBoxWaluta.setFont(FONT_DANE);
		
		JLabel lblWaluta = new JLabel("Waluta");
		lblWaluta.setFont(new Font("Dialog", Font.PLAIN, 24));
		
		JLabel lblKurs2 = new JLabel("Kurs");
		lblKurs2.setFont(new Font("Dialog", Font.PLAIN, 24));
		
		lblNazwaWaluty = new JLabel("--");
		lblNazwaWaluty.setFont(new Font("Dialog", Font.BOLD, 24));
		
		lblKurs_1 = new JLabel("--");
		lblKurs_1.setFont(new Font("Dialog", Font.BOLD, 24));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblWybierzWalut, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblWaluta, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblKurs2, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
					.addGap(43)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(comboBoxWaluta, 0, 446, Short.MAX_VALUE)
						.addComponent(lblNazwaWaluty, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
						.addComponent(lblKurs_1, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblWybierzWalut, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBoxWaluta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblWaluta, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblKurs2, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblNazwaWaluty, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblKurs_1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblNumerTabeli = new JLabel("Numer tabeli");
		lblNumerTabeli.setFont(FONT_LABEL);
		
		JLabel lblData = new JLabel("Data");
		lblData.setFont(FONT_LABEL);
		
		lblNrTabeli = new JLabel("--");
		lblNrTabeli.setFont(FONT_DANE);
		
		lblDataTabeli = new JLabel("--");
		lblDataTabeli.setFont(FONT_DANE);
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lblData, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNumerTabeli, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
					.addGap(92)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDataTabeli, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
						.addComponent(lblNrTabeli, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumerTabeli)
						.addComponent(lblNrTabeli))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblData)
						.addComponent(lblDataTabeli))
					.addContainerGap(31, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		frmPrzelicznikWalut.getContentPane().setLayout(groupLayout);
	}
	
	private void pobierzTabele() {
		// Aby w aplikacji okienkowej wykonać operację w tle (w innym wątku),
		// najwygodniej użyć klasy SwingWorker
		
		SwingWorker<Tabela, Tabela> worker =
				new SwingWorker<Tabela, Tabela>() {

			protected Tabela doInBackground() {
				// To wykona się w tle (w innym wątku)
				return ObslugaNBP.pobierzAktualnaTabele();
			}
			
			protected void done() {
				// To zostanie wykonane przez wątek interfejsu graficznego "EDT"
				
				// Na pole tabela wpiszę wynik metody doInBackground
				try {
					tabela = this.get();
					
					// Uaktualniam to, co wyświetla okno
					uaktualnijWidokTabeli();
					zmianaWaluty();
					
				} catch (InterruptedException | ExecutionException e) {
				}
			}
		}; // koniec tworzenia SwingWorkera
		
		// uruchamiam workera ("wysyłam do pracy")
		worker.execute();
	}

	protected void uaktualnijWidokTabeli() {
		lblNrTabeli.setText(tabela.getNumerTabeli());
		lblDataTabeli.setText(tabela.getData().toString());
		comboBoxWaluta.setModel(new DefaultComboBoxModel<>(tabela.getKodyWalut()));
	}

	protected void uaktualnijWidokWaluty() {
		if(wybranaWaluta != null) {
			lblNazwaWaluty.setText(wybranaWaluta.getNazwa());
			lblKurs_1.setText(wybranaWaluta.getKurs().toString());
		}
		
	}

	protected void zmianaWaluty() {
		String kod = (String)comboBoxWaluta.getSelectedItem();
		if(tabela != null) {
			wybranaWaluta = tabela.znajdz(kod);
			uaktualnijWidokWaluty();
		}
	}

}
