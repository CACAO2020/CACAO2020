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
	
	public GestionCriee(Producteur1 sup) //Clément
	{
		//Prix par unité
		this.lastPrixMin = 0;
		this.lastPrixVente = 0;
		this.producteur1 = sup;
	}
	
	public GestionCriee(double lastPrixMinInit, double lastPrixVenteInit, IActeur sup) //Clément
	{
		this.lastPrixMin = lastPrixMinInit;
		this.lastPrixVente = lastPrixVenteInit;
	}


	//Clément 
	public LotCacaoCriee getLotEnVente() {
		this.producteur1.ajouterJournaux("[GestionCriee] - Mise en vente de : " + producteur1.getStock());
		double quantiteAVendre = producteur1.getStock();
		if(quantiteAVendre == 0)
		{
			return null;
		}
		this.lastPrixMin = lastPrixVente+10;
		return new LotCacaoCriee(this.producteur1, Feve.FEVE_BASSE, quantiteAVendre, quantiteAVendre * (lastPrixVente+10));
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


	
	//Clément
	public void notifierVente(PropositionCriee proposition) {
		this.producteur1.ajouterJournaux("[GestionCriee] - Vente de : " + proposition.getQuantiteEnTonnes());
		this.producteur1.removeStock(proposition.getQuantiteEnTonnes());
	}
}
