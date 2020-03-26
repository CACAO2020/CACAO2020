package control;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import abstraction.fourni.Variable;

public class CtrlDecouvertAutorise implements PropertyChangeListener {
    private Variable decouvertAutorise;
    private Variable compte;
    
    public CtrlDecouvertAutorise( Variable decouvertAutorise, Variable compte) {
    	this.decouvertAutorise = decouvertAutorise;
    	this.compte = compte;
    }
	public void propertyChange(PropertyChangeEvent evt) {
		compte.setMin(decouvertAutorise.getValeur());
	}
}