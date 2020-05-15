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
                return this.offreStandard(cours, chocolat);
            case CHOCOLAT_HAUTE:
                return this.offreStandard(cours, chocolat);
            case CHOCOLAT_HAUTE_EQUITABLE:
                return this.offreStandard(cours, chocolat);
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

    private double offreStandard(double cours, Chocolat choco) {
        double offre = 0;
        for (Couple<Variable> cp : acteur.getStock().getStockChocolat().get(choco)) {
            if (cours > seuilRentabilite + cp.get2().getValeur()) {
                offre += cp.get1().getValeur() * facteurDeVente(cours * cp.get1().getValeur());
                tentativeDeVente.get(choco).add(cp);
                this.croissanceRentabilite();
            } else if (this.necessiteDeVendre(choco)) {
                offre += cp.get1().getValeur();
                tentativeDeVente.get(choco).add(cp);
            }else { 
                this.decroissanceRentabilite();
            }
        }
        return offre;
    }
    
    /**
     * @return <b>true</b> si la trésorerie est dans un état critique, <b>false</b> sinon
     *  false sinon
     */
    private boolean necessiteDeVendre(Chocolat choco) {
        if (this.acteur.getTresorier().getMontantCompte() + this.valeurDuStockAVendreChoco(choco) < 10000) {
            return true;
        } else {
            return false;
        }
    }
    private double valeurDuStockAVendreChoco(Chocolat choco){
        return this.tentativeDeVente.get(choco).stream().mapToDouble(couple -> couple.get1().getValeur()*couple.get2().getValeur()).sum();
    }
    /**
     * 
     * @param profitPrevu 
     * @return proprotion de stock qu'il faut vendre
     */
    private double facteurDeVente(double profitPrevu) {
        if(seuilRentabilite <= 0){
            return 0.5;
        }
        else{
            double steep = 1;
            return Math.sqrt((profitPrevu - 1) / steep / this.seuilRentabilite);
        }
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
            seuilRentabilite = seuilRentabilite * 0.95 - 100;
        else
            seuilRentabilite = seuilRentabilite * 1.05 - 50;
    }
    
    private void croissanceRentabilite() {
        if(seuilRentabilite > 0)
            seuilRentabilite = (seuilRentabilite + 0.5) / 0.95;
        else
            seuilRentabilite = (seuilRentabilite + 50) /1.05;
    }
}