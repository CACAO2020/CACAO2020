package abstraction.eq2Producteur2;
import java.util.ArrayList;
import java.util.List;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;


public class eq2Stock extends eq2Acteur{
/**
 * @author lucas et lucas
 * cette classe gère les stocks de fèves et de pâte, les masses sont exprimées en tonnes
 */
	private Variable masse_feves_trinitario;
	private Variable masse_feves_forastero;
	private Variable masse_feves_criollo;
	private Variable qt_pate_bassegamme;
	private Variable qt_pate_hautegamme;
	/**
	 * @param masse_feves_trinitario
	 * @param masse_feves_forastero
	 * @param masse_feves_criollo
	 * @param qt_pate_bassegamme
	 * @param qt_pate_hautegamme
	 */
	public eq2Stock( IActeur createur,double init1, double init2, double init3, double init4, double init5) {
		this.masse_feves_trinitario = new Variable("masse_feves_trinitario", createur, init1);
		this.masse_feves_forastero = new Variable("masse_feves_forastero", createur, init2);
		this.masse_feves_criollo = new Variable("masse_feves_criollo", createur, init3 );
		this.qt_pate_bassegamme = new Variable("qt_pate_bassegamme", createur, init4);
		this.qt_pate_hautegamme = new Variable("qt_pate_hautegamme", createur, init5);
	}
 public List<Variable> getVariables() {
	 List<Variable> variables=new ArrayList<Variable>();
	 variables.add(this.masse_feves_forastero);
	 variables.add(this.masse_feves_trinitario);
	 variables.add(this.masse_feves_criollo);
	 variables.add(this.qt_pate_bassegamme);
	 variables.add(this.qt_pate_hautegamme);
	 return variables;
 }
/**
 * @param masse_feves_trinitario the masse_feves_trinitario to set
 */
public void setmasse_feves_trinitario(double q_feves_trinitario) {
	this.masse_feves_trinitario.setValeur(masse_feves_trinitario.getCreateur() , q_feves_trinitario); 
}
/**
 * @param masse_feves_forastero the masse_feves_forastero to set
 */
public void setmasse_feves_forastero(double q_feves_forastero) {
	this.masse_feves_forastero.setValeur(masse_feves_forastero.getCreateur(), q_feves_forastero);;
}
/**
 * @param masse_feves_criollo the masse_feves_criollo to set
 */
public void setmasse_feves_criollo(double q_feves_criollo) {
	this.masse_feves_criollo.setValeur(masse_feves_criollo.getCreateur(), q_feves_criollo);;
}
/**
 * @param qt_pate_bassegamme the qt_pate_bassegamme to set
 */
public void setQt_pate_bassegamme(double q_pate_bassegamme) {
	this.qt_pate_bassegamme.setValeur(qt_pate_bassegamme.getCreateur(), q_pate_bassegamme);;
}
/**
 * @param qt_pate_hautegamme the qt_pate_hautegamme to set
 */
public void setQt_pate_hautegamme(double q_pate_hautegamme) {
	this.qt_pate_hautegamme.setValeur(qt_pate_hautegamme.getCreateur(), q_pate_hautegamme);;
}
public void setVariables(double q1,double q2,double q3,double q4,double q5) {
	this.setmasse_feves_trinitario( q1);
	this.setmasse_feves_forastero( q2);
	this.setmasse_feves_criollo( q3);
	this.setQt_pate_bassegamme( q4);
	this.setQt_pate_hautegamme( q5);
}
}
