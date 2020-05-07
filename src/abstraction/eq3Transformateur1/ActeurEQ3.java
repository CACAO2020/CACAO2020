package abstraction.eq3Transformateur1;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.cacaoCriee.FiliereTestVentesCacaoCriee;
import abstraction.eq8Romu.ventesCacaoAleatoires.FiliereVentesCacaoAleatoires;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class ActeurEQ3 implements IActeur{
	protected Variable stockFeves;
	protected Variable stockChocolat;
	protected Integer cryptogramme;
	protected Journal journalEq3;

	public ActeurEQ3() {
		this.stockFeves=new Variable(getNom()+" stock feves", this, 50);
		this.stockChocolat=new Variable(getNom()+" stock chocolat", this, 100);
		this.journalEq3 = new Journal("Eq3 activites", this);
	}
	
	public String getNom() {
		return "EQ3";
	}

	public String getDescription() {
		return "eq3Transformateur1 transforme du cacao en chocolat de moyenne et haute gamme, labellisé équitable ou non."
				+ "Le centre de notre activité concernera la transformation du chocolat haut de gamme."
				+ "La transformation du chocolat haut de gamme est effectuée totalement en interne depuis l'achat de la fève jusqu'à la revente du produit finalisé."
				+ "Sur le haut de gamme la majorité de la production sera du chocolat labellisé."
				+ "Concernant la gamme moyenne, du chocolat labellisé équitable ou non sera produit."
				+ "L'équipe se garde la liberté d'acheter de la pâte de moyenne gamme";
		
	}
	
	public Color getColor() {
		return new Color(52, 152, 219);
	}

	public void initialiser() {
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	public void next() {
	}

	public List<String> getNomsFilieresProposees() {
		ArrayList<String> filieres = new ArrayList<String>();
		filieres.add("ACHATCACAOCRIE");
		filieres.add("VENTECHOCOLATBOURSE");
		return filieres;
	}

	public Filiere getFiliere(String nom) {
		switch (nom) {
		case "ACHATCACAOCRIE" : return new FiliereVentesCacaoAleatoires();
		case "VENTECHOCOLATBOURSE" : return new FiliereTestVentesCacaoCriee();
		default : return null;
		}
	}
	
	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.stockFeves);
		res.add(stockChocolat);
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.add(this.journalEq3);
		return res;
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
