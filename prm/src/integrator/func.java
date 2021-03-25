package integrator;

import java.util.Stack;

import functions.*;

public class func {

	protected Stack<Elem> infix, postfix;

	public static final int NUMBER = 0, BIN_OPERATOR = 1, VARIABLE = 2, UN_OPERATOR = 3, PI = 4, PAREN = 5;

	public static final Elem node_x = new Elem("x", func.VARIABLE), node_pi = new Elem("π", func.PI);

	
	public Elem infix_0;
	public int sz;
	
	public func() {
		infix = new Stack<Elem>();
		postfix = new Stack<Elem>();
	}

	// dodavanje operatora u listu
	public void addOperatorToInfixList(String s, boolean bin) throws Error {
		if (infix.empty() && bin)
			throw new Error();

		if (s == "π")
			infix.push(func.node_pi);
		else if (s == "(" || s == ")") {
			infix.push(new Elem(s, func.PAREN));
		} else if (!bin) {
			infix.push(new Elem(s, func.UN_OPERATOR));
		} else
			infix.push(new Elem(s, func.BIN_OPERATOR));
	}

	// dodavanje cifre/promenljive
	public void dodajCif(String s) {
		if (s == "x") {
			infix.push(func.node_x);
		} else {
			if (infix.empty())
				infix.push(new Elem(s, func.NUMBER));
			else {
				if (infix.peek().type == func.NUMBER) {
					Elem e = infix.pop();
					e.content += s;
					infix.push(e);
				} else
					infix.push(new Elem(s, func.NUMBER));
			}

		}
	}

	public void print_infix() {
		System.out.println("INFIX:");
		for (int i = 0; i < infix.size(); i++) {
			System.out.print(" " + infix.get(i).content);
		}
		System.out.println();
	}

	public void in2post() throws Error {
		System.gc();
		int rank = 0;
		Elem x;

		Stack<Elem> help = new Stack<Elem>();

		PriorityTable tab = new PriorityTable();

		Elem next = infix.get(0);
		infix_0 = next;
		sz = infix.size();
		int index = 1;

		while (next != null) {
			System.out.println(next.content + " " + next.type);
			if (next.type == func.NUMBER || next.type == func.PI || next.type == func.VARIABLE
					|| next.type == func.UN_OPERATOR) {

				postfix.push(next);
				rank++;
			} else {
				while (!help.empty() && (tab.IPR(next) <= tab.SPR(help.peek()))) {
					x = help.peek();

					postfix.push(x);
					rank = rank + tab.R(x);
				//	if (rank < 1)
					//	throw new Error();
				}
				if (next.content != ")")

					help.add(0, next);
				else
					x = help.pop();
			}
			next = (infix.size() > index) ? infix.get(index++) : null;
		}
		while (!help.empty()) {
			x = help.pop();
			postfix.push(x);
			rank = rank + tab.R(x);
		}

		if (rank != 1)
			throw new Error();

		print_postfix();
		help = null;
		infix.clear();
		infix = null;
	}

	public void print_postfix() {
		System.out.print("POSTFIX: ");
		for (int i = 0; i < postfix.size(); i++) {
			System.out.print(" " + postfix.get(i).content);
		}
		System.out.println();
	}

	// izracunavanje prostih operacija
	public double calc(double a, double b, String op) {
		double rez = 0;
		switch (op) {
		case "+":
			rez = a + b;
			break;
		case "-":
			rez = a - b;
			break;
		case "*":
			rez = a * b;
			break;
		case "/":
			rez = a / b;
			break;
		case "^":
			rez = Math.pow(a, b);
			break;
		}
		return rez;
	}

	// izrcunavanje postfiksnog izraza
	public double evalExp(double VARIABLE) throws Error {
	//	System.out.println("evalExp");
		System.gc();
		Elem x;
		double rez;
		Stack<Elem> help = new Stack<Elem>();

		int index = 0;

		if (postfix.size() == 0)
			in2post();
		while (postfix.size() > index) {
			x = postfix.get(index++);
		//	System.out.println(x.content + " " + x.type);
			if (x.type == func.NUMBER || x.type == func.PI || x.type == func.VARIABLE)
				help.push(x);
			else if (x.type == func.UN_OPERATOR) {
				Function integralInput = new X();
				switch (x.content) {
				case "x":
					integralInput = new X();
					break;
				case "sin":
					integralInput = new Sin();
					break;
				case "cos":
					integralInput = new Cos();
					break;
				case "tg":
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

				rez = integralInput.aprox(VARIABLE);
				help.push(new Elem(Double.toString(rez), func.NUMBER));

			} else if (x.type == func.BIN_OPERATOR) {
				double o[] = new double[2];
				for (int i = 0; i < 2; i++) {
					Elem oper = help.pop();
					if (oper.type == func.NUMBER)
						o[i] = Double.parseDouble(oper.content);
					else if (oper.type == func.PI)
						o[i] = Math.PI;
					else
						o[i] = VARIABLE;
				}
				rez = calc(o[1], o[0], x.content);
				help.push(new Elem(Double.toString(rez), func.NUMBER));

			}
		}

		if (help.empty())
			throw new Error();
		Elem el = help.pop();
		if (el.type == func.NUMBER)
			rez = Double.parseDouble(el.content);
		else
			throw new Error();
		if (help.empty())
			return rez;
		else
			throw new Error();
	}

	
	public double aprox(double x) throws Error {
		//System.out.println("aprox");
		double d;
		if (infix!=null && infix.size()>0) { infix_0 = infix.get(0); sz = infix.size(); }
 		if (infix_0==null)
			throw new Error();
		if (sz == 1) {
			if (infix_0.type == func.PI)
				return Math.PI;
			if (infix_0.type == func.NUMBER)
				return Double.parseDouble(infix.get(0).content);
			else if (infix_0.type == func.VARIABLE)
				return x;
			else
				throw new Error(); // Greska ako je sam operand ili zagrada
		} else {

			d = evalExp(x);
		}
		return d;
	}
	
	
	public void reset() {
		infix = null; postfix = null;
		infix = new Stack<Elem>();
		postfix = new Stack<Elem>();
		
	}

}
