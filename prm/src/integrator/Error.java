package integrator;

@SuppressWarnings("serial")
public class Error extends Exception{

	public Error() {
		
	}
	
	@Override
	public String toString() {
		return "Unos nije korektan";
	}
}
