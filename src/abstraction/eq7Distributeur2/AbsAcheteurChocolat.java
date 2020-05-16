package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.tools.javac.util.Pair;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AbsAcheteurChocolat {
	
	protected Journal journal;
	
	protected Map<Chocolat, Variable> demandesChoco;
	protected Map<Chocolat, Variable> chocoReceptionne;
	protected Pair<Chocolat, Double> commandeImpayee;

	protected Distributeur2 ac;
	
	public Color titleColor = Color.BLACK;
	public Color alertColor = Color.RED;
	public Color warningColor = Color.ORANGE;
	public Color positiveColor = Color.GREEN;
	public Color descriptionColor = Color.YELLOW;
	
	public AbsAcheteurChocolat(Distributeur2 ac) {
		this.ac = ac;	
		initJournaux();
		demandesChoco = new HashMap<Chocolat, Variable>();
		chocoReceptionne = new HashMap<Chocolat, Variable>();
		commandeImpayee = null;
		
		for (Chocolat choco : Chocolat.values()) {
			demandesChoco.put(choco, new Variable(getNom() + " : " + choco.name() + " [Demande]", ac, 0));
		}

		for (Chocolat choco : ac.nosChoco) {
			chocoReceptionne.put(choco, new Variable(getNom() + " : " + choco.name() + " [Réception]", ac, 0));
		}
	}
	
	public void initJournaux() {
		this.journal = new Journal(this.getNom() + " : Acheteur Chocolat Bourse", ac);
		journal.ajouter(Journal.texteColore(titleColor, Color.WHITE, this.getNom() + " : Acheteur Chocolat Bourse"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Ce journal suit les activités de l'acheteur de chocolat à la bourse"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "associé à ce distributeur. Il affiche les commandes effectuées et leur"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "statut (payée, impayée) ainsi que les réceptions de commandes."));
	}

	public String getNom() {
		return ac.getNom();
	}

	public String getDescription() {
		return ac.getDescription();
	}

	public Color getColor() {
		return ac.getColor();
	}

	public List<String> getNomsFilieresProposees() {
		return ac.getNomsFilieresProposees();
	}

	public Filiere getFiliere(String nom) {
		return ac.getFiliere(nom);
	}

	public void setCryptogramme(Integer crypto) {
		ac.setCryptogramme(crypto);
	}

	public void notificationFaillite(IActeur acteur) {
		ac.notificationFaillite(acteur);
	}

	public void notificationOperationBancaire(double montant) {
		ac.notificationOperationBancaire(montant);
	}
	
	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		for (Chocolat choco : ac.nosChoco) {
			res.add(chocoReceptionne.get(choco));
		}
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res = new ArrayList<Journal>();
		res.add(journal);
		return res;
	}
	
} 
