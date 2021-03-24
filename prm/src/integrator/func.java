package integrator;

import java.util.Stack;

import functions.*;

public class func {

	protected Stack<Elem> infix, postfix;

	public static final int NUMBER = 0, BIN_OPERATOR = 1, VARIABLE = 2, UN_OPERATOR = 3, PI = 4, PAREN = 5;

	public static final Elem node_x = new Elem("x", func.VARIABLE), node_pi = new Elem("π", func.PI);

	public func() {
		infix = new Stack<Elem>();
		postfix = new Stack<Elem>();
	}

	// dodavanje operatora u listu
	public void addOperatorToInfixList(String s, boolean bin) throws Error {
		System.out.println("dodajOp: " + s);
		if (infix.empty() && bin)
			throw new Error();// i ako je binarni, greska je u unosu

		if (s == "π")
			infix.push(func.node_pi); // ako je pi, dodajem pi
		else if (s == "(" || s == ")") {
			infix.push(new Elem(s, func.PAREN)); // ako je PAREN, dodajem
		} else if (!bin) {
			infix.push(new Elem(s, func.UN_OPERATOR)); // ako je unarni, dodajem ga
		} else
			infix.push(new Elem(s, func.BIN_OPERATOR));
	}

	// dodavanje cifre (ili u vec postojeci element
	// ili u novi ako je prethodni element operator)
	// u ovoj funkciji se takodje u listu dodaje i promenljiva
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

	/*
	 * public boolean isPrefixOperation(String s) { if (s == "log" || s == "ln" || s
	 * == "e^" || s == "√" || s == "sin" || s == "cos" || s == "tg" || s == "ctg" ||
	 * s == "asin" || s == "acos" || s == "atan" || s == "acot") return true; return
	 * false; }
	 */

	public void ispis() {
		System.out.println("INFIX:");
		for (int i = 0; i < infix.size(); i++) {
			System.out.print(" " + infix.get(i).content);
		}
		System.out.println();
	}

	// POPRAVI
	public void in2post() throws Error {
		System.gc();
		System.out.println("Usao u in2post");
		int rank = 0;
		Elem x;

		Stack<Elem> help = new Stack<Elem>();

		TabelaPrioriteta tab = new TabelaPrioriteta();

		Elem next = infix.get(0);
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
		System.out.println("Izasao iz in2post");
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
			System.out.println(x.content + " " + x.type);
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

	/*
	 * 
	 * public void unarni(Elem p, double x) throws Error { Elem priv = p, sled =
	 * priv.sledeci;
	 * 
	 * // ova petlja prolazi kroz celu listu // i eliminise sve unarne operande
	 * while (priv != null) { if (priv.type != func.UN_OPERATOR) { // ako nije
	 * isUnaryarni operand, samo ide dalje // pret=priv; priv = priv.sledeci; if
	 * (sled != null) sled = sled.sledeci; } else {// ako je trenutni element
	 * isUnaryarni operand
	 * 
	 * Elem tek = priv.sledeci; if (sled.type == func.BIN_OPERATOR) throw new
	 * Error(); else { func podf = new func(); // podfisUnarykcija // Ako posle
	 * isUnaryarnog operanda sledi "(", za podfisUnarykciju uzimam sve sto // je u
	 * PARENadi if (sled.content == "(") { int bz; bz = 1; // NUMBERim PARENade //
	 * podf.dodajEl(sled); tek = sled.sledeci; while (tek != null) { if (tek.content
	 * == ")") { bz--; if (bz < 0) throw new Error(); else if (bz == 0) break; else
	 * podf.dodajEl(tek); } else { if (tek.content == "(") bz++; podf.dodajEl(tek);
	 * } tek = tek.sledeci; } if (tek == null && bz != 0) throw new Error(); // ako
	 * PARENada nikad nije zatvorena } else if (sled.type == func.UN_OPERATOR) {
	 * unarni(sled, x);
	 * 
	 * // PROVERI STA JE U PARENADI podf.dodajEl(priv.sledeci); } else
	 * podf.dodajEl(sled);
	 * 
	 * double d = podf.aprox(x); Elem e = new Elem(d + "", func.NUMBER); if (tek !=
	 * null) e.sledeci = tek.sledeci; else e.sledeci = null; priv.sledeci = e; } //
	 * sad je podfisUnarykcija oblika fisUnarykcija(NUMBER)
	 * 
	 * func f; /* switch(priv.content) { case "log": f=new Log(); break; case "ln":
	 * f=new Ln(); break; case "e2": f=new e2(); break; case "âˆš": f=new Root();
	 * break; case "sin": f=new Sin(); break; case "cos": f=new Cos(); break; case
	 * "tan": f=new Tan(); break; case "cot": f=new Cot(); break; case "asin": f=new
	 * Asin(); break; case "acos": f=new Acos(); break; case "atan": f=new Atan();
	 * break; case "acot": f=new Acot(); break; default: f=new fja(); break; }
	 * 
	 * double dd=f.aprox(x);
	 * 
	 * priv.content=dd+""; priv.type=fja.NUMBER;
	 * 
	 * priv.sledeci=priv.sledeci.sledeci;
	 * 
	 * } } // while(priv!=null) }// isUnaryarni
	 */

	
	public double aprox(double x) throws Error {
		//System.out.println("aprox");
		double d;
		if (infix.empty())
			throw new Error();
		if (infix.size() == 1) {
			if (infix.get(0).type == func.PI)
				return Math.PI;
			if (infix.get(0).type == func.NUMBER)
				return Double.parseDouble(infix.get(0).content);
			else if (infix.get(0).type == func.VARIABLE)
				return x;
			else
				throw new Error(); // Greska ako je sam operand ili PARENada
		} else {

			d = evalExp(x);
			System.out.println("\n d");
		}
		return d;
	}

}
