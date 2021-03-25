package integrator;

import gui.Gui;

@SuppressWarnings("serial")
public class Error extends Exception{

	public Error() {
		
	}
	
	@Override
	public String toString() {
		Gui.resultArea.setText("Unos nije korektan!  Polja integral, preciznost i granice moraju biti ispravno popunjena!");
		return "Unos nije korektan";
	}
}
