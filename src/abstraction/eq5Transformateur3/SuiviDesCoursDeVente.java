package abstraction.eq5Transformateur3;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Filiere;
import abstraction.fourni.Variable;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class SuiviDesCoursDeVente {
	
	private Transformateur3 acteur;
	private Map<Chocolat, List<Double>> prixStat;
	private Integer tour;
	
	/* prixStat : pour chaque type de chocolat, on garde le prix min, max et moyen.
	 */
	
	
	public SuiviDesCoursDeVente(Transformateur3 acteur) {
		this.acteur = acteur;
		this.prixStat = new HashMap<Chocolat, List<Double>>();
		this.tour = Filiere.LA_FILIERE.getEtape();
		this.prixStat.put(Chocolat.CHOCOLAT_BASSE, new ArrayList<Double>(3));
		this.prixStat.put(Chocolat.CHOCOLAT_MOYENNE, new ArrayList<Double>(3));
		this.prixStat.put(Chocolat.CHOCOLAT_HAUTE, new ArrayList<Double>(3));
		this.prixStat.put(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE, new ArrayList<Double>(3));
		this.prixStat.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, new ArrayList<Double>(3));
				
		for (Chocolat choco : Chocolat.values()) {
			this.prixStat.get(choco).set(0, 0.0);
			this.prixStat.get(choco).set(1, 0.0);
			this.prixStat.get(choco).set(2, 0.0);
		}
	}
	
	public Double getCours(Chocolat choco, int n) {
		return Filiere.LA_FILIERE.getIndicateur("BourseChoco cours " + choco.name()).getHistorique().get(n).getValeur();	
	}
	public Double getCours(Chocolat choco) {
		return Filiere.LA_FILIERE.getIndicateur("BourseChoco cours " + choco.name()).getHistorique().get(Filiere.LA_FILIERE.getEtape()).getValeur();
	}
	
	public void updatePrixStat(Chocolat choco) {
		Double cours = Filiere.LA_FILIERE.getIndicateur("BourseChoco cours " + choco.name()).getHistorique().get(this.tour).getValeur();
		if (cours < this.prixStat.get(choco).get(0) ) {
			this.prixStat.get(choco).set(0, cours);
		}
		if (cours > this.prixStat.get(choco).get(1) ) {
			this.prixStat.get(choco).set(1, cours);
		}
		Double moyenne = this.prixStat.get(choco).get(2);
		Double nouvelleMoyenne = moyenne*(this.tour/(this.tour+1)) + cours/(this.tour+1);
		this.prixStat.get(choco).set(2, nouvelleMoyenne);
	}
	public void updatePrixStat() {
		for (Chocolat choco : Chocolat.values()) {
			updatePrixStat(choco);
		}
	}
	

}
