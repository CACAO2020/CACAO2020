package abstraction.eq2Producteur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq4Transformateur2.PateInterne;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;

public class Producteur2 extends eq2Investisseur implements IActeur {
	
	private List<PaquetArbres> PaquetsArbres; // la liste des paquets d'arbres de notre acteur

	public Producteur2() {
		super();
	}

	public void initialiser() {
	}
	/**
	 * Cette methode est appellee a chaque nouveau tour
	 */
	public void next() {
		this.RefreshAge();
		this.RefreshStocks();
	}
	/**
	 * Cette méthode avance l'age de chaque paquet d'arbre de 1 et enleve les arbres qui ont atteint les 45 ans
	 */
	public void RefreshAge() {
		List<Integer> deathlist = new ArrayList<Integer>();
		
		for (int i = 0; i < this.getPaquetsArbres().size(); i++) {
			this.getPaquetsArbres().get(i).setAge(this.getPaquetsArbres().get(i).getAge() + 1);
			if (this.getPaquetsArbres().get(i).getAge() == 45);
			{deathlist.add(i);}
		}
		while (deathlist.size()>0) {
			this.getPaquetsArbres().remove(deathlist.get(deathlist.size()-1));
			deathlist.remove(deathlist.size()-1);
		}
	}
	
	/**
	 * 
	 * @author lucas P
	 * cette fonction calcule la production d'un cycle et la rajoute au stock
	 */
	public void RefreshStocks() {
		for (int i = 0; i < this.PaquetsArbres.size(); i++) {
			this.addQtFeve(this.PaquetsArbres.get(i).getType(),this.PaquetsArbres.get(i).production());
		}
	}
	//cette fonction va essayer de calculer la valeur de notre stock a partir des prix de la criée precedente (pour le moment), il pourra etre amelioré.(lucas p)
	public double EstimationVenteStock() {
		return this.getPrixTC().getValeur()*this.getQuantiteFeve(Feve.FEVE_HAUTE)+this.getPrixTT().getValeur()*this.getQuantiteFeve(Feve.FEVE_MOYENNE)+this.getPrixTF().getValeur()*this.getQuantiteFeve(Feve.FEVE_BASSE);
	}
	public List<Variable> getIndicateurs() {
		List<Variable> res = 
	}

}