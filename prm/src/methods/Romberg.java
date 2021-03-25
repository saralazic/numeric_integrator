package methods;

import functions.Function;
import gui.Gui;
import integrator.Error;
import integrator.func;

public class Romberg {

	private int stepCounter;
	private double accuracy;
	private Gui gui;

	public static int defaultStepCounter = 20;

	public Romberg(double accuracy, Gui gui, int stepCounter) {
		this.accuracy = accuracy;
		this.gui = gui;
		this.stepCounter = stepCounter;
	}

	public Romberg(double accuracy, Gui gui) {
		this.gui = gui;
		this.accuracy = accuracy;

		String s = gui.stepCounter.getText();
		if (s.length() > 0)
			this.stepCounter = Integer.parseInt(s);
		else
			this.stepCounter = defaultStepCounter;
	}

	public double Calculate(func f, double down, double up) throws Error {
		double[][] r = new double[stepCounter][stepCounter];

		String s1 = "";

		s1 += "Rombergova integracija koristi sledeće formule: \n";
		s1 += "Trapezna formula: T=(h/2)*(f(a)+f(b)+2*Σf(a+i*h0))\n";
		s1 += "Formula Ričardsonove ekstrapolacije: T[k][j]=(4^j T[k][j-1]-T[k-1][j-1])/(4^j-1), j=1...k\n";

		s1 += "\n\nh0=b-a" + "=" + (up - down);
		double h = up - down; // step size
		Trapezoidal trap = new Trapezoidal(h, gui);
		s1 += "\nT[0][0]=(h0/2)*(f(a)+f(b)+2*Σf(a+i*h0))";
		r[0][0] = trap.Calculate(f, down, up);
		s1 += "T[0][0]=" + r[0][0] + ", izracunato po trapeznoj formuli";
		int i = 1;
		do {
			s1 += "\n\nk=" + i;
			s1 += "\nh" + i + "=h0/2^" + i + "=" + (h / (double) Math.pow(2, (double) i)) + "\n";
			Trapezoidal t = new Trapezoidal(h / (double) Math.pow(2, (double) i), gui);
			r[i][0] = t.Calculate(f, down, up);
			s1 += "T[" + i + "][0]=" + r[i][0] + ", izracunato po trapeznoj formuli \n";
			for (int j = 1; j <= i; j++) {
				r[i][j] = ((double) Math.pow(2, 2.0 * j) * r[i][j - 1] - r[i - 1][j - 1])
						/ ((double) Math.pow(2, 2.0 * j) - 1.0);
				s1 += "T[" + i + "][" + j + "]=" + r[i][j] + ", dobijeno Ričardsonovom ekstrapolacijom \n";
			}
			i++;
		} while ((Math.abs(r[i - 1][i - 1] - r[i - 2][i - 2]) > accuracy) && (i < stepCounter));

		gui.s1 = s1;
		gui.s2 = "";
		return r[i - 1][i - 1];
	}

};
