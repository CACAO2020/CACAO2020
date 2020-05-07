package abstraction.eq6Distributeur1;
import java.util.ArrayList;
import java.util.HashMap;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.fourni.Filiere;
import abstraction.fourni.Variable;

public class Prix { /** @author Mélissa Tamine */
	
	private HashMap<Chocolat,Double> prixachatParProduit; // HashMap contenant le prix par produit (prix produit achat auprès des transformateurs)
    private HashMap<Chocolat,Double> margeParProduit; // HashMap contenant la marge de prix par produit mis en vente
    private Distributeur1 EQ6 ;
    
    private Variable prixMG; //Moyenne gamme
    private Variable prixBG; // Bas de gamme
    private Variable prixHGE; // Haut de gamme équitable
    
public Prix(Distributeur1 distributeur) {
		
		EQ6 = distributeur; 

		this.prixMG = new Variable("EQ6 "+Chocolat.CHOCOLAT_MOYENNE.toString(), EQ6, 10);
        Filiere.LA_FILIERE.ajouterIndicateur(this.prixMG);
        
        this.prixBG = new Variable("EQ6 "+Chocolat.CHOCOLAT_BASSE.toString(), EQ6, 5);
        Filiere.LA_FILIERE.ajouterIndicateur(this.prixBG);

        this.prixHGE = new Variable("EQ6 "+Chocolat.CHOCOLAT_HAUTE_EQUITABLE.toString(), EQ6, 20);
        Filiere.LA_FILIERE.ajouterIndicateur(this.prixHGE);

        
        this.margeParProduit = new HashMap<Chocolat, Double>();
        this.margeParProduit.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, 1.5);
        this.margeParProduit.put(Chocolat.CHOCOLAT_MOYENNE, 1.10);
        this.margeParProduit.put(Chocolat.CHOCOLAT_BASSE, 1.05);

        this.prixachatParProduit =  new HashMap<Chocolat,Double>();
        this.prixachatParProduit.put(Chocolat.CHOCOLAT_HAUTE_EQUITABLE, 10.0);
        this.prixachatParProduit.put(Chocolat.CHOCOLAT_MOYENNE, 4.0);
        this.prixachatParProduit.put(Chocolat.CHOCOLAT_BASSE, 2.0 );
      
	}

public Variable getPrix (Chocolat c) {

    if (c.getGamme()==Gamme.MOYENNE && !(c.isEquitable())) {
            return this.prixMG;
    }
    if (c.getGamme()==Gamme.BASSE && !(c.isEquitable())){ 
            return this.prixBG;
    }
    if (c.getGamme()==Gamme.HAUTE && (c.isEquitable())){
            return this.prixHGE;
    }
    else {
            return null;
    	}
}

public void setMargeParProduit(Chocolat choco, double marge) {
	 if  (!this.margeParProduit.containsKey(choco)) {
        return ;
	 }
this.margeParProduit.put(choco, marge);
}



public double getMargeParProduit(Chocolat choco) {
    if  (!this.margeParProduit.containsKey(choco)) {
            return Double.NaN;
    }
    return (this.margeParProduit.containsKey(choco)? this.margeParProduit.get(choco) : 0.0);
}



public double getPrixachatParProduit(Chocolat choco) {
    if  (!this.prixachatParProduit.containsKey(choco)) {
            return Double.NaN;
    }
    return this.prixachatParProduit.get(choco);
}


public HashMap<Chocolat,Double> getPrixachat() {
    return this.prixachatParProduit;

}

public void setPrixachatParProduit(Chocolat c) {
	// À compléter avec protocole de bourse
}

public double getPrixParProduit(Chocolat c) {
	setPrixachatParProduit(c);
	
	double prix = getPrixachatParProduit(c)*getMargeParProduit(c);
	this.getPrix(c).setValeur(EQ6, prix);
	return getPrix(c).getValeur();
}

public Variable getPrixMG() {
		return prixMG;
	}

public Variable getPrixBG() {
		return prixBG;
	}

public Variable getPrixHGE() {
		return prixHGE;
	  
	}


public void ajustementMarge(ArrayList<Double> Historique, Chocolat c ) { //Ajustement de la marge
     
	if (Filiere.LA_FILIERE.getEtape() > 24) { // Récupération de l'historique des ventes
		
	double vente_stockC= 0;
	int steps = 0;

	if (Filiere.LA_FILIERE.getEtape() >= 24) {
		for (int k=0 ; k<Historique.size()-1; k++) {
			vente_stockC+=Historique.get(k);
			steps+=1;
		}
	}
	double moyenneventeprecedente = vente_stockC/steps; // Vente moyenne par step

	//Baisse de la marge si les dernières ventes sont mauvaises (inférieures à 80% de nos ventes précédentes) et si notre prix reste au dessus de 0.97*moyenneprixvendeur
	
	if (Historique.size() > 24 && Historique.get(Historique.size() -1) < moyenneventeprecedente*0.8) {
		if (getPrixParProduit(c) > this.getPrixachatParProduit(c)) {
			double nouvellemarge = this.getMargeParProduit(c)*0.97;
			setMargeParProduit(c, nouvellemarge);
		}
	}

	//Augmentation de la marge de 5% si les dernières ventes sont hautes (supérieures à 2 fois nos ventes précédentes) et si prix actuel assez faible
	
	if (Historique.size() > 24 && Historique.get(Historique.size() -1) > moyenneventeprecedente*2) {
		if (getPrixParProduit(c) <(this.getMargeParProduit(c)+0.1)*this.getPrixachatParProduit(c)) {
			double nouvellemarge = this.getMargeParProduit(c)*1.05;
			setMargeParProduit(c, nouvellemarge);
		}
	}
	}
}


}

