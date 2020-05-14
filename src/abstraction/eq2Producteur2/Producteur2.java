package abstraction.eq2Producteur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq8Romu.produits.Feve;
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
	 * Pour l'instant elle avance l'age de chaque paquet d'arbre de 1
	 * @author Kristof Szentes
	 */
	public void next() {
		List<Integer> deathlist = new ArrayList<Integer>();
		
		for (int i = 0; i < this.getPaquetsArbres().size()+1; i++) {
			this.getPaquetsArbres().get(i).setAge(this.getPaquetsArbres().get(i).getAge() + 1);
			if (this.getPaquetsArbres().get(i).getAge() == 40);
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
	 */
	public void RefreshStocks() {
		for (int i = 0; i < this.PaquetsArbres.size()+1; i++) {
			this.addQtFeve(this.PaquetsArbres.get(i).getType(),this.PaquetsArbres.get(i).production());
		}
	}
	//cette fonction va essayer de calculer la valeur de notre stock a partir des prix de la criée precedente (pour le moment), il pourra etre amelioré.(lucas p)
	public double EstimationVenteStock() {
		return this.getPrixTC().getValeur()*this.getQuantiteFeve(Feve.FEVE_HAUTE)+this.getPrixTT().getValeur()*this.getQuantiteFeve(Feve.FEVE_MOYENNE)+this.getPrixTF().getValeur()*this.getQuantiteFeve(Feve.FEVE_BASSE);
	}
}
