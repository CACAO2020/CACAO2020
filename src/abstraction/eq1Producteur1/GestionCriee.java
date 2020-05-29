package abstraction.eq1Producteur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq8Romu.cacaoCriee.IVendeurCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.LotCacaoCriee;
import abstraction.eq8Romu.cacaoCriee.PropositionCriee;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;




/** @author Clément 
 * Implémente les fonctions nécessaire pour la vente a la criée
 * ainsi que les fonctions telles que la mise a jour du prix minimum de vente.
 * 
 * Dans une v1, nous avons testé plusiseurs méthode pour définire le prix minimum de vente d'un lot
 * cependant, pour nous alligner avec ce qui a été fait dans l'équipe 2, nous fixerons les prix a la tonne
 * dans le constructeur.
*/
class GestionCriee //implements IVendeurCacaoCriee 
{
	
	private double lastPrixVenteFeveBasse;
	private double lastPrixVenteFeveMoyenne;
	private boolean venteBasseSurCeTour;
	/**
	 * Booleen qui symbolise si l'ont vend des fèves de basse qualité sur ce tour
	 * puisque l'on ne peut mettre en vent qu'un lot par tour
	 */
	
	private Producteur1 producteur1;
	private List<LotCacaoCriee> miseEnVenteLog;
	private List<PropositionCriee> venduLog;
	private double v1PrixBasse;
	private double v1PrixMoyenne;
	
	public GestionCriee(Producteur1 sup) //Clément
	{
		//Prix par unité
		this.lastPrixVenteFeveBasse = 0;
		this.lastPrixVenteFeveMoyenne = 0;
		this.producteur1 = sup;
		this.venduLog = new ArrayList<PropositionCriee>();
		this.miseEnVenteLog = new ArrayList<LotCacaoCriee>();
		this.venteBasseSurCeTour = false;
		v1PrixBasse = 2500;
		v1PrixMoyenne = 1900;
	}
	
	public GestionCriee(double lastPrixMinInit, double lastPrixVenteInit, IActeur sup) //Clément
	{
		this.lastPrixVenteFeveBasse = lastPrixVenteInit;
	}


	private LotCacaoCriee makeLot(Feve typeFeve, double quantiteAVendre)
	{
		double PrixMoy = this.prixMoyenDernierreVentes(typeFeve);
		double prixVente = 0; //quantiteAVendre * (PrixMoy+0.004);
		if(typeFeve == Feve.FEVE_BASSE)
		{
			prixVente = this.v1PrixBasse*quantiteAVendre;
		}
		else
		{
			prixVente = this.v1PrixMoyenne*quantiteAVendre;
		}
		this.producteur1.ajouterJournaux("[GestionCriee] - Mise en vente de : " + typeFeve + " en quantité "+ quantiteAVendre + " au prix minimum de" + prixVente);
		if(quantiteAVendre == 0)
		{
			return null;
		}
		LotCacaoCriee lot = new LotCacaoCriee(this.producteur1, typeFeve, quantiteAVendre, prixVente);
		this.miseEnVenteLog.add(lot);
		return lot;
	}

	//Clément 
	public LotCacaoCriee getLotEnVente() {
		if(venteBasseSurCeTour)
		{
			this.venteBasseSurCeTour = !this.venteBasseSurCeTour;
			return makeLot(Feve.FEVE_BASSE, producteur1.getStock(Feve.FEVE_BASSE));
		}
		else
		{
			this.venteBasseSurCeTour = !this.venteBasseSurCeTour;
			return makeLot(Feve.FEVE_MOYENNE, producteur1.getStock(Feve.FEVE_MOYENNE));
		}


	}
	
	//Clément
	/** 
	* Si on obtient aucunne proposition pour un lot, alors
	* On diminue le prix min acceptable en changeant directement
	* la variable lastPrixFeve... 
	*/
	public void notifierAucuneProposition(LotCacaoCriee lot) {
		if(lot.getFeve() == Feve.FEVE_BASSE)
		{
			this.lastPrixVenteFeveBasse -= 10;
			if(this.lastPrixVenteFeveBasse <= 0)
			{
				this.lastPrixVenteFeveBasse = 0.001;
			}
		}
		else
		{
			this.lastPrixVenteFeveMoyenne -= 10;
			if(this.lastPrixVenteFeveMoyenne <= 0)
			{
				this.lastPrixVenteFeveMoyenne = 0.001;
			}
		}
	}

	//Clément
	public PropositionCriee choisir(List<PropositionCriee> propositions) {
		int n = propositions.size();
		double prixMax = 0.0000002; // On set le prix max a quelque chose de différent par sécurité pour ne pas accepter des lots de prix 0
		int indPrixMax = -1;
		for(int i = 0; i < n; i++)
		{
			if(propositions.get(i).getPrixPourUneTonne() >= prixMax)
			{
				indPrixMax = i;
			}
		}
		if(indPrixMax >= 0)
		{
			return propositions.get(indPrixMax);
		}
		else
		{
			return null;
		}
	}


	public List<PropositionCriee> getLotVendu()
	{
		return this.venduLog;
	}
	
	//Clément
	public void notifierVente(PropositionCriee proposition) {
		Feve typeFeve = proposition.getFeve();
		this.producteur1.ajouterJournaux("[GestionCriee] - Vente de : " + proposition.getQuantiteEnTonnes() + " de type : " + typeFeve);
		this.producteur1.removeStock(proposition.getQuantiteEnTonnes(), typeFeve);
		this.venduLog.add(proposition);
	}

	/**
	 * Calcul le prix moyen sur les dernières ventes
	 * @return prix_moyen
	 */
	public double prixMoyenDernierreVentes(Feve typeFeve)
	{
		int n = this.venduLog.size();
		if(n == 0)
		{
			return 0;
		}
		int i = 0;
		int j = 0;
		double moyenne = 0;
		while(i < n && i < 10)
		{
			if(this.venduLog.get(n-i-1).getFeve() == typeFeve)
			{
				moyenne += this.venduLog.get(n-i-1).getPrixPourUneTonne();
				j++;
			}
			i++;
		}
		return moyenne / (double) j;
	}
}
