package abstraction.eq2Producteur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class eq2Acteur implements IActeur {
	
	/*Lucas Y
	 * 
	 */
	private List<PaquetArbres> PaquetsArbres;
	private Journal journalEq2;
	private Integer cryptogramme;
	private List<Variable> parametres;

	public eq2Acteur() {
		this.journalEq2 = new Journal("Journal Principal", this);
		this.PaquetsArbres = new ArrayList<PaquetArbres>();
		this.parametres = new ArrayList<Variable>();
		this.PaquetsArbres.add(new PaquetArbres(100,Feve.FEVE_MOYENNE));
	}

	
	public int getCrypto(){
		return this.cryptogramme;
	}
	
	public List<PaquetArbres> getPaquetsArbres(){
		return this.PaquetsArbres;
	}

	public void ajoutPaquetArbres(PaquetArbres paquetArbres){
		this.PaquetsArbres.add(paquetArbres);
	}
	
	public double NbTotalArbres() {
		double TotalArbre =0; 
		for (int i=0; i<this.getPaquetsArbres().size(); i++) {
			TotalArbre += this.getPaquetsArbres().get(i).getNbreArbres();
		}
		return TotalArbre;
	}
	
	@Override
	public String getNom() {
		return "Moulacao";
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void next() {
		for (int i = 0; i < this.getPaquetsArbres().size(); i++) {
			this.getPaquetsArbres().get(i).setAge(this.getPaquetsArbres().get(i).getAge() + 1);
		}
	}

	@Override
	public List<String> getNomsFilieresProposees() {
			return new ArrayList<String>();
	}

	@Override
	public Filiere getFiliere(String nom) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
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
