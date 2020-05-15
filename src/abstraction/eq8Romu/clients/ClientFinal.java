package abstraction.eq8Romu.clients;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.chocolatBourse.IVendeurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

// TODO : pub


public class ClientFinal implements IActeur {

	private static final double DISTRIBUTIONS_ANNUELLES[][] = {
			//Jan1 Jan2 Fev1 Fev2 Mar1 Mar2 Avr1 Avr2 Mai1 Mai2 Jui1 Jui2 Jul1 Jul2 Aou1 Aou2 Sep1 Sep2 Oct1 Oct2 Nov1 Nov2 Dec1 Dec2
			{ 3.5, 3.5, 6.0, 3.5, 3.5, 3.5, 3.5, 3.5, 9.0, 3.5, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.5, 3.5, 3.5, 3.5, 3.5, 3.5, 9.0, 9.0, },			
			{ 3.0, 3.0, 6.0, 3.0, 3.0, 3.0, 3.0, 3.0, 9.0, 3.0, 3.0, 2.0, 2.0, 2.0, 2.0, 2.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0,15.0,15.0, },			
			{ 3.0, 3.0, 7.0, 3.0, 3.0, 3.0, 3.0, 3.0,10.0, 3.0, 3.0, 2.0, 2.0, 2.0, 2.0, 2.0, 3.0, 3.0, 3.0,10.0, 3.0, 3.0,11.0,10.0, },			
			{ 3.0, 3.0,10.0, 3.0, 3.0, 3.0, 3.0, 3.0,12.0, 3.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 3.0, 3.0, 3.0, 3.0, 3.0,15.0,15.0, },			
			{ 3.0, 3.0,11.0, 3.0, 3.0, 3.0, 3.0, 3.0,13.0, 3.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 3.0, 3.0,10.0, 3.0, 3.0,11.0,10.0, },			
	};
	private Map<ChocolatDeMarque, Double> attractiviteChocolat;// Plus un chocolat a une forte attractivite (compare aux autres chocolats), plus ce chocolat aura une place importante dans la consommation globale de chocolat
	private Map<ChocolatDeMarque, Map<IDistributeurChocolatDeMarque, Double>> attractiviteDistributeur;// Pour un chocolat donne, plus l'attractivite d'un distributeur est grande (comparee a celle des autres distributeurs) plus sa part de marche sur ce chocolat sera grande
	private Map<Integer, Map<ChocolatDeMarque, Double>> relevesDePrixMoyens; // Pour chaque etape, on memorise le prix de vente moyen des differents chocolats
	private Variable deltaConsoMin, deltaConsoMax, conso, surcoutMemeQualite, surcoutQualitesDifferentes;
	private Map<IDistributeurChocolatDeMarque, List<Integer>> troisDernieresPubs;// les etapes auxquelles ont eu lieu les trois dernires campagnes de pub
	protected Map<Chocolat, Variable> stocksChocolat;
	protected Integer cryptogramme;
	protected Journal journal, journalPrix, JournalDistribution;

	// Evolution de la distribution temporelle de la consommation 
	private Variable dureeMinTransitionDistribution ;// passer d'une distribution de la consommation a une autre prend au moins ce nombre d'etapes
	private Variable dureeMaxTransitionDistribution;// passer d'une distribution de la consommation a une autre prend au plus ce nombre d'etapes
	private int distributionOrigine; // On part de DISTRIBUTIONS_ANNUELLES[ distributionOrigine ]
	private int distributionArrivee; // On evolue vers DISTRIBUTIONS_ANNUELLES[ distributionArrivee ]
	private int etapesEvolutionDistribution; // C'est au bout de etapesEvolutionDistribution qu'on atteindra la distribution DISTRIBUTIONS_ANNUELLES[ distributionArrivee ] 
	private int etapeEvolutionDistribution; // On a effectue etapeEvolutionDistribution etape sur les etapesEvolutionDistribution etapes necessaires pour atteindre la distribution d'arrivee
	private Map<Integer, Map<ChocolatDeMarque, Double>> historiqueVentes;

	public ClientFinal() {
		this.conso=new Variable(getNom()+" consommation annuelle", this, 7000000);
		this.deltaConsoMin=new Variable(getNom()+" delta annuel min conso", this, 0.0);
		this.deltaConsoMax=new Variable(getNom()+" delta annuel max conso", this, 0.0);
		this.journal = new Journal(this.getNom()+" activites", this);
		this.journalPrix = new Journal(this.getNom()+" prix", this);
		this.JournalDistribution= new Journal(this.getNom()+" distribution", this);
		this.dureeMinTransitionDistribution = new Variable(getNom()+" duree min evolution distribution", this, 24);
		this.dureeMaxTransitionDistribution = new Variable(getNom()+" duree max evolution distribution", this, 48);
		this.surcoutMemeQualite = new Variable(getNom()+" surcout meme qualite", this, 0.25);
		this.surcoutQualitesDifferentes = new Variable(getNom()+" surcout qualites differentes", this, 1.25);
		this.relevesDePrixMoyens = new HashMap<Integer, Map<ChocolatDeMarque, Double>> ();
		this.troisDernieresPubs = new HashMap<IDistributeurChocolatDeMarque, List<Integer>>();
	}

	public String getNom() {
		return "CLIENTFINAL";
	}

	public String getDescription() {
		return "Client final ";
	}

	public Color getColor() {
		return new Color(231,76,60);
	}

	public static List<IVendeurChocolatBourse> vendeursChocolatBourse() {
		List<IVendeurChocolatBourse> res = new LinkedList<IVendeurChocolatBourse>();
		for (IActeur acteur : Filiere.LA_FILIERE.getActeurs()) {
			if (acteur instanceof IVendeurChocolatBourse) {
				res.add((IVendeurChocolatBourse)acteur);
			}
		}
		return res;
	}
	public static List<IDistributeurChocolatDeMarque> distributeursChocolatDeMarque() {
		List<IDistributeurChocolatDeMarque> res = new LinkedList<IDistributeurChocolatDeMarque>();
		for (IActeur acteur : Filiere.LA_FILIERE.getActeurs()) {
			if (acteur instanceof IDistributeurChocolatDeMarque) {
				res.add((IDistributeurChocolatDeMarque)acteur);
			}
		}
		return res;
	}
	public static List<ChocolatDeMarque> tousLesChocolatsDeMarquePossibles() {
		List<ChocolatDeMarque> resultat = new LinkedList<ChocolatDeMarque>();
		for (Chocolat choco : Chocolat.values()) {
			List<IVendeurChocolatBourse> vendeursChocolatBourse=vendeursChocolatBourse();
			for (IVendeurChocolatBourse vendeur : vendeursChocolatBourse) {
				resultat.add( new ChocolatDeMarque(choco, vendeur.getNom()) );
			}
			List<IDistributeurChocolatDeMarque> distributeursChocolatDeMarque=distributeursChocolatDeMarque();
			for (IDistributeurChocolatDeMarque distri : distributeursChocolatDeMarque) {
				resultat.add( new ChocolatDeMarque(choco, distri.getNom()) );
			}
		}
		return resultat;
	}
	public static List<ChocolatDeMarque> chocolatsDeMarqueEnVente() {
		List<ChocolatDeMarque> res = new LinkedList<ChocolatDeMarque>();
		List<ChocolatDeMarque> tous =  tousLesChocolatsDeMarquePossibles();
		List<IDistributeurChocolatDeMarque> distributeurs = distributeursChocolatDeMarque();
		for (ChocolatDeMarque choco : tous) {
			boolean ajoute=false;
			for (int i=0;!ajoute && i<distributeurs.size();i++) {
				if (distributeurs.get(i).getCatalogue().contains(choco)) {
					res.add(choco);
					ajoute=true;
				}
			}
		}
		return res;
	}
	public static Map<Chocolat, List<ChocolatDeMarque>> chocolatsDeMarqueParType() {
		Map<Chocolat, List<ChocolatDeMarque>> repartition = new HashMap<Chocolat, List<ChocolatDeMarque>>();
		for (Chocolat choco : Chocolat.values()) {
			repartition.put(choco, new LinkedList<ChocolatDeMarque>());
		}
		List<ChocolatDeMarque> chocolatsDeMarqueEnVente = chocolatsDeMarqueEnVente();
		for (ChocolatDeMarque chocoMarque : chocolatsDeMarqueEnVente) {
			repartition.get(chocoMarque.getChocolat()).add(chocoMarque);
		}
		return repartition;
	}
	public static Map<ChocolatDeMarque, List<IDistributeurChocolatDeMarque>> distributeursParChocolatDeMarque() {
		Map<ChocolatDeMarque, List<IDistributeurChocolatDeMarque>> repartition = new HashMap<ChocolatDeMarque, List<IDistributeurChocolatDeMarque>>();
		List<IDistributeurChocolatDeMarque> distributeursChocolatDeMarque = distributeursChocolatDeMarque();
		List<ChocolatDeMarque> chocolatsDeMarqueEnVente = chocolatsDeMarqueEnVente();
		for (ChocolatDeMarque chocoMarque : chocolatsDeMarqueEnVente) {
			repartition.put(chocoMarque, new LinkedList<IDistributeurChocolatDeMarque>());
			for (IDistributeurChocolatDeMarque distri : distributeursChocolatDeMarque) {
				if (distri.getCatalogue().contains(chocoMarque)) {
					repartition.get(chocoMarque).add(distri);
				}
			}
		}
		return repartition;
	}

	public static List<ChocolatDeMarque> chocolatsDeMarqueEnRayon() {
		List<ChocolatDeMarque> res = new LinkedList<ChocolatDeMarque>();
		List<ChocolatDeMarque> tous =  chocolatsDeMarqueEnVente();
		List<IDistributeurChocolatDeMarque> distributeurs = distributeursChocolatDeMarque();
		for (ChocolatDeMarque choco : tous) {
			boolean ajoute=false;
			for (int i=0;!ajoute && i<distributeurs.size();i++) {
				if (distributeurs.get(i).quantiteEnVente(choco)>0.0) {
					res.add(choco);
					ajoute=true;
				}
			}
		}
		return res;
	}
	public void initialiser() {
		this.distributionOrigine = (int) (Math.random()*DISTRIBUTIONS_ANNUELLES.length);
		this.distributionArrivee = (int) (Math.random()*DISTRIBUTIONS_ANNUELLES.length);// potentiellement distributionArrivee==distributionOrigine ==> dans ce cas on est dans une periode ou les pics de consommation ne changent pas.
		this.etapesEvolutionDistribution =(int) ( dureeMinTransitionDistribution.getValeur() + (int)(Math.random()*(dureeMaxTransitionDistribution.getValeur()-dureeMinTransitionDistribution.getValeur()))); // Nombre d'etapes pour atteindre la prochaine distribution
		this.etapeEvolutionDistribution=0;

		// Initialisation a 1.0 de l'attractivite de tous les distributeurs sur tous les types de chocolat
		this.attractiviteDistributeur=new HashMap<ChocolatDeMarque, Map<IDistributeurChocolatDeMarque, Double>>();
		List<ChocolatDeMarque> tousLesChocolatsDeMarquePossibles = tousLesChocolatsDeMarquePossibles();
		List<IDistributeurChocolatDeMarque> distributeurs = distributeursChocolatDeMarque();
		for (ChocolatDeMarque choco : tousLesChocolatsDeMarquePossibles) {
			HashMap<IDistributeurChocolatDeMarque, Double> attract = new HashMap<IDistributeurChocolatDeMarque,Double>();
			for (IDistributeurChocolatDeMarque distri : distributeurs) {
				attract.put(distri, 1.0);
			}
			this.attractiviteDistributeur.put(choco, attract);
		}
		// Initialisation de l'attractivite des gammes de chocolat
		// La division sert a repartir l'attractivite d'un type de chocolat entre les marques aux catalogues de ce type de chocolat
		Map<Chocolat, List<ChocolatDeMarque>> chocolatsDeMarqueParType=chocolatsDeMarqueParType();
		this.attractiviteChocolat = new HashMap<ChocolatDeMarque, Double>();
		for (ChocolatDeMarque choco : tousLesChocolatsDeMarquePossibles) {
			System.out.println("-- "+choco.name());
			switch (choco.getChocolat()) {
			case CHOCOLAT_BASSE : attractiviteChocolat.put(choco, 4.0/(chocolatsDeMarqueParType.get(Chocolat.CHOCOLAT_BASSE).size()==0? 1.0:chocolatsDeMarqueParType.get(Chocolat.CHOCOLAT_BASSE).size())); break;
			case CHOCOLAT_MOYENNE : attractiviteChocolat.put(choco, 2.0/(chocolatsDeMarqueParType.get(Chocolat.CHOCOLAT_MOYENNE).size()==0? 1.0:chocolatsDeMarqueParType.get(Chocolat.CHOCOLAT_MOYENNE).size())); break;
			case CHOCOLAT_HAUTE : attractiviteChocolat.put(choco, 1.0/(chocolatsDeMarqueParType.get(Chocolat.CHOCOLAT_HAUTE).size()==0? 1.0:chocolatsDeMarqueParType.get(Chocolat.CHOCOLAT_HAUTE).size())); break;
			case CHOCOLAT_MOYENNE_EQUITABLE : attractiviteChocolat.put(choco, 1.5/(chocolatsDeMarqueParType.get(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE).size()==0? 1.0:chocolatsDeMarqueParType.get(Chocolat.CHOCOLAT_MOYENNE_EQUITABLE).size())); break;
			case CHOCOLAT_HAUTE_EQUITABLE : attractiviteChocolat.put(choco, 0.5/(chocolatsDeMarqueParType.get(Chocolat.CHOCOLAT_HAUTE_EQUITABLE).size()==0? 1.0:chocolatsDeMarqueParType.get(Chocolat.CHOCOLAT_HAUTE_EQUITABLE).size())); break;
			}
		}
		// Initialisation de l'historique des volumes de ventes
		this.historiqueVentes=new HashMap<Integer, Map<ChocolatDeMarque, Double>>();
		List<ChocolatDeMarque> chocolatsDeMarqueEnVente = chocolatsDeMarqueEnVente();
		for (int etape=0; etape<24; etape++) {
			Map<ChocolatDeMarque, Double> ventesEtape = new HashMap<ChocolatDeMarque, Double>();
			double consoStep = conso.getValeur()*ratioStep(etape);
			// Repartition des besoins clients en ventes
			double totalAttractiviteChocolats = 0.0;
			for (ChocolatDeMarque choco : chocolatsDeMarqueEnVente) {
				totalAttractiviteChocolats+=attractiviteChocolat.get(choco);
			}
			for (ChocolatDeMarque choco : chocolatsDeMarqueEnVente) {
				ventesEtape.put(choco,consoStep*attractiviteChocolat.get(choco)/totalAttractiviteChocolats);
			}
			historiqueVentes.put(etape-24, ventesEtape);
		}
		// Initialisation des etapes auxquelles ont eu lieu les dernieres campagnes de pub
		for (IDistributeurChocolatDeMarque distri : distributeurs) {
			List<Integer> etapes = new LinkedList<Integer>();
			etapes.add(-24);
			etapes.add(-23);
			etapes.add(-22);
			troisDernieresPubs.put(distri, etapes);
		}
	}

	public boolean campagneDePubAutorisee(IDistributeurChocolatDeMarque distri) {
		return (Filiere.LA_FILIERE.getEtape() - troisDernieresPubs.get(distri).get(0)) >=24;
	}
	
	public void memorisePubs(IDistributeurChocolatDeMarque distri) {
		troisDernieresPubs.get(distri).add(Filiere.LA_FILIERE.getEtape());
		troisDernieresPubs.get(distri).remove(0);
	}
	
	public double getVentes(int etape, ChocolatDeMarque choco) {
		if (this.historiqueVentes.keySet().contains(etape)) {
			if (this.historiqueVentes.get(etape).containsKey(choco)) {
				return this.historiqueVentes.get(etape).get(choco);
			} else {
				return 0.0;
			}
		} else {
			throw new IllegalArgumentException(" Appel de ClientFinal.getVentes avec etape=="+etape+" alors que les etapes valides sont "+this.historiqueVentes.keySet());
		}
	}

	public double getVentes(int etape, Chocolat choco) {
		if (this.historiqueVentes.keySet().contains(etape)) {
			Map<ChocolatDeMarque, Double> ventes = this.historiqueVentes.get(etape);
			double totalVentes=0.0;
			for (ChocolatDeMarque cdm : ventes.keySet()) {
				if (cdm.getChocolat().equals(choco)) {
					totalVentes=totalVentes+ventes.get(cdm);
				}
			}
			return totalVentes;
		} else {
			throw new IllegalArgumentException(" Appel de ClientFinal.getVentes avec etape=="+etape+" alors que les etapes valides sont "+this.historiqueVentes.keySet());
		}
	}

	public void initAttractiviteChoco(ChocolatDeMarque choco, double val) {
		if (Filiere.LA_FILIERE==null || Filiere.LA_FILIERE.getEtape()<1) {
			if (val<0.1) {
				throw new IllegalArgumentException("la methode initAttractiviteChoco de ClientFinal n'accepte pas une valeur inferieure a 0.1");
			} else {
				attractiviteChocolat.put(choco, val);
			}
		} else {
			throw new IllegalArgumentException("la methode initAttractiviteChoco de ClientFinal ne peut etre appelee qu'avant le premier step");
		}
	}

	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}
	private double ratioStep(int step) {
		double origine = DISTRIBUTIONS_ANNUELLES[distributionOrigine][step%24];
		double arrivee = DISTRIBUTIONS_ANNUELLES[distributionArrivee][step%24];
		return (origine+((arrivee-origine)*etapeEvolutionDistribution)/etapesEvolutionDistribution)/100.0;
	}

	private double ratioStep() {
		return ratioStep(Filiere.LA_FILIERE.getEtape());
	}

	public double prixMoyen(ChocolatDeMarque choco) {
		return relevesDePrixMoyens.get(Filiere.LA_FILIERE.getEtape()).get(choco);
	}

	public double prixMoyenEtapePrecedente(ChocolatDeMarque choco) {
		if (Filiere.LA_FILIERE.getEtape()<1) {
			throw new IllegalAccessError(" Il est impossible de faire appel a prixMoyenEtapePrecedente a l'etape 1");
		} else {
			return relevesDePrixMoyens.get(Filiere.LA_FILIERE.getEtape()-1).get(choco);
		}
	}

	public void next() {
		// Evolution de la distribution annuelle des ventes
		this.etapeEvolutionDistribution++;
		if (this.etapeEvolutionDistribution>this.etapesEvolutionDistribution) {
			this.etapeEvolutionDistribution=0;
			this.distributionOrigine=this.distributionArrivee;
			this.distributionArrivee = (int) (Math.random()*DISTRIBUTIONS_ANNUELLES.length);// potentiellement distributionArrivee==distributionOrigine ==> dans ce cas on est dans une periode ou les pics de consommation ne changent pas.
			this.etapesEvolutionDistribution =(int) ( dureeMinTransitionDistribution.getValeur() + (int)(Math.random()*(dureeMaxTransitionDistribution.getValeur()-dureeMinTransitionDistribution.getValeur()))); // Nombre d'etapes pour atteindre la prochaine distribution
		}
		this.JournalDistribution.ajouter("Etape "+Filiere.LA_FILIERE.getEtape()+" ======================");
		this.JournalDistribution.ajouter(" origine="+this.distributionOrigine+" arrive="+this.distributionArrivee+" etape="+this.etapeEvolutionDistribution+"/"+this.etapesEvolutionDistribution);

		// Memorisation des prix moyens a l'etape courante
		List<ChocolatDeMarque> chocolatsDeMarqueEnVente = chocolatsDeMarqueEnVente();
		Map<ChocolatDeMarque, List<IDistributeurChocolatDeMarque>>distributeursParChocolatDeMarque=distributeursParChocolatDeMarque();
		Map<ChocolatDeMarque, Double> releve = new HashMap<ChocolatDeMarque, Double>();
		List<IDistributeurChocolatDeMarque> distribs;
		journalPrix.ajouter("Etape "+Filiere.LA_FILIERE.getEtape()+" ===============================");
		for (ChocolatDeMarque choco : chocolatsDeMarqueEnVente) {
			distribs = distributeursParChocolatDeMarque.get(choco);
			if (distribs.size()==0) {
				journalPrix.ajouter(" pas d'achat possible de "+choco.name());
				releve.put(choco, Double.MAX_VALUE);
			} else {
				double somme=0.0;
				int nbDistrib=0;
				for (IDistributeurChocolatDeMarque distri : distribs) {
					if (distri.quantiteEnVente(choco)>0.0) {
						nbDistrib++;
						journalPrix.ajouter(" "+distri.getNom()+" vend "+choco.name()+" au prix de "+Journal.doubleSur(distri.prix(choco), 4));
						somme+=distri.prix(choco);
					}
				}
				if (nbDistrib==0) {
					journalPrix.ajouter(" pas d'achat possible de "+choco.name());
					releve.put(choco, Double.MAX_VALUE);
				} else {
					journalPrix.ajouter(" prix moyen du chocolat "+choco.name()+" = "+Journal.doubleSur(somme/nbDistrib, 4));
					releve.put(choco, somme/nbDistrib);
				}
			}
		}
		this.relevesDePrixMoyens.put(Filiere.LA_FILIERE.getEtape(), releve);

		// Evolution du volume de la consommation annuelle
		if (deltaConsoMin.getValeur()>deltaConsoMax.getValeur()) {
			double max = deltaConsoMin.getValeur();
			deltaConsoMin.setValeur(this,  deltaConsoMax.getValeur());
			deltaConsoMax.setValeur(this,  max);
		}
		double deltaConso = deltaConsoMin.getValeur() + (Math.random()*(deltaConsoMax.getValeur() - deltaConsoMin.getValeur()));
		conso.setValeur(this, conso.getValeur()*(1+(deltaConso/24.0)));

		journal.ajouter("delta conso = "+Journal.doubleSur(deltaConso, 4)+" ==> conso = "+Journal.doubleSur(conso.getValeur(),2));

		// Calcul du volume de la consommation a cette etape 
		double consoStep = conso.getValeur()*ratioStep();
		journal.ajouter(" le ratio a ce step est "+Journal.doubleSur(ratioStep(), 4)+" --> la consommation est de "+Journal.doubleSur(consoStep, 4));

		// Repartition des besoins clients en ventes
		double totalAttractiviteChocolats = 0.0;
		for (ChocolatDeMarque choco : chocolatsDeMarqueEnVente) {
			totalAttractiviteChocolats+=attractiviteChocolat.get(choco);
		}
		journal.ajouter(" la somme des attractivites des chocolats est "+Journal.doubleSur(totalAttractiviteChocolats,4));
		Map<ChocolatDeMarque, Double> ventesEtape = new HashMap<ChocolatDeMarque, Double>();

		for (ChocolatDeMarque choco : chocolatsDeMarqueEnVente) {
			if (!attractiviteDistributeur.get(choco).keySet().isEmpty()) {// il y a des distributeurs
				journal.ajouter(" l'attractivite de "+choco.name()+" est de "+Journal.doubleSur(attractiviteChocolat.get(choco), 4));
				double consoStepChoco = consoStep*attractiviteChocolat.get(choco)/totalAttractiviteChocolats;
				// Cette consommation consoStepChoco est a repartir entre les distributeurs au prorata de leur attractivite sur ce produit
				List<IDistributeurChocolatDeMarque> distributeursDeChoco = distributeursParChocolatDeMarque.get(choco);
				double totalAttractiviteDistris = 0.0;
				double totalVentes=0.0;// pour moemoriser dans l'historique
				for (IDistributeurChocolatDeMarque dist : distributeursDeChoco) {
					totalAttractiviteDistris+=this.attractiviteDistributeur.get(choco).get(dist).doubleValue();
				}
				journal.ajouter(" le total de l'acttractivite des distributeurs est "+Journal.doubleSur(totalAttractiviteDistris, 4));
				double prixMoyen =prixMoyen(choco);
				for (IDistributeurChocolatDeMarque dist : distributeursDeChoco) {
					double quantiteDesiree = consoStepChoco*this.attractiviteDistributeur.get(choco).get(dist)/totalAttractiviteDistris;
					double enVente = dist.quantiteEnVente(choco);
					double quantiteAchetee = Math.min(quantiteDesiree, enVente);
					journal.ajouter("   pour "+dist.getNom()+" d'attractivite "+Journal.doubleSur(this.attractiviteDistributeur.get(choco).get(dist), 4)+" la quantite desiree est "+Journal.doubleSur(quantiteDesiree,4)+" et quantite en vente ="+Journal.doubleSur(enVente, 4)+" -> quantitee achetee "+Journal.doubleSur(quantiteAchetee, 4));
					if (quantiteAchetee>0.0) {
						totalVentes+=quantiteAchetee;
						Filiere.LA_FILIERE.getBanque().virer(this, cryptogramme, dist, quantiteAchetee*dist.prix(choco));
						dist.vendre(this, choco, quantiteAchetee, quantiteAchetee*dist.prix(choco));
					}
					if (quantiteDesiree>enVente) {
						dist.notificationRayonVide(choco);
					}
					double penaliteRupture = (quantiteDesiree>enVente ? -0.03 : 0.01); // si le client n'a pas trouve tout ce qu'il souhaite la penalite est de -3%, sinon l'attractivite augmente de 1%
					double penalitePrix = ((prixMoyen-dist.prix(choco))/prixMoyen)/10.0; // 10% du pourcentage d'ecart de prix avec la moyenne.
					attractiviteDistributeur.get(choco).put(dist,attractiviteDistributeur.get(choco).get(dist)*(1.0+penaliteRupture+penalitePrix));
					journal.ajouter(" penalite pour rupture de stock de "+dist.getNom()+" = "+Journal.doubleSur(penaliteRupture,  4));
					journal.ajouter(" penalite pour l'ecart avec le prix moyen "+dist.getNom()+" = "+Journal.doubleSur(penalitePrix,  4));
					journal.ajouter(" l'attractivite de "+dist.getNom()+" pour le chocolat "+choco.name()+" = "+Journal.doubleSur(attractiviteDistributeur.get(choco).get(dist), 4));
				}
				ventesEtape.put(choco,totalVentes);
			}
			this.historiqueVentes.put(Filiere.LA_FILIERE.getEtape(), ventesEtape);
		}

		// Transfert d'attractivite entre les chocolats en fonction des rapports qualite/prix
		for (ChocolatDeMarque choco1 : chocolatsDeMarqueEnVente) {
			for (ChocolatDeMarque choco2 : chocolatsDeMarqueEnVente) {
				if (choco1!=choco2) {
					ChocolatDeMarque moinsCher=choco1;
					ChocolatDeMarque plusCher=choco2;
					if (prixMoyen(choco2)<prixMoyen(choco1)) {
						moinsCher=choco2;
						plusCher=choco1;
					}
					if (qualite(moinsCher)==qualite(plusCher)) {
						if ((prixMoyen(plusCher)-prixMoyen(moinsCher))/prixMoyen(moinsCher)>this.surcoutMemeQualite.getValeur()) {// a qualite identique un ecart de prix de plus de 25% modifie l'attractivite
							attractiviteChocolat.put(moinsCher, attractiviteChocolat.get(moinsCher)*1.005);// +0.5%						
							attractiviteChocolat.put(plusCher, attractiviteChocolat.get(plusCher)*0.995);// -0.5%						
							this.JournalDistribution.ajouter("le prix moyen du "+moinsCher.name()+" est de "+Journal.doubleSur(prixMoyen(moinsCher), 4)+" et celui du "+plusCher.name()+" est de "+Journal.doubleSur(prixMoyen(plusCher), 4)+" --> attractivites evoluent");
						}
					} else if (qualite(moinsCher)>qualite(plusCher)) {
						attractiviteChocolat.put(moinsCher, attractiviteChocolat.get(moinsCher)*1.05);// +5%						
						attractiviteChocolat.put(plusCher, attractiviteChocolat.get(plusCher)*0.95);// -5%						
						this.JournalDistribution.ajouter("le prix moyen du "+moinsCher.name()+" est de "+Journal.doubleSur(prixMoyen(moinsCher), 4)+" et celui du "+plusCher.name()+" est de "+Journal.doubleSur(prixMoyen(plusCher), 4)+" --> attractivites evoluent");
					} else {
						if ((prixMoyen(plusCher)-prixMoyen(moinsCher))/prixMoyen(moinsCher)>this.surcoutQualitesDifferentes.getValeur()*(qualite(plusCher)-qualite(moinsCher))) {
							attractiviteChocolat.put(moinsCher, attractiviteChocolat.get(moinsCher)*1.005);						
							attractiviteChocolat.put(plusCher, attractiviteChocolat.get(plusCher)*0.995);					
							this.JournalDistribution.ajouter("le prix moyen du "+moinsCher.name()+" est de "+Journal.doubleSur(prixMoyen(moinsCher), 4)+" et celui du "+plusCher.name()+" est de "+Journal.doubleSur(prixMoyen(plusCher), 4)+" --> attractivites evoluent");
						}
					}
				}
			}
		}
		for (ChocolatDeMarque choco1 : chocolatsDeMarqueEnVente) {
			this.JournalDistribution.ajouter(" attractivite du "+choco1.name()+" == "+Journal.doubleSur(attractiviteChocolat.get(choco1), 4));
		}
		
		// Campagnes de pub
		List<IDistributeurChocolatDeMarque> distributeurs = distributeursChocolatDeMarque();
		for (IDistributeurChocolatDeMarque distri : distributeurs) {
			if (campagneDePubAutorisee(distri)) {
				List<ChocolatDeMarque> produits = distri.pubSouhaitee();
				if (produits!= null && produits.size()>0) {// le distributeur souhaite faire une campagne de pub
					this.journal.ajouter("PUB de "+distri.getNom());
					for (ChocolatDeMarque choc : produits) {
						if (!attractiviteChocolat.containsKey(choc)) {
							throw new Error(" appele sur l'acteur "+distri.getNom()+" la methode pubSouhaitee retourne une liste contenant un chocolat ne possedant pas d'attractivite:"+choc.name());
						} else {
							attractiviteChocolat.put(choc, attractiviteChocolat.get(choc)*(1.0+ (0.15/produits.size())));
							memorisePubs(distri);
						}
					}
				}
			}
		}
	}


	private static double qualite(ChocolatDeMarque choco) {
		switch (choco.getChocolat()) {
		case CHOCOLAT_BASSE : return 1.0;
		case CHOCOLAT_MOYENNE : return 2.0;
		case CHOCOLAT_HAUTE : return 3.0;
		case CHOCOLAT_MOYENNE_EQUITABLE : return 2.5;
		default : return 3.5; //CHOCOLAT_HAUTE_EQUITABLE
		}
	}
	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.conso);
		res.add(deltaConsoMin);
		res.add(deltaConsoMax);
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(this.dureeMinTransitionDistribution);
		res.add(this.dureeMaxTransitionDistribution);
		res.add(this.surcoutMemeQualite);
		res.add(this.surcoutQualitesDifferentes);
		return res;
	}

	public List<Journal> getJournaux() {
		List<Journal> j= new ArrayList<Journal>();
		j.add(this.journal);
		j.add(this.journalPrix);
		j.add(this.JournalDistribution);
		return j;
	}

	public void notificationFaillite(IActeur acteur) {
	}

	public void notificationOperationBancaire(double montant) {
	}
}
