package abstraction.eq2Producteur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.chocolatBourse.FiliereTestVentesChocolatBourse;
import abstraction.eq8Romu.clients.FiliereTestClientFinal;
import abstraction.eq8Romu.contratsCadres.FiliereTestContratCadre;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.ventesCacaoAleatoires.FiliereVentesCacaoAleatoires;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class eq2Acteur implements IActeur {
	
	/*Lucas Y
	 * 
	 */
	private List<PaquetArbres> PaquetsArbres;
	private List<Usine>Usines;
	private Journal journalEq2;
	private Integer cryptogramme;
	private List<Variable> parametres;
	

	public eq2Acteur() {
		this.journalEq2 = new Journal("Journal Principal Eq2", this);
		this.PaquetsArbres = new ArrayList<PaquetArbres>();
		this.parametres = new ArrayList<Variable>();
		this.Usines = new ArrayList<Usine>();
	}

	
	public int getCrypto(){
		return this.cryptogramme;
	}
	
	public List<PaquetArbres> getPaquetsArbres(){
		return this.PaquetsArbres;
	}

	public List<Usine> getUsines(){
		return this.Usines;
	}
	
	public void ajoutPaquetArbres(PaquetArbres paquetArbres){
		this.PaquetsArbres.add(paquetArbres);
	}
	
	public void ajoutUsine(Usine usine){
		this.Usines.add(usine);
	}
	
	public double NbTotalArbres() {
		double TotalArbre =0; 
		for (int i=0; i<this.getPaquetsArbres().size(); i++) {
			TotalArbre += this.getPaquetsArbres().get(i).getNbreArbres();
		}
		return TotalArbre;
	}
	
	public double NbTotalMachines() {
		double TotalMachine =0; 
		for (int i=0; i<this.getUsines().size(); i++) {
			TotalMachine += this.getUsines().get(i).getNbMachine();
		}
		return TotalMachine;
	}
	
	@Override
	public String getNom() {
		return "Return of the Stonks";
	}

	@Override
	public String getDescription() {
		return "Producteur 2, basé au Ghana. Nous représentons une coopérative de producteurs, avec pour but principal de garantir les meilleures conditions de vie possible à nos producteurs";
	}

	@Override
	public Color getColor() {
		return new Color(46, 204, 113);
	}

	@Override
	public void initialiser() {
		// Au début de la partie, on va commencer avec quelques arbres déjà plantés
		this.getPaquetsArbres().add(new PaquetArbres(2500, Feve.FEVE_BASSE, 240));
		this.getPaquetsArbres().add(new PaquetArbres(1500, Feve.FEVE_MOYENNE, 289));
		this.getPaquetsArbres().add(new PaquetArbres(1000, Feve.FEVE_HAUTE, 48));
		this.getPaquetsArbres().add(new PaquetArbres(500, Feve.FEVE_MOYENNE_EQUITABLE,70));
		this.getPaquetsArbres().add(new PaquetArbres(500, Feve.FEVE_HAUTE_EQUITABLE,80));

	}

	@Override
	public void next() {
		
		}
	

	@Override
	public List<String> getNomsFilieresProposees() { //jfais des tests
		//return new ArrayList<String>();
		ArrayList<String> filieres = new ArrayList<String>();
		filieres.add("VCA"); // Ventes  Cacao Aleatoires
		filieres.add("TESTCRIEEEQ2"); 
		filieres.add("TESTBOURSE"); 
		filieres.add("TESTCLIENT"); 
		filieres.add("TESTCC");//Contrat Cadre 
		return filieres;
}

@Override
public Filiere getFiliere(String nom) {
	switch (nom) {
	case "VCA" : return new FiliereVentesCacaoAleatoires();
	case "TESTCRIEEEQ2" : return new TesteurVenteCriée();
	case "TESTBOURSE" : return new FiliereTestVentesChocolatBourse();
	case "TESTCLIENT" : return new FiliereTestClientFinal();
	case "TESTCC" : return new FiliereTestContratCadre();
    default : return null;
	}
}


	@Override
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	@Override
	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}
	public Journal getJournalPrincipal() {
		return this.journalEq2;
	}
	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(this.journalEq2);
		return res;
	}

	@Override
	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}

	public void notificationFaillite(IActeur acteur) {
		if (this==acteur) {
		System.out.println("I'll be back... or not... "+this.getNom());
		} else {
			System.out.println("Poor "+acteur.getNom()+"... We will miss you. "+this.getNom());
		}
	}
	
	public void notificationOperationBancaire(double montant) {
	}
	

}
