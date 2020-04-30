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




//Création par Clément
class GestionCriee {
	
	private double lastPrixMin;
	private double lastPrixVente;
	
	public GestionCriee()
	{
		this.lastPrixMin = 0;
		this.lastPrixVente = 0;
	}
	
	public GestionCriee(double lastPrixMinInit, double lastPrixVenteInit)
	{
		this.lastPrixMin = lastPrixMinInit;
		this.lastPrixVente = lastPrixVenteInit;
	}
	
	public LotCacaoCriee getLotEnVente(IVendeurCacaoCriee acteur1, double valeurStock)
	{
		return new LotCacaoCriee(acteur1, Feve.FEVE_BASSE, valeurStock, 10);
	}
	
	@Override
	public void notifierAucuneProposition(LotCacaoCriee lot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PropositionCriee choisir(List<PropositionCriee> propositions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifierVente(PropositionCriee proposition) {
		// TODO Auto-generated method stub
		
	}
}
