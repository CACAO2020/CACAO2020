package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class AbsDistributeurChocolat {
	
	protected Integer cryptogramme;

	protected Distributeur2 ac;
	
	protected Journal journalCatalogue;
	protected Journal journal;
	
	public Color titleColor = Color.BLACK;
	public Color metaColor = Color.CYAN;
	public Color alertColor = Color.RED;
	public Color warningColor = Color.ORANGE;
	public Color positiveColor = Color.GREEN;
	public Color descriptionColor = Color.YELLOW;
	
	public AbsDistributeurChocolat(Distributeur2 ac) {	
		this.ac = ac;
		initJournaux();
	}
	
	public void initJournaux() {
		journal = new Journal(this.getNom() + " : Distributeur Chocolat", ac);
		journal.ajouter(Journal.texteColore(titleColor, Color.WHITE, this.getNom() + " : Distributeur Chocolat"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Ce journal mémorise les activités du distributeur, vendeur de chocolat au client"));
		journal.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "final. Il affiche les ventes effectuées et les alertes lorsqu'un rayon est vide."));
		
		journalCatalogue = new Journal(ac.getNom() + " : Catalogue du distributeur", ac);
		journalCatalogue.ajouter(Journal.texteColore(titleColor, Color.WHITE, this.getNom() + " : Catalogue du distributeur"));
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Ce journal présente le contenu du catalogue du distributeur à chaque étape :"));
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "types de chocolat en vente, quantités disponibles à la vente et prix à l'unité."));
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Il est mis à jour à chaque appel de la commande next() du distributeur,"));
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "soit APRÈS la vente au client final et AVANT la réception des commandes de l'étape."));
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
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> res = new ArrayList<Journal>();
		res.add(journal);
		res.add(journalCatalogue);
		return res;
	}
}