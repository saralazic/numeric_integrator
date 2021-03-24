package integrator;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JTextField;

import functions.*;
import gui.Gui;

public class Keyboard {

	private Gui gui;

	public Panel panel;
	


	private JTextField input;
	public Function integralInput;

	private JButton[] digits = new JButton[10];
	private JButton[] operations = new JButton[20];
	private JButton variable;
	private JButton decimal_pt;
	private final Font btnFont = new Font("Serif", Font.BOLD | Font.ITALIC, 15);

	private int parentheses_cnt = 0; // broj otvorenih zagrada
	
	public func integral = new func();

	public JTextField setInput(JTextField l) {
		input = l;
		return input;
	}

	public void reset() {
		integralInput = null;
	}

	private void panelVars(Panel panel) {
		OperationsListener listener = new OperationsListener();
		variable = new JButton("x");
		variable.addActionListener(listener);
		variable.setEnabled(true);
		variable.setVisible(true);
		variable.setFont(btnFont);
		panel.add(variable);
	}

	private void panelDigits(Panel panel) {
		NumActionListener listener = new NumActionListener();
		for (int i = 0; i < 10; i++) {
			panel.add(digits[i] = new JButton(Integer.toString(i)));
			digits[i].addActionListener(listener);
			digits[i].setFont(btnFont);
			digits[i].setEnabled(true);
			// preth=false;
		}
		decimal_pt = new JButton(".");
		decimal_pt.addActionListener(listener);
		decimal_pt.setEnabled(true);
		decimal_pt.setVisible(true);
		panel.add(decimal_pt);
	}

	private void panelOperators(Panel panel) {
		String[] btns = { "-", "+", "*", "/", "^", "√", "log", "ln", "e^", "sin", "cos", "tg", "ctg", "asin", "acos",
				"atan", "acot", "π", "(", ")" };
		OperationsListener listener = new OperationsListener();
		for (int i = 0; i < 20; i++) {
			panel.add(operations[i] = new JButton(btns[i].toString()));
			operations[i].addActionListener(listener);
			operations[i].setFont(btnFont);
			if (i < 5)
				operations[i].setEnabled(false);
			else if (i == 19)
				operations[i].setEnabled(parentheses_cnt > 0);
			else
				operations[i].setEnabled(true);
		}
		operations[0].setEnabled(true);
	}

	// enable dugmica za funkciju
	public void init() {
		variable.setEnabled(true);
		for (int i = 0; i < 10; i++)
			digits[i].setEnabled(true);
		for (int i = 0; i < 20; i++) {
			if (i < 5)
				operations[i].setEnabled(false);
			else if (i == 19)
				operations[i].setEnabled(parentheses_cnt > 0);
			else
				operations[i].setEnabled(true);
		}
	}

	public void initG(String oldContent) {
		for (int i = 0; i < 10; i++)
			digits[i].setEnabled(true);
		decimal_pt.setEnabled(oldContent.length() == 0);
		variable.setEnabled(false);
		for (int i = 0; i < 20; i++)
			if (i == 0 || i == 17)
				operations[i].setEnabled(true);
			else
				operations[i].setEnabled(false);
	}

	// enable dugmica za accuracy
	public void initP() {
		for (int i = 0; i < 10; i++)
			digits[i].setEnabled(true);
		decimal_pt.setEnabled(true);
		for (int i = 0; i < 20; i++)
			operations[i].setEnabled(false);
		variable.setEnabled(false);
	}

	// enable dugmica za broj koraka
	public void initS() {
		initP();
		decimal_pt.setEnabled(false);
		variable.setEnabled(false);

	}

	public void enableBinaryOperations(boolean b) {
		for (int i = 0; i < 5; i++) {
			operations[i].setEnabled(b);// + - * / ^
		}
	}
	
	public void enableUnaryOperations(boolean b) {
		for (int i = 5; i < 18; i++) {
			operations[i].setEnabled(b);// "√", "log", "ln", "e^", "sin", "cos", "tg", "ctg", "asin", "acos", "atan", "acot", π
			variable.setEnabled(b);
		}
	}
	
	public void enableDigits(boolean b) {
		for (int i = 0; i < 10; i++) {
			digits[i].setEnabled(b);
		}
	}
	
	public boolean operationType(String op) {
		switch (op) {
		case "x":			
		case "sin":			
		case "cos":			
		case "tan":
		case "cot":
		case "asin":
		case "acos":
		case "atan":
		case "acot":
		case "ln":
		case "log":
		case "e^":
		case "√":
		case "π":
			return false;
		}
		return true;
	}
	
	private class OperationsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent dog) {
			String oldContent;
			String op = dog.getActionCommand();

			switch (gui.labelPosition) {
			case 0: {
				oldContent = gui.lowerBound.getText();
				oldContent = oldContent + op;
				gui.lowerBound.setText(oldContent);
				operations[17].setEnabled(false);//-
				operations[0].setEnabled(false);//-
				if (op == "-") {
					decimal_pt.setEnabled(false);//.

				}
				else {
					if (oldContent.contains(".")) decimal_pt.setEnabled(false);
					else decimal_pt.setEnabled(true);	
				}
				

			}
				;
				break;

			case 1: {
				oldContent = gui.upperBound.getText();
				oldContent = oldContent + op;
				gui.upperBound.setText(oldContent);
				operations[17].setEnabled(false);//-
				operations[0].setEnabled(false);//-
				if (op == "-") {
					decimal_pt.setEnabled(false);//.

				}
				else {
					if (oldContent.contains(".")) decimal_pt.setEnabled(false);
					else decimal_pt.setEnabled(true);	
				}
			}
				;
				break;

			case 3: {
				System.out.println("input: "+op);
				try {
					if(op=="x") integral.dodajCif("x");
					else integral.addOperatorToInfixList(op, operationType(op));
				} catch (Error e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				oldContent = input.getText();
				switch (op) {
				case "x":
					integralInput = new X();
					break;
				case "sin":
					integralInput = new Sin();
					break;
				case "cos":
					integralInput = new Cos();
					break;
				case "tan":
					integralInput = new Tan();
					break;
				case "cot":
					integralInput = new Cot();
					break;
				case "asin":
					integralInput = new Asin();
					break;
				case "acos":
					integralInput = new Acos();
					break;
				case "atan":
					integralInput = new Atan();
					break;
				case "acot":
					integralInput = new Acot();
					break;
				case "ln":
					integralInput = new Ln();
					break;
				case "log":
					integralInput = new Log();
					break;
				case "e^":
					integralInput = new E();
					break;
				case "√":
					integralInput = new Root();
					break;

				}

				if (op == "(")
					parentheses_cnt++;
				if (op == ")")
					parentheses_cnt--;

				// enable dugmica za operations
				if (op == "Ï€" || op == ")") {
					enableDigits(false);
					enableUnaryOperations(false);
					enableBinaryOperations(true);
					operations[19].setEnabled(parentheses_cnt > 0);
				} else
					for (int i = 0; i < 20; i++) {
						if (i < 5)
							operations[i].setEnabled(false);
						else if (i == 19)
							operations[i].setEnabled(false);
						else
							operations[i].setEnabled(true);
					}

				decimal_pt.setEnabled(false);
				if (!operationType(op) && op!="x" && op!="π")
					input.setText(oldContent + " " + op + "x ");
				else
					input.setText(oldContent + " " + op + " ");
				
				if(!operationType(op)) {
					enableDigits(false);
					enableUnaryOperations(false);
					enableBinaryOperations(true);
					operations[18].setEnabled(false);
					operations[19].setEnabled(parentheses_cnt>0);

				} else{
					enableDigits(true);
					enableUnaryOperations(true);
					enableBinaryOperations(false);
					operations[18].setEnabled(true);
					
				}
				
				if (op == "Ï€" || op == ")") {
					enableDigits(false);
					enableUnaryOperations(false);
					enableBinaryOperations(true);
					operations[18].setEnabled(false);
					operations[19].setEnabled(parentheses_cnt > 0);
				}
			}
				;
				break;

			default: {
			}
			}
		}

	}

	private class NumActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {

			
			String btn = event.getActionCommand();
			String oldContent;

			switch (gui.labelPosition) {
			case 0: {// LOWER BOUND
				oldContent = gui.lowerBound.getText();
				oldContent = oldContent + btn;
				gui.lowerBound.setText(oldContent);
				operations[17].setEnabled(false);//-
				operations[0].setEnabled(false);//-
				if (btn == "-") {
					decimal_pt.setEnabled(false);//.

				}
				else {
					if (oldContent.contains(".")) decimal_pt.setEnabled(false);
					else decimal_pt.setEnabled(true);	
				}

			}
				;
				break;

			case 1: {// UPPER BOUND
				oldContent = gui.upperBound.getText();
				oldContent = oldContent + btn;
				gui.upperBound.setText(oldContent);
				
				operations[17].setEnabled(false);//-
				operations[0].setEnabled(false);//-
				if (btn == "-") {
					decimal_pt.setEnabled(false);//.

				}
				else {
					if (oldContent.contains(".")) decimal_pt.setEnabled(false);
					else decimal_pt.setEnabled(true);	
				}
			}
				;
				break;

			case 2: {// ACCURACY
				oldContent = gui.accuracy.getText();
				// if (oldContent.length() > 0) decimal_pt.setEnabled(true);
				oldContent = oldContent + btn;
				gui.accuracy.setText(oldContent);
				if (oldContent.contains(".")) decimal_pt.setEnabled(false);
			}
				;
				break;

			case 3: { // INPUT
				System.out.println("input: "+btn);
				integral.dodajCif(btn);
				
				oldContent = input.getText();
				if (btn!="x" && !operationType(btn) && btn!="π")
					input.setText(oldContent + btn + " x");
				else
					input.setText(oldContent + btn);
				/*if (btn == "x")
					input.setText(oldContent + btn);
				else
					input.setText(oldContent + btn + " x");*/
				for (int i = 0; i < 20; i++) {
					if (btn == ".")
						operations[i].setEnabled(false);
					else
						operations[i].setEnabled(true);
				}
				operations[19].setEnabled(parentheses_cnt > 0);
				if (btn == "." || btn == "x")
					decimal_pt.setEnabled(false);
				
				else
					decimal_pt.setEnabled(true);

				enableBinaryOperations(true);
				//enableDigits(false);
				enableUnaryOperations(false);
				operations[18].setEnabled(false);

			}
				;
				break;

			case 4: { // stepCounter
				oldContent = gui.stepCounter.getText();
				oldContent = oldContent + btn;
				gui.stepCounter.setText(oldContent);
			}
			}
		}
	}

	public double rGran(String oldContent) {
		if (oldContent == null || oldContent.length() == 0)
			System.out.println("GRESKA");

		String s = oldContent, s1 = "";
		char c;
		double d = 0;
		if (!s.contains("π"))
			d = Double.parseDouble(s);
		else {
			for (int i = 0; i < s.length(); i++)
				if ((c = s.charAt(i)) != "π".charAt(0))
					s1 += c;
			if (s1.length() != 0)
				d = Double.parseDouble(s1) * Math.PI;
			else
				d = Math.PI;
		}

		return d;
	}

	public Keyboard(Gui i) {

		panel = new Panel();
		panel.setSize(290, 385);
		panel.setLayout(new GridLayout(8, 4));

		panelVars(panel);
		panelDigits(panel);
		panelOperators(panel);

		gui = i;
	}

}
