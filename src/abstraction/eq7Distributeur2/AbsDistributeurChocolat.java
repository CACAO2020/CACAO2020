package abstraction.eq7Distributeur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	protected Map<Chocolat, Variable> totalChocoVendu;
	protected Map<Chocolat, Variable> chocoVendu;
	
	
	public Color titleColor = Color.BLACK;
	public Color metaColor = Color.CYAN;
	public Color alertColor = Color.RED;
	public Color warningColor = Color.ORANGE;
	public Color positiveColor = Color.GREEN;
	public Color descriptionColor = Color.YELLOW;
	
	public AbsDistributeurChocolat(Distributeur2 ac) {	
		this.ac = ac;
		totalChocoVendu = new HashMap<Chocolat, Variable>();
		chocoVendu = new HashMap<Chocolat, Variable>();
		for (Chocolat choco : ac.nosChoco) {
			totalChocoVendu.put(choco, new Variable(getNom() + " : " + choco.name() + " [Total Ventes]", ac, 0));
			chocoVendu.put(choco, new Variable(getNom() + " : " + choco.name() + " [Ventes i-1]", ac, 0));		
		}
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
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "types de chocolat mis en vente, quantités disponibles à la vente et prix à l'unité."));
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "Il est mis à jour à chaque appel de la commande next() du distributeur, c'est pourquoi"));
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "le catalogue à une étape donnée n'est visible qu'à partir du passage à l'étape suivante."));
		journalCatalogue.ajouter(Journal.texteColore(descriptionColor, Color.BLACK, "L'état du catalogue à une étape correspond à celui proposé au client lors de cette étape."));
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
			res.add(chocoVendu.get(choco));
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
		res.add(journalCatalogue);
		return res;
	}
}