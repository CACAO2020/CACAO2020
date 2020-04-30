package abstraction.eq2Producteur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.fourni.Filiere;

public class Producteur2 extends eq2Investisseur implements IActeur {
	
	private eq2Stock stock;
	private Journal journalEq2;
	private List<PaquetArbres> PaquetsArbres; // la liste des paquets d'arbres de notre acteur

	public Producteur2() {
		this.stock=new eq2Stock(this, 0,0,0,0,0);
		this.journalEq2 = new Journal("Eq2 activites", this);
		this.PaquetsArbres = new ArrayList<PaquetArbres>();
	}

	public void initialiser() {
	}
	
	public List<PaquetArbres> getPaquetsArbres(){
		return this.PaquetsArbres;
	}
	/**
	 * Cette methode est appellee a chaque nouveau tour
	 * Pour l'instant elle avance l'age de chaque paquet d'arbre de 1
	 * @author Kristof Szentes
	 */
	public void next() {
		for (int i = 0; i < this.getPaquetsArbres().size(); i++) {
			this.getPaquetsArbres().get(i).setAge(this.getPaquetsArbres().get(i).getAge() + 1);
		}
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		res.addAll(this.stock.getVariables());
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(this.journalEq2);
		return res;
	}
	
}
