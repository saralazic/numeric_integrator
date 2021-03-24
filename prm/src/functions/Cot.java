package functions;

public class Cot extends Function{

	public Cot() {}
	
	public double aprox(double x) {
		return 1/Math.tan(x);
	}
	
}
