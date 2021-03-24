package integrator;


public class Elem{
	public String content;
	public int type; 

	
	public Elem(String s, int b) {
		content=s;
		type=b;
	}
	
	
	public Elem(Elem copy) {
		if (copy!=null) { 
		content=copy.content;
		type=copy.type;
		}
	}
}