package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import integrator.Error;
import integrator.Keyboard;
import methods.Romberg;
import methods.Simpson;
import methods.Trapezoidal;



public class Gui {

	private JFrame frame;
	public JTextField integralInput;
	public JTextField accuracy;
	public JTextField upperBound;
	public JTextField lowerBound;
	public TextArea resultArea;

	public JButton btnRestartuj;
	public JButton btnIzracunaj;
	protected double down, up;

	private Keyboard calculator = new Keyboard(this);

	protected static int lowerBoundPosition = 0, 
			upperBoundPosition = 1, 
			accuracyPosition = 2,
			integralInputPosition = 3,
			stepCounterPosition = 4;

	public int labelPosition;
	private JRadioButton rdbtnTrapeznaFormula;

	public String s1 = "", s2 = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	public static void init() {
		new Gui();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(204, 255, 204));
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		resultArea = new TextArea();
		resultArea.setBounds(295, 175, 487, 384);
		frame.getContentPane().add(resultArea);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(204, 255, 204));
		panel.setBounds(0, 0, 784, 175);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		lowerBound = new JTextField();
		lowerBound.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 15));
		lowerBound.setColumns(10);
		lowerBound.setBounds(70, 140, 106, 20);
		panel.add(lowerBound);

		integralInput = new JTextField();
		integralInput.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		integralInput.setBounds(95, 70, 195, 34);
		panel.add(calculator.setInput(integralInput));
		integralInput.setColumns(10);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Gui.class.getResource("/resources/photo.png")));
		lblNewLabel.setBounds(15, 11, 91, 150);
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(204, 255, 204));
		panel_1.setBounds(350, 0, 250, 175);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Metoda:");
		lblNewLabel_1.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel_1.setBounds(10, 11, 52, 20);
		panel_1.add(lblNewLabel_1);

		rdbtnTrapeznaFormula = new JRadioButton("Trapezna formula");
		rdbtnTrapeznaFormula.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 15));
		rdbtnTrapeznaFormula.setBackground(new Color(204, 255, 204));
		rdbtnTrapeznaFormula.setBounds(70, 7, 139, 29);
		panel_1.add(rdbtnTrapeznaFormula);

		JRadioButton rdbtnSimpsonovaFormula = new JRadioButton("Simpsonova formula");
		rdbtnSimpsonovaFormula.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 15));
		rdbtnSimpsonovaFormula.setBackground(new Color(204, 255, 204));
		rdbtnSimpsonovaFormula.setBounds(70, 39, 155, 29);
		panel_1.add(rdbtnSimpsonovaFormula);

		JRadioButton rdbtnRombergovaMetoda = new JRadioButton("Rombergova metoda");
		rdbtnRombergovaMetoda.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 15));
		rdbtnRombergovaMetoda.setBackground(new Color(204, 255, 204));
		rdbtnRombergovaMetoda.setBounds(70, 71, 155, 29);
		panel_1.add(rdbtnRombergovaMetoda);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnRombergovaMetoda);
		bg.add(rdbtnSimpsonovaFormula);
		bg.add(rdbtnTrapeznaFormula);

		choiceListener listener = new choiceListener();
		rdbtnTrapeznaFormula.addItemListener(listener);
		rdbtnSimpsonovaFormula.addItemListener(listener);
		rdbtnRombergovaMetoda.addItemListener(listener);

		JLabel lblPreciznostbrojKoraka = new JLabel("Preciznost:");
		lblPreciznostbrojKoraka.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 15));
		lblPreciznostbrojKoraka.setBounds(10, 115, 69, 20);
		panel_1.add(lblPreciznostbrojKoraka);

		accuracy = new JTextField();
		accuracy.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 15));
		accuracy.setBounds(100, 115, 106, 20);
		panel_1.add(accuracy);
		accuracy.setColumns(10);

		JLabel lblBrojKoraka = new JLabel("Broj koraka:");
		lblBrojKoraka.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 15));
		lblBrojKoraka.setBounds(10, 145, 81, 20);
		panel_1.add(lblBrojKoraka);

		stepCounter = new JTextField();
		stepCounter.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 15));
		stepCounter.setColumns(10);
		stepCounter.setBounds(100, 145, 106, 20);
		panel_1.add(stepCounter);

		upperBound = new JTextField();
		upperBound.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 15));
		upperBound.setColumns(10);
		upperBound.setBounds(120, 12, 106, 20);
		panel.add(upperBound);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(204, 255, 204));
		panel_2.setBounds(607, 0, 167, 151755);
		panel.add(panel_2);
		panel_2.setLayout(null);

		btnRestartuj = new JButton("Restartuj");
		btnRestartuj.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 15));
		btnRestartuj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				calculator.reset();

				// enable dugmica na tastaturi se vraca na pocetne vrednosti
				calculator.init();

				upperBound.setText("");
				lowerBound.setText("");
				integralInput.setText("");
				accuracy.setText("");
			}
		});

		btnRestartuj.setBounds(38, 114, 100, 30);
		panel_2.add(btnRestartuj);

		btnIzracunaj = new JButton("Izra\u010Dunaj");
		btnIzracunaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculator.integral.ispis();
				
				
				Double result;
				result = 0.0;
				String accuracyPosition = accuracy.getText();
				up = calculator.rGran(upperBound.getText());
				down = calculator.rGran(lowerBound.getText());

				System.out.println("Dole" + down + "   Gore" + up);
				switch (integrationMethod) {
				case 0: {
					Romberg r = new Romberg(Double.parseDouble(accuracyPosition), Gui.this);
					if (up > down)
						result = r.Calculate(calculator.integralInput, down, up);
					else
						result = -r.Calculate(calculator.integralInput, up, down);
				}
					;
					break;
				case 1: {
					Simpson s = new Simpson(Double.parseDouble(accuracyPosition), Gui.this);
					if (up > down)
						try {
							result = s.Calculate(calculator.integral, down, up);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					else
						try {
							result = -s.Calculate(calculator.integral, up, down);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
					;
					break;

				case 2: {
					Trapezoidal tr = new Trapezoidal(Double.parseDouble(accuracyPosition), Gui.this);
					if (up > down)
						result = tr.Calculate(calculator.integralInput, down, up);
					else
						result = -tr.Calculate(calculator.integralInput, up, down);
				}
					;
				}

				resultArea.setText(s1 + "\n\n\n" + s2);
				resultArea.append("\n\nI=" + result);

			}
		});
		btnIzracunaj.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 15));
		btnIzracunaj.setBounds(38, 74, 100, 30);
		panel_2.add(btnIzracunaj);

		JPanel calc_panel = new JPanel();
		calc_panel.setBounds(0, 175, 290, 385);
		frame.getContentPane().add(calc_panel);

		calc_panel.add(calculator.panel, "West");
		calc_panel.setLayout(new GridLayout(1, 0, 0, 0));

		// Opcija da kad kliknem na TextField unosim tacno tu vrednost
		upperBound.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				calculator.initG(upperBound.getText()); // enable odgovarajucih dugmica
				// DODAJ
				labelPosition = upperBoundPosition;
			}
		});

		lowerBound.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				calculator.initG(lowerBound.getText()); // enable odgovarajucih dugmica
				// DODAJ
				labelPosition = lowerBoundPosition;
			}
		});

		accuracy.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				calculator.initP(); // enable odgovarajucih dugmica
				// DODAJ
				labelPosition = accuracyPosition;
			}
		});

		stepCounter.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				calculator.initS(); // enable odgovarajucih dugmica
				labelPosition = stepCounterPosition;
			}
		});

		
		integralInput.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				calculator.init(); // enable odgovarajucih dugmica
				// DODAJ
				labelPosition = integralInputPosition;
			}
		});

	}

	private static int ROMBERG = 0, SIMPSON = 1, TRAPEZOIDAL = 2;
	private int integrationMethod;
	public JTextField stepCounter;
	
	
	

	private class choiceListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent dog) {
			JRadioButton izvor = (JRadioButton) dog.getSource();
			String s = izvor.getLabel();
			switch (s) {
			case "Trapezna formula":
				integrationMethod = TRAPEZOIDAL;
				break;
			case "Simpsonova formula":
				integrationMethod = SIMPSON;
				break;
			case "Rombergova metoda":
				integrationMethod = ROMBERG;
				break;
			}
		}
	}
}
