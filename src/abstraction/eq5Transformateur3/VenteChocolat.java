package abstraction.eq5Transformateur3;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.fourni.Variable;

/** @author Nicolas PIERRE
 *  Gère la vente de chocolat à la bourse
 * Elle est agrégé dans la classe Transformateur3. */
public class VenteChocolat {
    
    private Transformateur3 acteur;
    private double seuilRentabilite;
    private Map<Chocolat, List<Couple<Variable>>> tentativeDeVente;

    public VenteChocolat(Transformateur3 acteur) {
        this.acteur = acteur;
        this.seuilRentabilite = 1000;
        tentativeDeVente = new HashMap<>();
    }

    public double getOffre(Chocolat chocolat, double cours) {
        if (tentativeDeVente.get(chocolat) == null) {
            tentativeDeVente.put(chocolat, new LinkedList<>());
        } else {
            tentativeDeVente.get(chocolat).clear();
        }
        switch (chocolat) {
            case CHOCOLAT_BASSE:
                return this.offreBasse(cours);
            case CHOCOLAT_HAUTE:
                return this.offreHaute(cours, chocolat);
            case CHOCOLAT_HAUTE_EQUITABLE:
                return this.offreHaute(cours, chocolat);
            default:
                return this.offreMonopole(chocolat, cours);
        }
    }

    /**
     * Cette décision d'offre est pour les marchés qui ne nous concerne pas normalement
     * On ne retourne pas 0 seulement si le marché de ce type de chocolat est anormalement haut
     */
    private double offreMonopole(Chocolat chocolat, double cours) {
        return 0;
    }

    private double offreHaute(double cours, Chocolat choco) {
        double offre = 0;
        for (Couple<Variable> cp : acteur.getStock().getStockChocolat().get(choco)) {
            if (cours * cp.get1().getValeur() > seuilRentabilite + cp.get1().getValeur() * cp.get2().getValeur()) {
                offre += facteurDeVente(cours * cp.get1().getValeur() / seuilRentabilite);
                tentativeDeVente.get(choco).add(cp);
                this.croissanceRentabilite();
            } else {
                this.decroissanceRentabilite();
            }
        }
        return offre;
    }
    /**
     * 
     * @param facteurDeRentabilite ratio entre le profit prévu et le seuil de rentabilité (<b>facteurDeRentabilite > 1</b>)
     * @return proprotion de stock qu'il faut vendre
     */
    private double facteurDeVente(double facteurDeRentabilite) {
        double steep = 1;
        return Math.sqrt((facteurDeRentabilite - 1) / steep);
    }

    private double offreBasse(double cours) {
        return 0;
    }

    public void livrer(Chocolat chocolat, double quantite) {
        double resteALivrer = quantite;
        //on trie les propositions de vente par prix décroissant
        Collections.sort(this.tentativeDeVente.get(chocolat),
                (e1, e2) -> (e2.get2().getValeur() - e1.get2().getValeur() <= 0) ? ((e1.get2().getValeur() - e2.get2().getValeur() < 0) ? -1 : 0) : 1);
        for (Couple<Variable> paquet : this.tentativeDeVente.get(chocolat)) {
            if (resteALivrer <= paquet.get1().getValeur()) {
                acteur.getStock().retirerChocolatPrix(chocolat, resteALivrer, paquet.get2().getValeur());
                break;
            } else {
                resteALivrer -= paquet.get1().getValeur();
                acteur.getStock().retirerChocolatPrix(chocolat, paquet.get1().getValeur(), paquet.get2().getValeur());
            }
        }
    }

    /**
     * réduit le seuil de rentabilité à chaque refus de vente car marge recherche de marge trop grande
     */
    private void decroissanceRentabilite() {
        if(seuilRentabilite > 0)
            seuilRentabilite = seuilRentabilite * 0.95 - 0.5;
        else
            seuilRentabilite -= 0.1;
    }
    
    private void croissanceRentabilite() {
        if(seuilRentabilite > 0)
            seuilRentabilite = (seuilRentabilite + 0.5) / 0.95;
        else
            seuilRentabilite += 0.1;
    }
}