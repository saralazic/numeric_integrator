package methods;

import functions.Function;
import gui.Gui;
import integrator.Error;
import integrator.func;

public class Trapezoidal {

	private double step;
	private Gui gui;

	public Trapezoidal(double step, Gui i) {
		this.step = step;
		this.gui = i;
	}

	public double Calculate(func f, double down, double up) throws Error {
		double d = 0;

		String s1, s2;

		s1 = "Trapezna formula aproksimira vrednost integrala na sledeći način: \n";
		s1 += "T=(h/2)*(f(a)+f(b)+2*Σf(a+i*h0))\n";

		s2 = "";
		s1 += "\n\nT(h)=(h/2)*(f0+2*(";
		s2 += "T(" + step + ")=(" + step / 2 + ")*(" + f.aprox(down) + "+2*(";

		int i;
		Double fx;
		for (i = 1; i <= ((int) ((up - down) / step) - 1); i++) {
			if (i > 1) {
				s1 += "+";
			}
			s1 += "f" + i;
			fx = f.aprox(down + i * step);
			if (i>1 && fx>0) s2+="+";
			d += fx;
			s2 += fx;
			if (i%10==0) {
				s1 += "\n";
				s2 += "\n";
			}
		}
		s1 += ")+f" + (i + 1) + ")";
		s2 += ")+" + f.aprox(up) + ")";

		gui.s1 = s1;
		gui.s2 = s2;

		// System.out.println(((step/2)*(f.aprox(down)+2*d+f.aprox(up))));

		return ((step / 2) * (f.aprox(down) + 2 * d + f.aprox(up)));

	}

}
