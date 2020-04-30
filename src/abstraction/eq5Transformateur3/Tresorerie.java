package abstraction.eq5Transformateur3;
/** @author Simon MORO  */
public class Tresorerie {
    /**
     * Gère l'argent de la société pour la répartir entre les filiaires et voir si toutes les décisions sont cohérentes en terme bugetaires.
     * Elle est agrégé dans la classe Transformateur3.
     * 
     * Infos utiles à récupérer : montant compte en banque ; stock ; historique des ventes / achats ; agios / découverts 
     * ; montant max d'investissement de chaque filière ;
     * 
     * Ces infos sont sûrement récupérées principalement dans le journal
     */
	
	public getMontantCompte() {
		return 0
	}
	
	public getStock() {
		return 0
		}
	
	public getHistorique() {
		return null
	}
	
	public getAgios() {
		return 0
	}
	
	public getFacteurPrioriteGamme () {
		/**
	     * Le facteur qui décrit la priorité que nous mettons sur le bas de gamme par rapport au haut de gamme (évolue au cours de la
	     * simulation)
	     */
	}
	
	public getInvestissementMaxBasDeGamme() {
		/**
	     * Renvoie l'investissement maximum possible à faire dans le bas de gamme en fonction du facteur de priorité que l'on s'impose
	     * ainsi que du montant de notre trésorerie
	     */
	}
	
	public getInvestissementMaxHautDeGamme() {
		/**
	     * Renvoie l'investissement maximum possible à faire dans le haut de gamme en fonction du facteur de priorité que l'on s'impose
	     * ainsi que du montant de notre trésorerie
	     */
	}
}