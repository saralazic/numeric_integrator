package methods;

import functions.Function;
import gui.Gui;
import integrator.func;

public class Simpson {

	private double step;

	private Gui gui;

	public Simpson(double step, Gui i) {
		this.step = step;
		this.gui = i;
	}

	public double Calculate(func f, double down, double up) throws Exception{
	
		double d1 = 0;
		int j;
		String s1, s2;

		s1 = "Simpsonova formula aproksimira vrednost integrala na sledeći način:\n";
		s1 += "S(h)=(h/3)*(f0+2*(f2+f4+...)+4*(f1+f3+...)+f2m)";

		s1 += "\n\n\nI=(h/3)*(f0+2*(";

		System.out.println(step);
		s2 = "I=" + ((step) / 3) + "*(" + f.aprox(down) + "2*(";

		for (int i = 1; i < ((int) (((up - down) / step) / 2)); i++) {
			Double fx = f.aprox(down + 2 * i * step);
			j = 2 * i;
			if (i > 1) {
				s1 += "+";
				if (fx>=0) s2 += "+";
			}
			s1 += "f" + j;
			
			d1 += fx;
			s2 += fx;
			if(i%10==0) {
				s1+="\n";
			}
			if(i%5==0) s2+="\n";
		}
		s1 += ")\n+4*(";
		s2 += ")+\n4*(";

		double d2 = 0;
		for (int i = 1; i <= ((int) (((up - down) / step) / 2)); i++) {
			j = 2 * i - 1;
			if (i > 1) {
				s1 += "+";
				s2 += "+";
			}
			s1 += "f" + j;
			Double fx = f.aprox(down + (2 * i - 1) * step);
			s2 += fx;
			d2 += fx;
			if(i%10==0) {
				s1+="\n";
			}
			if(i%5==0) s2+="\n";
		}

		s1 += ")\n+f2m))";
		s2 += ")+\n" + f.aprox(up) + "))";

		gui.s1 = s1;
		gui.s2 = s2;
		return ((step / 3) * (f.aprox(down) + 2 * d1 + 4 * d2 + f.aprox(up)));
	}

}
