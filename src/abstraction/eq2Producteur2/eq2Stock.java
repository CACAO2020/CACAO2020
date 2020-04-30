package abstraction.eq2Producteur2;
import java.util.ArrayList;
import java.util.List;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;


public class eq2Stock extends eq2Acteur{
/**
 * @author lucas
 * cette classe gère les stocks de fèves et de pâte 
 */
	private Variable nb_feves_trinitario;
	private Variable nb_feves_forastero;
	private Variable nb_feves_criollo;
	private Variable qt_pate_bassegamme;
	private Variable qt_pate_hautegamme;
	/**
	 * @param nb_feves_trinitario
	 * @param nb_feves_forastero
	 * @param nb_feves_criollo
	 * @param qt_pate_bassegamme
	 * @param qt_pate_hautegamme
	 */
	public eq2Stock( IActeur createur,double init1, double init2, double init3, double init4, double init5) {
		this.nb_feves_trinitario = new Variable("nb_feves_trinitario", createur, init1);
		this.nb_feves_forastero = new Variable("nb_feves_forastero", createur, init2);
		this.nb_feves_criollo = new Variable("nb_feves_criollo", createur, init3 );
		this.qt_pate_bassegamme = new Variable("qt_pate_bassegamme", createur, init4);
		this.qt_pate_hautegamme = new Variable("qt_pate_hautegamme", createur, init5);
	}
 public List<Variable> getVariables() {
	 List<Variable> variables=new ArrayList<Variable>();
	 variables.add(this.nb_feves_trinitario);
	 variables.add(this.nb_feves_forastero);
	 variables.add(this.nb_feves_criollo);
	 variables.add(this.qt_pate_bassegamme);
	 variables.add(this.qt_pate_hautegamme);
	 return variables;
 }

}
