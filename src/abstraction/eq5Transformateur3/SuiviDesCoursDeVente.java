package abstraction.eq5Transformateur3;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Filiere;
import abstraction.fourni.Variable;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


/** @author francoisgoujon 
 * 
 *
 */

public class SuiviDesCoursDeVente {
	
	private Transformateur3 acteur;
	private Map<Chocolat, List<Double>> prixStat;
	
	/* prixStat : pour chaque type de chocolat, on garde le prix min, max et moyen.
	 */
	
	
	public SuiviDesCoursDeVente(Transformateur3 acteur) {
		this.acteur = acteur;
		this.prixStat = new HashMap<Chocolat, List<Double>>();
		this.prixStat.put(Chocolat.CHOCOLAT_BASSE, new ArrayList<Double>(3));
		this.prixStat.put(Chocolat.CHOCOLAT_MOYENNE, new ArrayList<Double>(3));
		this.prixStat.put(Chocolat.CHOCOLAT_HAUTE, new ArrayList<Double>(3));
		this.prixStat.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE, new ArrayList<Double>(3));
		this.prixStat.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, new ArrayList<Double>(3));
				
		for (Chocolat choco : Chocolat.values()) {
			this.prixStat.get(choco).add(0.0);
			this.prixStat.get(choco).add(0.0);
			this.prixStat.get(choco).add(0.0);
		}
	}
	public HashMap<Chocolat, Integer> next() {
		HashMap<Chocolat, Integer> map = this.avisAchat();
		this.updatePrixStat();
		return map;
	}
	
	public double getCours(Chocolat choco, int n) {
		return Filiere.LA_FILIERE.getIndicateur("BourseChoco cours " + choco.name()).getHistorique().get(n).getValeur();	
	}
	public double getCours(Chocolat choco) {
		return Filiere.LA_FILIERE.getIndicateur("BourseChoco cours " + choco.name()).getHistorique().get(Filiere.LA_FILIERE.getEtape()).getValeur();
	}
	public double getMax(Chocolat choco) {
		return this.prixStat.get(choco).get(1);
	}
	public double getMin(Chocolat choco) {
		return this.prixStat.get(choco).get(0);
	}
	public double getMoy(Chocolat choco) {
		return this.prixStat.get(choco).get(2);
	}

	
	public void updatePrixStat(Chocolat choco) {
		double cours = this.getCours(choco);
		if (cours <= this.getMin(choco) ) {
			this.prixStat.get(choco).set(0, cours);
		}
		if (cours >= this.getMax(choco) ) {
			this.prixStat.get(choco).set(1, cours);
		}
		double moyenne = this.getMoy(choco);
		int tour = Filiere.LA_FILIERE.getEtape() ;
		double nouvelleMoyenne = moyenne*(tour/(tour+1.)) + cours/(tour+1.);
		this.prixStat.get(choco).set(2, nouvelleMoyenne);
	}
	/**
	 * 
	 * @return une map à 5 entrée, une pour chaque type de chocolat, avec pour chaque entrée : 0 si le cours est dans la moyenne, 1 si le cours est en dessous du min
	 */
	
	
	public void updatePrixStat() {
		for (Chocolat choco : Chocolat.values()) {
			updatePrixStat(choco);
		}
	}
	/** 
	 * Renvoie le cours en poucentage de la moyenne (renvoie 1 si les cours = moyenne)
	 */
	public double getCours_Moyenne(Chocolat choco) {
		Double moy = this.getMoy(choco);
		Double cours = this.getCours(choco);
		return cours/moy;
	}
	/**
	 * 
	 * @param choco
	 * @return 0 si le prix est entre min et max ou entre 0.5 et 2 moyenne : indifférent
	 * 			1 si le prix est < min ou < 1/2 moyenne : pas d'achat
	 * 			2 si le prix est > max ou > 2 moyenne : achat
	 */
	public int avisAchat(Chocolat choco) {
		double cours = this.getCours(choco);
		int res = 0;
		if (cours <= this.prixStat.get(choco).get(0) || this.getCours_Moyenne(choco) <= 0.5) {
			res = 1;
		}
		if (cours >= this.prixStat.get(choco).get(1) || this.getCours_Moyenne(choco) >= 2) {
			res = 2;
		}
		return res;
	}
	/**
	 * 
	 * @param choco
	 * @return Une map à 5 entrée, une pour chaque type de chocolat, avec pour chaque entrée : 0 si le prix est entre min et max ou entre 0.5 et 2 moyenne : indifférent
	 * 			1 si le prix est < min ou < 1/2 moyenne : pas d'achat
	 * 			2 si le prix est > max ou > 2 moyenne : achat
	 */
	
	public HashMap<Chocolat, Integer> avisAchat() {
		HashMap<Chocolat, Integer> map = new HashMap<Chocolat, Integer>();
		for (Chocolat choco : Chocolat.values()) {
			map.put(choco,this.avisAchat(choco));
		}
		return map;
	}
	

}
