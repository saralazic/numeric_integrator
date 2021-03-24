package functions;

public class Acot extends Function{

	public Acot() {}
	
	public double aprox(double x) {
		return Math.PI/2-Math.atan(x);
	}
	
}
