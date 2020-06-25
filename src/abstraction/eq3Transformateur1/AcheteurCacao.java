package abstraction.eq3Transformateur1;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.cacaoCriee.SuperviseurCacaoCriee;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public abstract class AcheteurCacao extends AchatPateCC implements abstraction.eq8Romu.cacaoCriee.IAcheteurCacaoCriee{
	private HashMap<Feve, Double> nb_proposition;
	
	public AcheteurCacao() {
		nb_proposition = new HashMap<Feve,Double>();
		nb_proposition.put(Feve.FEVE_HAUTE_EQUITABLE,0.0);
		nb_proposition.put(Feve.FEVE_MOYENNE_EQUITABLE,0.0);
		nb_proposition.put(Feve.FEVE_HAUTE,0.0);
		nb_proposition.put(Feve.FEVE_MOYENNE,0.0);
		nb_proposition.put(Feve.FEVE_BASSE,0.0);
	}
	

	
	public double getPrixLot(LotCacaoCriee lot) {
		return lot.getPrixMinPourUneTonne()*lot.getQuantiteEnTonnes();
	}
	
	/** fonction qui retourne le prix de vente du lot en bourse après les coûts de transformation, prend en compte le cours en bourse du tour actuel**/
	public double getPrixBourse(LotCacaoCriee lot) {
		return Filiere.LA_FILIERE.getIndicateur("BourseChoco cours "+equivalentChocoFeve(lot.getFeve())).getValeur()-5000;
	}
	
	public double proposerPrix(LotCacaoCriee lot, double nb_proposition) { /* fonction mathématique qui s'adapte à notre stratégie */
		if (lot.getFeve()==Feve.FEVE_BASSE) {
			return (1.85 - 1 /(Math.pow(nb_proposition, 1/3)))*lot.getPrixMinPourUneTonne();
		}
		if (lot.getFeve()==Feve.FEVE_MOYENNE) {
			return (1.90 - 1 /(Math.pow(nb_proposition, 1/3)))*lot.getPrixMinPourUneTonne();
		}
		if (lot.getFeve()==Feve.FEVE_MOYENNE_EQUITABLE) {
			return (1.95 + (nb_proposition/10000)- 1 /(Math.pow(nb_proposition, 1/3)))*lot.getPrixMinPourUneTonne();
		}
		if (lot.getFeve()==Feve.FEVE_HAUTE) {
			return (2.0 + (nb_proposition/10000)- 1 /(Math.pow(nb_proposition, 1/3)))*lot.getPrixMinPourUneTonne();
		}
		else  { /*Fèves Haute Qualité et équitables*/
			return (2.05 + (nb_proposition/10000)- 1 /(Math.pow(nb_proposition, 1/3)))*lot.getPrixMinPourUneTonne();
		}
		}
	
	
	/** @author Amaury & Nathan 
	 * On propose un prix qui augmente au fur et à mesure des propositions et en fonction de notre besoin en fève (ie si notre stock est vide)
	 * Les différents tests qu'on effectue sont :
	 * */
	
	public double proposerAchat(LotCacaoCriee lot) {
		if (this.getPrixBourse(lot) < lot.getPrixMinPourUneTonne()/2) 
		{ /* C'est le cas où notre lot vaut moins cher en bourse que le prix auquel on l'achète */
			this.journalAchat.ajouter("Le lot" + lot.toString() +"au prix " + Journal.doubleSur(lot.getPrixMinPourUneTonne(), 4) + "ne permet pas de faire des bénéfices");
			/* Achat vraiment pas cher si le client vend son lot trop cher*/
			if ((lot.getPrixMinPourUneTonne()*lot.getQuantiteEnTonnes() < this.MontantCompte*10)&&(Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape(),this.equivalentChocoFeve(lot.getFeve()))
					>(this.getStockFeves(lot.getFeve()) + this.getStockChocolat(this.equivalentChocoFeve(lot.getFeve()))) + lot.getQuantiteEnTonnes())
				&&(nb_proposition.get(lot.getFeve())<=2)) 
			{
				return this.getPrixBourse(lot)/10;

			}
		}

		else {
			if((Filiere.LA_FILIERE.getVentes(Filiere.LA_FILIERE.getEtape(),this.equivalentChocoFeve(lot.getFeve())) + 15
				>(this.getStockFeves(lot.getFeve()) + this.getStockChocolat(this.equivalentChocoFeve(lot.getFeve()))) + lot.getQuantiteEnTonnes())
				&&((this.getStockFeves((lot.getFeve()))==0) || (this.getStockFeves(lot.getFeve())<=this.getStockPate(this.equivalentChocoFeve(lot.getFeve())) + 15)) /* On achète pas de fèves sauf si on en a pas en stock où qu'on en a moins que le stock de pate de cette fève*/
				 /*c'est du au fait que la transformation se fasse avant l'achat à la criée donc si il nous reste des fèves c'est que nous n'avions pas l'argent pour les transformer*/ 
				&&(this.proposerPrix(lot, nb_proposition.get(lot.getFeve())) < this.MontantCompte)/* on vérifie qu'on ne fait pas faillite*/
				&& (this.proposerPrix(lot, nb_proposition.get(lot.getFeve())) < this.getPrixBourse(lot)))
				{
					this.journalAchat.ajouter("Proposition d'achat du lot" + lot.toString() + ", Nombre de propositions = " + nb_proposition.get(lot.getFeve()) + ", au prix :" + Journal.doubleSur(this.proposerPrix(lot, nb_proposition.get(lot.getFeve())),4));
					return this.proposerPrix(lot, nb_proposition.get(lot.getFeve())) ; 			

				}
				else {	
						this.journalAchat.ajouter("Impossible d'acheter le lot" + lot.toString() + ", Nombre de propositions = " + nb_proposition.get(lot.getFeve()) + ", au prix" + Journal.doubleSur(this.proposerPrix(lot, nb_proposition.get(lot.getFeve())),4));
						return 0.0;

					}
			}
		this.journalAchat.ajouter("Pas de proposition d'achat pour le lot " + lot.toString() + ", Nombre de propositions = " + nb_proposition.get(lot.getFeve()));
		return 0.0;
		
	}
	
	public void reset_propositions() {
		nb_proposition.clear();
		nb_proposition.put(Feve.FEVE_HAUTE_EQUITABLE,0.0);
		nb_proposition.put(Feve.FEVE_MOYENNE_EQUITABLE,0.0);
		nb_proposition.put(Feve.FEVE_HAUTE,0.0);
		nb_proposition.put(Feve.FEVE_MOYENNE,0.0);
		nb_proposition.put(Feve.FEVE_BASSE,0.0);
	}
	/** @author Nathan Olborski
	 * On fait un simple print de la notification de vente ou de refus de vente, on pourra ajouter cette notification au journal quand l'attribut journal sera mis en protected et qu'on y aura accès dans cette classe
	 */
	public void notifierPropositionRefusee(PropositionCriee proposition) {
		this.journalAchat.ajouter("La proposition " + proposition.getLot().toString() + ", de " + proposition.getVendeur().toString() + ", au prix " + Journal.doubleSur(proposition.getPrixPourLeLot(),4)
		+ "a été refusée");
		nb_proposition.put(proposition.getFeve(),nb_proposition.get(proposition.getFeve())+1);
		
	}

	public void notifierVente(PropositionCriee proposition) {
		//this.reset_propositions();
		this.setCoutFeves(proposition.getLot().getFeve(), this.calculCoutFeve(proposition.getLot().getFeve(), proposition.getQuantiteEnTonnes(), proposition.getPrixPourUneTonne()));
		this.setStockFeves(proposition.getLot().getFeve(), proposition.getQuantiteEnTonnes());
		this.journalAchat.ajouter("Lot " + proposition.getLot().toString() + ", de " + proposition.getVendeur().toString() + ", au prix " + Journal.doubleSur(proposition.getPrixPourLeLot(),4) + " a été effectué");
		nb_proposition.put(proposition.getFeve(), 0.0);

	}

	/** @author Nathan Olborski
	 * Simple getter	*/
	public Integer getCryptogramme(SuperviseurCacaoCriee superviseur) {
		if (superviseur != null) {
			return this.cryptogramme;}
		else return null;
	}




}
