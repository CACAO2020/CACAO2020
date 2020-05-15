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




/** @author Clément */
class GestionCriee //implements IVendeurCacaoCriee 
{
	
	private double lastPrixMin;
	private double lastPrixVente;
	private Producteur1 producteur1;
	private List<LotCacaoCriee> miseEnVenteLog;
	private List<PropositionCriee> venduLog;
	
	public GestionCriee(final Producteur1 sup) //Clément
	{
		//Prix par unité
		this.lastPrixMin = 0;
		this.lastPrixVente = 0;
		this.producteur1 = sup;
		this.venduLog = new ArrayList<PropositionCriee>();
		this.miseEnVenteLog = new ArrayList<LotCacaoCriee>();
	}
	
	public GestionCriee(final double lastPrixMinInit, final double lastPrixVenteInit, final IActeur sup) //Clément
	{
		this.lastPrixMin = lastPrixMinInit;
		this.lastPrixVente = lastPrixVenteInit;
	}


	private LotCacaoCriee makeLot(Feve typeFeve)
	{
		this.producteur1.ajouterJournaux("[GestionCriee] - Mise en vente de : " + producteur1.getStock(typeFeve));
		double quantiteAVendre = producteur1.getStock(typeFeve);
		if(quantiteAVendre == 0)
		{
			return null;
		}
		this.lastPrixMin = lastPrixVente+10;
		LotCacaoCriee lot = new LotCacaoCriee(this.producteur1, typeFeve, quantiteAVendre, quantiteAVendre * (lastPrixVente+10));
		this.miseEnVenteLog.add(lot);
		return lot;
	}

	//Clément 
	public LotCacaoCriee getLotEnVente() {
		return makeLot(Feve.FEVE_BASSE);
	}
	
	//Clément
	public void notifierAucuneProposition(LotCacaoCriee lot) {
		lastPrixVente -= 20;
	}

	//Clément
	public PropositionCriee choisir(List<PropositionCriee> propositions) {
		int n = propositions.size();
		double prixMax = 0.02;
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
}
